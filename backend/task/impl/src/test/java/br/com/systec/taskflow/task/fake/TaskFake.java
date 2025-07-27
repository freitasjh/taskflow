package br.com.systec.taskflow.task.fake;

import br.com.systec.taskflow.task.api.model.Task;
import br.com.systec.taskflow.task.api.model.TaskStatus;
import br.com.systec.taskflow.task.api.vo.TaskVO;

import java.time.LocalDateTime;

public class TaskFake {

    public static Task fake() {
        Task task = new Task();
        task.setId(3L);
        task.setCode("CODE-003");
        task.setDescription("Task 3");
        task.setStatus(TaskStatus.OPEN);
        task.setDateClose(LocalDateTime.now());
        task.setCreatedBy(103L);
        task.setProjectId(1L);
        task.setWorkflowStatus(1L);

        return task;
    }

    public static TaskVO fakeVO() {
        TaskVO task = new TaskVO();
        task.setId(3L);
        task.setCode("CODE-003");
        task.setDescription("Task 3");
        task.setStatus(TaskStatus.OPEN);
        task.setDateClose(LocalDateTime.now());
        task.setCreatedBy(103L);
        task.setTeamId(203L);
        task.setWorkflowStatus(1L);
        task.setProjectId(1L);

        return task;
    }
}
