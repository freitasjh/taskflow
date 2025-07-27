package br.com.systec.taskflow.task.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.commons.exceptions.ValidationError;
import br.com.systec.taskflow.security.permission.HasPermission;
import br.com.systec.taskflow.task.api.filter.TaskFilter;
import br.com.systec.taskflow.task.api.service.TaskService;
import br.com.systec.taskflow.task.api.vo.TaskFilterResponseVO;
import br.com.systec.taskflow.task.api.vo.TaskVO;
import br.com.systec.taskflow.task.v1.dto.TaskCommentDTO;
import br.com.systec.taskflow.task.v1.dto.TaskInputDTO;
import br.com.systec.taskflow.task.v1.dto.TaskInputObservationDTO;
import br.com.systec.taskflow.task.v1.dto.TaskResponseDTO;
import br.com.systec.taskflow.task.v1.mapper.TaskMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/tasks")
@Tag(name = "Team", description = "Cadastro de Tarefas")
@SecurityRequirement(name = "Authorization")
public class TaskControllerV1 extends AbstractController {

    private final TaskService service;

    public TaskControllerV1(TaskService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastrar uma Tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a tarefa cadastrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem acesso", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "406", description = "Validação de informação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
    })
    @HasPermission("TASK_CREATE")
    public ResponseEntity<TaskInputDTO> create(@RequestBody @Valid TaskInputDTO inputDTO) {
        TaskVO taskToSave = TaskMapper.of().toVO(inputDTO);

        TaskVO taskSave = service.create(taskToSave);

        return buildSuccessResponse(TaskMapper.of().toInputDTO(taskSave));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Atualizar uma Tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a tarefa cadastrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskInputDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "406", description = "Validação de informação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
    })
    @HasPermission("TASK_MODIFY")
    public ResponseEntity<TaskInputDTO> update(@RequestBody @Valid TaskInputDTO inputDTO) {
        TaskVO taskToUpdate = TaskMapper.of().toVO(inputDTO);

        TaskVO taskUpdated = service.update(taskToUpdate);

        return buildSuccessResponse(TaskMapper.of().toInputDTO(taskUpdated));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Busca a tarefa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TaskInputDTO.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TASK_READ")
    public ResponseEntity<TaskInputDTO> findById(@PathVariable("id") Long id) {
        TaskVO task = service.findById(id);

        return buildSuccessResponse(TaskMapper.of().toInputDTO(task));
    }

    @GetMapping(value = "/filters")
    @Operation(description = "Busca as tarefas por filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TASK_READ")
    public ResponseEntity<Page<TaskResponseDTO>> findByFilter(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                              @RequestParam(value = "limit", defaultValue = "30", required = false) int limit,
                                                              @RequestParam(value = "keyword", required = false) String keyword) {
        TaskFilter taskFilter = new TaskFilter(limit, page, keyword);

        Page<TaskFilterResponseVO> pageTaskResult = service.findByFilter(taskFilter);

        return buildSuccessResponse(TaskMapper.of().toResponsePage(pageTaskResult));
    }

    @PostMapping(value = "/{taskId}/accountable/add/{accountableId}")
    @Operation(description = "Adiciona o responsavel da tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TASK_MODIFY")
    public ResponseEntity<Void> addAccountable(@PathVariable("taskId") Long taskId, @PathVariable("accountableId") Long accountableId) {
        service.addAccountable(accountableId, taskId);

        return buildSuccessResponseNoContent();
    }

    @PostMapping("/{taskId}/comments/add")
    @Operation(description = "Adiciona comentario na tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TASK_MODIFY")
    public ResponseEntity<Void> addComment(@PathVariable("taskId") Long taskId, @RequestBody @Valid TaskInputObservationDTO inputDTO) {
        service.addComment(inputDTO.getContent(), taskId);

        return buildSuccessResponseNoContent();
    }

    @PutMapping(value = "/{taskId}/comments/update/{commentId}")
    @Operation(description = "Atualiza o comentario da tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TASK_MODIFY")
    public ResponseEntity<Void> updateComment(@PathVariable("taskId") Long taskId, @PathVariable("commentId") Long commentId,
                                              @RequestBody @Valid TaskInputObservationDTO inputDTO) {
        service.updateComment(inputDTO.getContent(), taskId, commentId);
        return buildSuccessResponseNoContent();
    }

    @DeleteMapping(value = "/{taskId}/comments/remove/{commentId}")
    @Operation(description = "Deleta comentario da tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TASK_MODIFY")
    public ResponseEntity<Void> removeComment(@PathVariable("taskId") Long taskId, @PathVariable("commentId") Long commentId) {
        service.removeComment(commentId, taskId);

        return buildSuccessResponseNoContent();
    }

    @PostMapping(value="{taskId}/cancel")
    @Operation(description = "Cancela a tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TASK_MODIFY")
    public ResponseEntity<Void> cancelTask(@PathVariable("taskId") Long taskId, @RequestBody @Valid TaskCommentDTO taskCommentDTO) {
        service.cancelTask(taskId, taskCommentDTO.getContent());

        return buildSuccessResponseNoContent();
    }
}
