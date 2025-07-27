package br.com.systec.taskflow.workflow.api.converter;

import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StatusConverterTest {

    @Test
    void testToVO_ConvertsStatusToStatusVO() {
        // Arrange
        Status status = new Status();
        status.setId(1L);
        status.setName("In Progress");
        status.setDescription("Task in progress");
        status.setOrder(2);

        // Act
        StatusVO statusVO = StatusConverter.toVO(status);

        // Assert
        assertNotNull(statusVO);
        assertEquals(1L, statusVO.getId());
        assertEquals("In Progress", statusVO.getName());
        assertEquals("Task in progress", statusVO.getDescription());
        assertEquals(2, statusVO.getOrder());
    }

    @Test
    void testToVO_NullStatus_ReturnsEmptyStatusVO() {
        // Act
        StatusVO statusVO = StatusConverter.toVO(null);

        // Assert
        assertNotNull(statusVO);
        assertNull(statusVO.getId());
        assertNull(statusVO.getName());
        assertNull(statusVO.getDescription());
        assertEquals(0, statusVO.getOrder());
    }

    @Test
    void testToModel_ConvertsStatusVOToStatus() {
        // Arrange
        StatusVO statusVO = new StatusVO();
        statusVO.setId(1L);
        statusVO.setName("Completed");
        statusVO.setDescription("Task completed");
        statusVO.setOrder(3);

        // Act
        Status status = StatusConverter.toModel(statusVO);

        // Assert
        assertNotNull(status);
        assertEquals(1L, status.getId());
        assertEquals("Completed", status.getName());
        assertEquals("Task completed", status.getDescription());
        assertEquals(3, status.getOrder());
    }

    @Test
    void testToModel_NullStatusVO_ReturnsEmptyStatus() {
        // Act
        Status status = StatusConverter.toModel(null);

        // Assert
        assertNotNull(status);
        assertNull(status.getId());
        assertNull(status.getName());
        assertNull(status.getDescription());
        assertEquals(0, status.getOrder());
    }

    @Test
    void testToVOList_ConvertsListOfStatusToListOfStatusVO() {
        // Arrange
        Status status1 = new Status();
        status1.setId(1L);
        status1.setName("To Do");
        status1.setDescription("Task to do");
        status1.setOrder(1);

        Status status2 = new Status();
        status2.setId(2L);
        status2.setName("Done");
        status2.setDescription("Task done");
        status2.setOrder(2);

        List<Status> statusList = List.of(status1, status2);

        // Act
        List<StatusVO> statusVOList = StatusConverter.toVOList(statusList);

        // Assert
        assertNotNull(statusVOList);
        assertEquals(2, statusVOList.size());

        StatusVO vo1 = statusVOList.get(0);
        assertEquals(1L, vo1.getId());
        assertEquals("To Do", vo1.getName());
        assertEquals("Task to do", vo1.getDescription());
        assertEquals(1, vo1.getOrder());

        StatusVO vo2 = statusVOList.get(1);
        assertEquals(2L, vo2.getId());
        assertEquals("Done", vo2.getName());
        assertEquals("Task done", vo2.getDescription());
        assertEquals(2, vo2.getOrder());
    }

    @Test
    void testToVOList_EmptyList_ReturnsEmptyList() {
        // Arrange
        List<Status> emptyList = List.of();

        // Act
        List<StatusVO> statusVOList = StatusConverter.toVOList(emptyList);

        // Assert
        assertNotNull(statusVOList);
        assertTrue(statusVOList.isEmpty());
    }

    @Test
    void testToVOList_NullList_ReturnsEmptyList() {
        // Act
        List<StatusVO> statusVOList = StatusConverter.toVOList(null);

        // Assert
        assertNotNull(statusVOList);
        assertTrue(statusVOList.isEmpty());
    }
}