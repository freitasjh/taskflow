package br.com.systec.taskflow.kanban.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.kanban.api.filter.KanbanFilter;
import br.com.systec.taskflow.kanban.api.service.KanbanService;
import br.com.systec.taskflow.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.kanban.v1.dto.KanbanInputDTO;
import br.com.systec.taskflow.kanban.v1.dto.KanbanResponseDTO;
import br.com.systec.taskflow.kanban.v1.mapper.KanbandMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1+"/kanban-boards")
@Tag(name = "Kanban Board", description = "Cadastro de Board de Kanban")
@SecurityRequirement(name = "Authorization")
public class KanbanBoardControllerV1 extends AbstractController {

    private final KanbanService service;

    public KanbanBoardControllerV1(KanbanService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Salva um novo kanban")
    public ResponseEntity<KanbanInputDTO> create(@RequestBody @Valid KanbanInputDTO inputDTO){
        KanbanVO kanbanToSave = KanbandMapper.of().toVO(inputDTO);

        KanbanVO kanbanSaved = service.create(kanbanToSave);

        return buildSuccessResponse(KanbandMapper.of().toInputDTO(kanbanSaved));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Atualiza os dados do Kanban")
    public ResponseEntity<KanbanInputDTO> update(@RequestBody @Valid KanbanInputDTO inputDTO) {
        KanbanVO kanbanToSave = KanbandMapper.of().toVO(inputDTO);

        KanbanVO kanbanSaved = service.update(kanbanToSave);

        return buildSuccessResponse(KanbandMapper.of().toInputDTO(kanbanSaved));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retorna os dados do Kanban")
    public ResponseEntity<KanbanInputDTO> findById(@PathVariable("id") Long id) {
        KanbanVO kanbanReturn = service.findById(id);

        return buildSuccessResponse(KanbandMapper.of().toInputDTO(kanbanReturn));
    }

    @GetMapping(value = "/filter")
    @Operation(description = "Pesquisa o kanban por filtros")
    public ResponseEntity<Page<KanbanResponseDTO>> findByFilter(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                @RequestParam(value = "limit", defaultValue = "30", required = false) int limit,
                                                                @RequestParam(value = "keyword", required = false) String keyword) {
        KanbanFilter filter = new KanbanFilter(limit, page, keyword);

        Page<KanbanVO> pageKanbanResult = service.findByFilter(filter);

        return buildSuccessResponse(KanbandMapper.of().toResponse(pageKanbanResult));
    }
}
