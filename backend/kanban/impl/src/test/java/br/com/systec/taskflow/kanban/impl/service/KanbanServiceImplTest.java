package br.com.systec.taskflow.kanban.impl.service;

import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.i18n.I18nTranslate;
import br.com.systec.taskflow.kanban.api.exceptions.KanbanException;
import br.com.systec.taskflow.kanban.api.filter.KanbanFilter;
import br.com.systec.taskflow.kanban.api.model.Kanban;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.kanban.impl.KanbanBoardFake;
import br.com.systec.taskflow.kanban.impl.repository.KanbanRepository;
import br.com.systec.taskflow.kanban.impl.repository.KanbanRepositoryJPA;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KanbanServiceImplTest {

    @Mock
    private KanbanRepository repository;

    @Mock
    private KanbanRepositoryJPA repositoryJPA;

    @Mock
    private ResourceBundleMessageSource messageSource;
    @InjectMocks
    private I18nTranslate i18nTranslate;

    @InjectMocks
    private KanbanServiceImpl kanbanService;


    @Test
    void whenCreateKanbanBoard_thenReturnSucces() throws BaseException {
        Kanban kanbanBoardToReturn = KanbanBoardFake.toModel();

        KanbanVO kanbanToSave = KanbanBoardFake.toVO();

        when(repository.save(any(Kanban.class))).thenReturn(kanbanBoardToReturn);

        KanbanVO result = kanbanService.create(kanbanToSave);

        assertNotNull(result);
        assertEquals(kanbanToSave.getId(), result.getId());
        assertEquals(kanbanToSave.getName(), result.getName());
        assertEquals(kanbanToSave.getProjectId(), result.getProjectId());
        assertEquals(kanbanToSave.getWorkflowId(), result.getWorkflowId());

        verify(repository, times(1)).save(any(Kanban.class));
    }

    @Test
    void whenCreateKanbanBoard_thenThrowException() {
        when(repository.save(any(Kanban.class))).thenThrow(new RuntimeException("Database error"));

        assertThrows(KanbanException.class, () -> kanbanService.create(KanbanBoardFake.toVO()));
    }

    @Test
    void whenUpdateKanbanBoard_thenReturnSuccess() throws BaseException {
        Kanban kanbanBoardToReturn = KanbanBoardFake.toModel();
        KanbanVO kanbanBoardToUpdate = KanbanBoardFake.toVO();

        when(repository.findById(1L)).thenReturn(Optional.of(kanbanBoardToReturn));
        when(repository.update(any(Kanban.class))).thenReturn(kanbanBoardToReturn);

        KanbanVO result = kanbanService.update(kanbanBoardToUpdate);

        assertNotNull(result);
        assertEquals(kanbanBoardToUpdate.getId(), result.getId());
        assertEquals(kanbanBoardToUpdate.getName(), result.getName());

        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).update(any(Kanban.class));
    }

    @Test
    void whenUpdateKanbanBoard_thenThrowFindException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        KanbanException exception = assertThrows(KanbanException.class, () -> kanbanService.update(KanbanBoardFake.toVO()));
        assertEquals(I18nTranslate.toLocale("kanban.find.error"), exception.getMessage());
    }

    @Test
    void whenUpdateKanbanBoard_thenReturnKanbanException() {
        when(repository.findById(1L)).thenReturn(Optional.of(KanbanBoardFake.toModel()));
        when(repository.update(any(Kanban.class))).thenThrow(new RuntimeException("Database error"));

        KanbanException exception = assertThrows(KanbanException.class, () -> kanbanService.update(KanbanBoardFake.toVO()));
        assertEquals(I18nTranslate.toLocale("kanban.update.error"), exception.getMessage());
    }

    @Test
    void testFindByIdSuccess() throws BaseException {
        Kanban kanbanBoardToReturn = KanbanBoardFake.toModel();
        when(repository.findById(1L)).thenReturn(Optional.of(kanbanBoardToReturn));

        KanbanVO result = kanbanService.findById(1L);

        assertNotNull(result);
        assertEquals(kanbanBoardToReturn.getId(), result.getId());
        assertEquals(kanbanBoardToReturn.getName(), result.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        KanbanException exception = assertThrows(KanbanException.class, () -> kanbanService.findById(1L));
        assertEquals(I18nTranslate.toLocale("kanban.find.error"), exception.getMessage());
    }

    @Test
    void testFindByIdThrowsException() {
        when(repository.findById(1L)).thenThrow(new RuntimeException("Database error"));

        KanbanException exception = assertThrows(KanbanException.class, () -> kanbanService.findById(1L));
        assertEquals(I18nTranslate.toLocale("kanban.find.error"), exception.getMessage());
    }

    @Test
    void testFindByFilterSuccess() throws BaseException {
        KanbanFilter filter = new KanbanFilter();

        Kanban kanbanBoardToReturn = KanbanBoardFake.toModel();

        Page<Kanban> page = new PageImpl<>(Collections.singletonList(kanbanBoardToReturn));
        when(repositoryJPA.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);

        Page<KanbanVO> result = kanbanService.findByFilter(filter);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(kanbanBoardToReturn.getId(), result.getContent().get(0).getId());
        verify(repositoryJPA, times(1)).findAll(any(Specification.class), any(Pageable.class));
    }

    @Test
    void testFindByFilterThrowsException() {
        KanbanFilter filter = new KanbanFilter();
        PageRequest pageable = PageRequest.of(0, 10);

        when(repositoryJPA.findAll(any(Specification.class), eq(pageable)))
                .thenThrow(new RuntimeException("Database error"));

        KanbanException exception = assertThrows(KanbanException.class, () -> kanbanService.findByFilter(filter));
        assertEquals(I18nTranslate.toLocale("kanban.filter.error"), exception.getMessage());
    }

}
