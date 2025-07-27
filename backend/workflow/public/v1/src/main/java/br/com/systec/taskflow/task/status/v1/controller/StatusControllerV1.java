package br.com.systec.taskflow.task.status.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.commons.exceptions.ValidationError;
import br.com.systec.taskflow.workflow.api.service.StatusService;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.task.status.v1.dto.StatusInputDTO;
import br.com.systec.taskflow.task.status.v1.dto.StatusResponseDTO;
import br.com.systec.taskflow.task.status.v1.mapper.StatusMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

import java.util.List;

@RestController
@RequestMapping(RestPath.V1 + "/workflow-status")
@Tag(name = "Status de tarefa", description = "Cadastro de status de tarefa")
@SecurityRequirement(name = "Authorization")
public class StatusControllerV1 extends AbstractController {

    private final StatusService service;

    public StatusControllerV1(StatusService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastro de status de tarefa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<StatusInputDTO> create(@RequestBody @Valid StatusInputDTO inputDTO) {
        StatusVO statusToSave = StatusMapper.toVO(inputDTO);

        StatusVO statusSaved = service.create(statusToSave);

        return buildSuccessResponse(StatusMapper.toInputDTO(statusSaved));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Atualiza status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<StatusInputDTO> update(@RequestBody @Valid StatusInputDTO inputDTO) {
        StatusVO statusToUpdate = StatusMapper.toVO(inputDTO);
        StatusVO statusSaved = service.update(statusToUpdate);

        return buildSuccessResponse(StatusMapper.toInputDTO(statusSaved));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Retorna todos os status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = List.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<List<StatusResponseDTO>> findAll() {
        List<StatusVO> listAllStatus = service.findAll();

        return buildSuccessResponse(StatusMapper.toList(listAllStatus));
    }

}
