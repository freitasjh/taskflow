package br.com.systec.taskflow.task.api.converter;

import br.com.systec.taskflow.task.api.model.Task;
import br.com.systec.taskflow.task.api.model.TaskComment;
import br.com.systec.taskflow.task.api.vo.TaskCommentVO;
import br.com.systec.taskflow.task.api.vo.TaskVO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;

public class TaskConverter {

    private TaskConverter(){}

    public static TaskConverter of() {
        return new TaskConverter();
    }

    public Task toModel(TaskVO taskVO) {
        Task task = new Task();
        task.setId(taskVO.getId());
        task.setCode(taskVO.getCode());
        task.setDescription(taskVO.getDescription());
        task.setStatus(taskVO.getStatus());
        task.setDateClose(taskVO.getDateClose());
        task.setCreatedBy(taskVO.getCreatedBy());
        task.setWorkflowStatus(taskVO.getWorkflowStatus());
        task.setPriority(taskVO.getPriority());
        task.setAccountable(taskVO.getAccountable());
        task.setObservation(taskVO.getObservation());
        task.setProjectId(taskVO.getProjectId());

        return task;
    }

    public TaskVO toVO(Task task) {
        TaskVO taskVO = new TaskVO();
        taskVO.setId(task.getId());
        taskVO.setDateCreate(task.getDateCreated());
        taskVO.setDateUpdate(task.getDateUpdated());
        taskVO.setCode(task.getCode());
        taskVO.setDescription(task.getDescription());
        taskVO.setStatus(task.getStatus());
        taskVO.setDateClose(task.getDateClose());
        taskVO.setCreatedBy(task.getCreatedBy());
        taskVO.setWorkflowStatus(task.getWorkflowStatus());
        taskVO.setPriority(task.getPriority());
        taskVO.setAccountable(task.getAccountable());
        taskVO.setObservation(task.getObservation());
        taskVO.setProjectId(task.getProjectId());

        if(task.getListComments() != null) {
            for (TaskComment item : task.getListComments()) {
                toTaskComment(taskVO, item);
            }
        }

        return taskVO;
    }

    private void toTaskComment(TaskVO task, TaskComment taskComment) {
        TaskCommentVO taskCommentVO = new TaskCommentVO();
        taskCommentVO.setTask(task);
        taskCommentVO.setId(taskComment.getId());
        taskCommentVO.setContent(taskComment.getContent());
        taskCommentVO.setCreateBy(taskComment.getCreatedBy());

        if (task.getListComments() == null) {
            task.setListComments(new ArrayList<>());
        }

        task.getListComments().add(taskCommentVO);
    }

    public Page<TaskVO> toPageVO(Page<Task> pageTaskResult) {
        return pageTaskResult.map(this::toVO);
    }

}
