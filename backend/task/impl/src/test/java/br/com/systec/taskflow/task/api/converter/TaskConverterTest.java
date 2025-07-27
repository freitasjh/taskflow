package br.com.systec.taskflow.task.api.converter;

import br.com.systec.taskflow.task.api.model.Task;
import br.com.systec.taskflow.task.api.vo.TaskVO;
import br.com.systec.taskflow.task.fake.TaskFake;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TaskConverterTest {

    @Test
    void whenConverterTask_thenReturnTaskVO() {
        Task taskToConvert = TaskFake.fake();

        TaskVO taskConverted = TaskConverter.of().toVO(taskToConvert);

        assertNotNull(taskConverted);
        assertEquals(taskConverted.getId(), taskToConvert.getId());
        assertEquals(taskConverted.getCode(), taskToConvert.getCode());
        assertEquals(taskConverted.getDescription(), taskToConvert.getDescription());
        assertEquals(taskConverted.getStatus(), taskToConvert.getStatus());
        assertEquals(taskConverted.getDateClose(), taskToConvert.getDateClose());
        assertEquals(taskConverted.getCreatedBy(), taskToConvert.getCreatedBy());
        assertEquals(taskConverted.getWorkflowStatus(), taskToConvert.getWorkflowStatus());
    }

    @Test
    void whenConverterTaskVO_thenReturnTask() {
        TaskVO taskToConvert = TaskFake.fakeVO();

        Task taskConverted = TaskConverter.of().toModel(taskToConvert);

        assertNotNull(taskConverted);
        assertEquals(taskConverted.getId(), taskToConvert.getId());
        assertEquals(taskConverted.getCode(), taskToConvert.getCode());
        assertEquals(taskConverted.getDescription(), taskToConvert.getDescription());
        assertEquals(taskConverted.getStatus(), taskToConvert.getStatus());
        assertEquals(taskConverted.getDateClose(), taskToConvert.getDateClose());
        assertEquals(taskConverted.getCreatedBy(), taskToConvert.getCreatedBy());
        assertEquals(taskConverted.getWorkflowStatus(), taskToConvert.getWorkflowStatus());
    }

    @Test
    void whenConvertPageTask_thenReturnPageTaskVO() {
        Page<Task> pageTaskToConverter = new PageImpl<>(List.of(TaskFake.fake()));

        Page<TaskVO> pageTaskConverted = TaskConverter.of().toPageVO(pageTaskToConverter);

        assertNotNull(pageTaskConverted);
        assertThat(pageTaskConverted.getContent()).isNotEmpty();
        assertEquals(pageTaskToConverter.getTotalElements(), pageTaskConverted.getTotalElements());
    }

    @Test
    void whenConvertPageTask_thenReturnPageEmpty() {
        Page<Task> pageTaskToConverter = new PageImpl<>(List.of());

        Page<TaskVO> pageTaskConverted = TaskConverter.of().toPageVO(pageTaskToConverter);

        assertNotNull(pageTaskConverted);
        assertThat(pageTaskConverted.getContent()).isEmpty();
        assertEquals(0, pageTaskConverted.getTotalElements());
    }
}
