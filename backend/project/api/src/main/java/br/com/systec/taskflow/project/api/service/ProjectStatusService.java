package br.com.systec.taskflow.project.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.project.api.filter.ProjectStatusFilter;
import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProjectStatusService {

    ProjectStatusVO create(ProjectStatusVO projectStatusVO) throws BaseException;

    ProjectStatusVO update(ProjectStatusVO projectStatusVO) throws BaseException;

    ProjectStatusVO findById(Long id) throws BaseException;

    List<ProjectStatusVO> findAll() throws BaseException;

    void delete(Long id) throws BaseException;

    Page<ProjectStatusVO> findByFilter(ProjectStatusFilter filter) throws BaseException;
}
