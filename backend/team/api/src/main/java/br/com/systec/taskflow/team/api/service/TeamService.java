package br.com.systec.taskflow.team.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.team.api.filter.TeamFilter;
import br.com.systec.taskflow.team.api.vo.TeamVO;
import org.springframework.data.domain.Page;

public interface TeamService {

    TeamVO create(TeamVO teamVO) throws BaseException;

    TeamVO update(TeamVO teamVO) throws BaseException;

    TeamVO findById(Long id) throws BaseException;

    void delete(Long id) throws BaseException;

    void addMember(Long teamId, Long employeeId) throws BaseException;

    void removeMember(Long teamId, Long memberId) throws BaseException;

    Page<TeamVO> findByFilter(TeamFilter filter) throws BaseException;
}
