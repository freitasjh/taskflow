package br.com.systec.taskflow.workflow.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.workflow.api.filter.WorkflowFilter;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import org.springframework.data.domain.Page;

public interface WorkflowService {

    WorkflowVO create(WorkflowVO workflow) throws BaseException;

    WorkflowVO update(WorkflowVO workflowVO) throws BaseException;

    WorkflowVO findById(Long id) throws BaseException;

    Page<WorkflowVO> findByFilter(WorkflowFilter filter) throws BaseException;

    StatusVO findStatusById(Long statusId) throws BaseException;

    StatusVO findInitialStatusByWorkflowId(Long workflowId) throws BaseException;
}
