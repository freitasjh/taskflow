package br.com.systec.taskflow.workflow.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.workflow.api.filter.StatusFilter;
import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StatusService {

    StatusVO create(StatusVO statusVO) throws BaseException;

    StatusVO update(StatusVO statusVO) throws BaseException;

    void delete(StatusVO statusVO) throws BaseException;

    List<StatusVO> findAll() throws BaseException;

    Page<StatusVO> findByFilter(StatusFilter filter) throws BaseException;

    List<Status> findByWorkflowId(Long workflowId) throws BaseException;
}
