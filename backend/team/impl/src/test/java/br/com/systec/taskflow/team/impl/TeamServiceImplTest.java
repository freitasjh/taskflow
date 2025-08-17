package br.com.systec.taskflow.team.impl;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.ObjectNotFoundException;
import br.com.systec.taskflow.employee.api.model.Employee;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.team.api.TeamException;
import br.com.systec.taskflow.team.api.model.Team;
import br.com.systec.taskflow.team.api.model.TeamMembers;
import br.com.systec.taskflow.team.api.vo.TeamVO;
import br.com.systec.taskflow.team.impl.repository.TeamRepository;
import br.com.systec.taskflow.team.impl.repository.jpa.TeamRepositoryJpa;
import br.com.systec.taskflow.team.impl.service.TeamServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeamServiceImplTest {

    @Mock
    private TeamRepository repository;
    @Mock
    private TeamRepositoryJpa repositoryJpa;
    @InjectMocks
    private TeamServiceImpl teamService;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    private Team team;
    private TeamVO teamVO;
    private Employee employee;
    private TeamMembers member;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setMembers(new HashSet<>());

        teamVO = new TeamVO();
        teamVO.setId(1L);

        employee = new Employee();
        employee.setId(1L);

        member = new TeamMembers();
        member.setId(1L);
        member.setEmployee(employee);
    }

    @Test
    void create_Success_ReturnsTeamVO() throws BaseException {
        when(repository.save(any(Team.class))).thenReturn(team);
        TeamVO result = teamService.create(teamVO);

        assertNotNull(result);
        verify(repository).save(any(Team.class));
        verifyNoMoreInteractions(repository, repositoryJpa);
    }

    @Test
    void create_Failure_ThrowsBaseException() {
        when(repository.save(any(Team.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(BaseException.class, () -> teamService.create(teamVO));
        verify(repository).save(any(Team.class));
        verifyNoInteractions(repositoryJpa);
    }

    @Test
    void update_Success_ReturnsUpdatedTeamVO() throws BaseException {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));
        when(repository.update(any(Team.class))).thenReturn(team);

        TeamVO result = teamService.update(teamVO);

        assertNotNull(result);
        verify(repository).findById(Mockito.anyLong());
        verify(repository).update(any(Team.class));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void update_TeamNotFound_ThrowsBaseException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(TeamException.class, () -> teamService.update(teamVO));

        verify(repository).findById(1L);
    }

    @Test
    void removeMember_Success_RemovesMember() throws BaseException {
        team.setMembers(new HashSet<>());
        team.getMembers().add(member);

        when(repository.findById(1L)).thenReturn(Optional.of(team));

        teamService.removeMember(1L, 1L);

        verify(repository).findById(1L);
        verify(repository).update(any(Team.class));
    }

    @Test
    void removeMember_TeamNotFound_ThrowsBaseException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(TeamException.class, () -> teamService.removeMember(1L, 1L));

        verify(repository).findById(1L);
    }

    @Test
    void removeMember_MemberNotFound_ThrowsObjectNotFoundException() {
        when(repository.findById(1L)).thenReturn(Optional.of(team));

        Assertions.assertThrowsExactly(ObjectNotFoundException.class, () -> teamService.removeMember(1L, 1L));

        verify(repository).findById(1L);
    }

    @Test
    void addMember_Success_AddsMember() throws BaseException {
        when(repository.findById(1L)).thenReturn(Optional.of(team));

        teamService.addMember(1L, 1L);

        verify(repository).findById(1L);
        verify(repository).update(any(Team.class));
    }

    @Test
    void addMember_TeamNotFound_ThrowsBaseException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        BaseException exception = assertThrows(BaseException.class, () -> teamService.addMember(1L, 1L));
        verify(repository).findById(1L);

    }

    @Test
    void addMember_MemberAlreadyExists_ThrowsBaseException() {
        team.setMembers(Set.of(member));
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));

        BaseException exception = assertThrows(BaseException.class, () -> teamService.addMember(1L, 1L));
        assertEquals("Employee already exists in the team", exception.getMessage());
        verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void delete_Success_DeletesTeam() throws BaseException {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));

        teamService.delete(1L);

        verify(repository).findById(Mockito.anyLong());
        verify(repository).delete(any(Team.class));
    }

    @Test
    void delete_TeamNotFound_ThrowsBaseException() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(TeamException.class, () -> teamService.delete(1L));

        verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void findById_Success_ReturnsTeamVO() throws BaseException {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(team));

        TeamVO result = teamService.findById(1L);

        assertNotNull(result);
        verify(repository).findById(Mockito.anyLong());
    }

    @Test
    void findById_TeamNotFound_ThrowsBaseException() {
        when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(TeamException.class, () -> teamService.findById(1L));

        verify(repository).findById(Mockito.anyLong());
    }
}