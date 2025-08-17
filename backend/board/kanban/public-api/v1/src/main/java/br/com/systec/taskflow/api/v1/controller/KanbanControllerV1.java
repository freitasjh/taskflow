package br.com.systec.taskflow.api.v1.controller;

import br.com.systec.board.kanban.api.service.KanbanService;
import br.com.systec.board.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1+"/kanban")
@SecurityRequirement(name = "Authorization")
@Tag(name = "Kanban", description = "Kanban Board Management")
public class KanbanControllerV1 extends AbstractController {

    private final KanbanService kanbanService;

    public KanbanControllerV1(KanbanService kanbanService) {
        this.kanbanService = kanbanService;
    }

    @GetMapping(value = "/board/{projectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get Kanban by Project ID",
               description = "Retrieve the Kanban board for a specific project by its ID.")
    public ResponseEntity<KanbanVO> findKanbanByProjectId(@PathVariable("projectId") Long projectId) {
        KanbanVO kanban = kanbanService.generateKanban(projectId);

        return buildSuccessResponse(kanban);
    }
}
