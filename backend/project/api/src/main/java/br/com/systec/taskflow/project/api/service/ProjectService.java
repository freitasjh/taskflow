package br.com.systec.taskflow.project.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.project.api.filter.ProjectFilter;
import br.com.systec.taskflow.project.api.model.Project;
import br.com.systec.taskflow.project.api.vo.ProjectVO;
import org.springframework.data.domain.Page;

public interface ProjectService {

    ProjectVO create(ProjectVO projectVO) throws BaseException;

    ProjectVO update(ProjectVO projectVO) throws BaseException;

    Page<ProjectVO> findByFilter(ProjectFilter filter) throws BaseException;

    ProjectVO findById(Long id) throws BaseException;

    Long findInitialStatusWorkflow(Long projectId) throws BaseException;
}
