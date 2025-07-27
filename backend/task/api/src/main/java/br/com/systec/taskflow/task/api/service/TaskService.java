package br.com.systec.taskflow.task.api.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.task.api.filter.TaskFilter;
import br.com.systec.taskflow.task.api.vo.TaskFilterResponseVO;
import br.com.systec.taskflow.task.api.vo.TaskVO;
import org.springframework.data.domain.Page;

public interface TaskService {

    TaskVO create(TaskVO taskVO) throws BaseException;

    TaskVO update(TaskVO taskVO) throws BaseException;

    TaskVO findById(Long id) throws BaseException;

    Page<TaskFilterResponseVO> findByFilter(TaskFilter filter) throws BaseException;

    void addAccountable(Long accountableId, Long taskId) throws BaseException;

    void addComment(String content, Long taskId) throws BaseException;

    void updateComment(String content, Long taskId, Long commentId) throws BaseException;

    void removeComment(Long taskObservationId, Long taskId) throws BaseException;

    void cancelTask(Long id, String content) throws BaseException;
}
