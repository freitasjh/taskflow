package br.com.systec.taskflow.team.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.team.api.TeamException;
import br.com.systec.taskflow.team.api.converter.TeamConverter;
import br.com.systec.taskflow.team.api.filter.TeamFilter;
import br.com.systec.taskflow.team.api.model.Team;
import br.com.systec.taskflow.team.api.model.TeamMembers;
import br.com.systec.taskflow.team.api.service.TeamService;
import br.com.systec.taskflow.team.api.vo.TeamVO;
import br.com.systec.taskflow.team.impl.repository.TeamRepository;
import br.com.systec.taskflow.team.impl.repository.jpa.TeamRepositoryJpa;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeamServiceImpl implements TeamService {
    private static final Logger log = LoggerFactory.getLogger(TeamServiceImpl.class);

    private final TeamRepository repository;
    private final TeamRepositoryJpa repositoryJpa;


    public TeamServiceImpl(TeamRepository repository, TeamRepositoryJpa repositoryJpa) {
        this.repository = repository;
        this.repositoryJpa = repositoryJpa;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TeamVO create(TeamVO teamVO) throws BaseException {
        try {
            Team team = TeamConverter.toTeam(teamVO);
            Team teamSaved = repository.save(team);

            return TeamConverter.toVO(teamSaved);
        } catch (Exception e) {
            log.error("Error creating team: {}", e.getMessage(), e);
            throw new TeamException("Error creating team", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public TeamVO update(TeamVO teamVO) throws BaseException {
        try {
            Team existingTeam = getTeamById(teamVO.getId());

            Team team = TeamConverter.toUpdateTeam(teamVO, existingTeam);
            Team teamUpdated = repository.update(team);

            return TeamConverter.toVO(teamUpdated);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error creating team: {}", e.getMessage(), e);
            throw new TeamException("Error creating team", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeMember(Long teamId, Long memberId) throws BaseException {
        try {
            Team team = getTeamById(teamId);

            TeamMembers member = team.getMembers().stream()
                    .filter(m -> m.getId().equals(memberId))
                    .findFirst()
                    .orElseThrow(() -> new ObjectNotFoundException("Member not found with id: " + memberId));

            team.removeMember(member);

            repository.update(team);
        } catch (BaseException e) {
            log.error("Error removing member from team: {}", e.getMessage(), e);
            throw e; // Re-throwing the BaseException
        } catch (Exception e) {
            log.error("Error removing member from team: {}", e.getMessage(), e);
            throw new TeamException("Error removing member from team", e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addMember(Long teamId, Long employeeId) throws BaseException {
        try {
            Team team = getTeamById(teamId);

            boolean memberExists = team.getMembers().stream()
                    .anyMatch(m -> m.getEmployee().getId().equals(employeeId));

            if(memberExists) {
                throw new BaseException("Employee already exists in the team");
            }

            Employee employee = new Employee();
            employee.setId(employeeId);

            team.addMember(employee);

            repository.update(team);
        } catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error adding member to team: {}", e.getMessage(), e);
            throw new TeamException("Error adding member to team", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) throws BaseException {
        Team exixteTeam = getTeamById(id);

        repository.delete(exixteTeam);
    }

    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public TeamVO findById(Long id) throws BaseException {
        try {
            Team team = getTeamById(id);

            return TeamConverter.toVO(team);
        }  catch (BaseException e) {
            log.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error("Error finding team by id: {}", e.getMessage(), e);
            throw new TeamException("Error finding team by id", e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Page<TeamVO> findByFilter(TeamFilter filter) throws BaseException {
        try {
            Page<Team> teamPage = repositoryJpa.findAll(filter.getSpecification(), filter.getPageable());

            return teamPage.map(TeamConverter::toVO);
        } catch (Exception e) {
            log.error("Error finding teams by filter: {}", e.getMessage(), e);
            throw new TeamException("Error finding teams by filter", e);
        }
    }

    private Team getTeamById(Long id) throws BaseException {
        return repository.findById(id)
                .orElseThrow(() -> new TeamException(I18nTranslate.toLocale("team.not.found")));
    }
}
