package br.com.systec.taskflow.kanban.impl.converter;

import br.com.systec.taskflow.kanban.api.model.Kanban;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KanbanBoardConverterTest {

    private KanbanConverter converter;

    @BeforeEach
    void setUp() {
        converter = KanbanConverter.of();
    }

    @Test
    void testOfCreatesInstance() {
        assertNotNull(KanbanConverter.of(), "Converter instance should not be null");
    }

    @Test
    void testToVOConvertsCorrectly() {
        Kanban kanban = new Kanban();
        kanban.setId(1L);
        kanban.setName("Test Board");
        kanban.setProjectId(2L);
        kanban.setWorkflowId(3L);

        KanbanVO result = converter.toVO(kanban);

        assertNotNull(result, "Converted VO should not be null");
        assertEquals(kanban.getId(), result.getId(), "ID should match");
        assertEquals(kanban.getName(), result.getName(), "Name should match");
        assertEquals(kanban.getProjectId(), result.getProjectId(), "Project ID should match");
        assertEquals(kanban.getWorkflowId(), result.getWorkflowId(), "Workflow ID should match");
    }

    @Test
    void testToModelConvertsCorrectly() {
        KanbanVO kanbanVO = new KanbanVO();
        kanbanVO.setId(1L);
        kanbanVO.setName("Test Board");
        kanbanVO.setProjectId(2L);
        kanbanVO.setWorkflowId(3L);

        Kanban result = converter.toModel(kanbanVO);

        assertNotNull(result, "Converted model should not be null");
        assertEquals(1L, result.getId(), "ID should match");
        assertEquals("Test Board", result.getName(), "Name should match");
        assertEquals(2L, result.getProjectId(), "Project ID should match");
        assertEquals(3L, result.getWorkflowId(), "Workflow ID should match");
    }

    @Test
    void testToVONullInput() {
        assertThrows(NullPointerException.class, () -> converter.toVO(null), 
            "Should throw NullPointerException for null input");
    }

    @Test
    void testToModelNullInput() {
        assertThrows(NullPointerException.class, () -> converter.toModel(null), 
            "Should throw NullPointerException for null input");
    }
}