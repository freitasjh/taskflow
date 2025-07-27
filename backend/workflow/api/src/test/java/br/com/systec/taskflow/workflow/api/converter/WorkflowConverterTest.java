package br.com.systec.taskflow.workflow.api.converter;

import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.model.Workflow;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import br.com.systec.taskflow.workflow.api.fake.StatusFake;
import br.com.systec.taskflow.workflow.api.fake.WorkflowFake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extensions;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class WorkflowConverterTest {

    @Test
    void whenConvertWorkflowVO_thenReturnWorkflow() {
        StatusVO statusVO = StatusFake.toFakeVO();
        List<StatusVO> listStatusToWorkflow = List.of(statusVO);
        List<Status> listStatus = StatusConverter.toList(listStatusToWorkflow);

        WorkflowVO workflowVO = WorkflowFake.toFakeVO();
        workflowVO.setListOfStatus(listStatusToWorkflow);

        Workflow workflow = WorkflowConverter.toModel(workflowVO);

        // Then
        assertNotNull(workflow);
        assertEquals(workflowVO.getId(), workflow.getId());
        assertEquals(workflowVO.getName(), workflow.getName());
        assertEquals(workflowVO.getProjectId(), workflow.getProjectId());
        assertNotNull(workflow.getListOfStatus());
        assertFalse(workflow.getListOfStatus().isEmpty());
        assertEquals(1, workflow.getListOfStatus().size());
        assertEquals(listStatus.get(0).getId(), workflow.getListOfStatus().get(0).getId());
        assertEquals(listStatus.get(0).getName(), workflow.getListOfStatus().get(0).getName());
    }


    @Test
    void whenConvertWorkflowVOToWorkflow_thenListStatusIsNull_shouldReturnListStatusEmpty() {
        WorkflowVO workflowVO = WorkflowFake.toFakeVO();

        Workflow workflow = WorkflowConverter.toModel(workflowVO);

        // Then
        assertNotNull(workflow);
        assertNotNull(workflow.getListOfStatus());
        assertTrue(workflow.getListOfStatus().isEmpty());
    }

    @Test
    void whenConvertWorkflow_theReturnWorkflowVO() {
        List<Status> listOfStatus = List.of(StatusFake.toFake());
        List<StatusVO> listOfStatusVO = StatusConverter.toVOList(listOfStatus);

        Workflow workflow = WorkflowFake.toFake();
        workflow.setListOfStatus(listOfStatus);

        try (MockedStatic<StatusConverter> mockedStatusConverter = mockStatic(StatusConverter.class)) {
            mockedStatusConverter.when(() -> StatusConverter.toVOList(listOfStatus)).thenReturn(listOfStatusVO);

            WorkflowVO workflowVO = WorkflowConverter.toVO(workflow);

            assertNotNull(workflowVO);
            assertEquals(workflow.getId(), workflowVO.getId());
            assertEquals(workflow.getName(), workflowVO.getName());
            assertEquals(workflow.getProjectId(), workflowVO.getProjectId());
            assertNotNull(workflowVO.getListOfStatus());
            assertFalse(workflowVO.getListOfStatus().isEmpty());
            assertEquals(1, workflow.getListOfStatus().size());
            assertEquals(listOfStatus.get(0).getId(), workflowVO.getListOfStatus().get(0).getId());
            assertEquals(listOfStatus.get(0).getName(), workflowVO.getListOfStatus().get(0).getName());
        }
    }
}
