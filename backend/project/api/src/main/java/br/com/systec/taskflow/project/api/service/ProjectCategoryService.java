package br.com.systec.taskflow.project.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.project.api.filter.ProjectCategoryFilter;
import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import org.springframework.data.domain.Page;

public interface ProjectCategoryService {

    ProjectCategoryVO create(ProjectCategoryVO projectCategoryVO) throws BaseException;

    ProjectCategoryVO update(ProjectCategoryVO projectCategoryVO) throws BaseException;

    ProjectCategoryVO findById(Long id) throws BaseException;

    Page<ProjectCategoryVO> findByFilter(ProjectCategoryFilter filter) throws BaseException;
}
