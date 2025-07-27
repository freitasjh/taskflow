package br.com.systec.taskflow.project.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.project.api.filter.ProjectStatusFilter;
import br.com.systec.taskflow.project.api.service.ProjectStatusService;
import br.com.systec.taskflow.project.api.vo.ProjectStatusVO;
import br.com.systec.taskflow.project.v1.dto.ProjectStatusInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectStatusResponseDTO;
import br.com.systec.taskflow.project.v1.mapper.ProjectStatusMapper;
import br.com.systec.taskflow.security.permission.HasPermission;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import java.util.List;

@RestController
@RequestMapping(RestPath.V1 + "/project-status")
@Tag(name = "Project Status", description = "Cadastro de status de Projeto")
public class ProjectStatusControllerV1 extends AbstractController {

    private final ProjectStatusService service;

    public ProjectStatusControllerV1(ProjectStatusService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Realiza o cadastro de status para Projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o status de projeto cadastrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_CREATE")
    public ResponseEntity<ProjectStatusInputDTO> create(@RequestBody @Valid ProjectStatusInputDTO inputDTO) {
        ProjectStatusVO projectStatusVO = ProjectStatusMapper.toVO(inputDTO);

        ProjectStatusVO projectStatusVOSaved = service.create(projectStatusVO);

        return buildSuccessResponse(ProjectStatusMapper.toInput(projectStatusVOSaved));
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Realiza a atualização do cadastro de status de projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o status de projeto atualizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_MODIFY")
    public ResponseEntity<ProjectStatusInputDTO> update(@RequestBody @Valid ProjectStatusInputDTO inputDTO) {
        ProjectStatusVO projectStatusVO = ProjectStatusMapper.toVO(inputDTO);

        ProjectStatusVO projectStatusUpdated = service.update(projectStatusVO);

        return buildSuccessResponse(ProjectStatusMapper.toInput(projectStatusUpdated));
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Retorna todos os status cadastrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna odos os cadastros", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_CREATE")
    public ResponseEntity<List<ProjectStatusResponseDTO>> findAll() {
        List<ProjectStatusVO> listProjectStatus = service.findAll();

        return buildSuccessResponse(ProjectStatusMapper.toListResponse(listProjectStatus));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Retorna o status filtrado por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o Status de Projeto", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusResponseDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_READ")
    public ResponseEntity<ProjectStatusInputDTO> findById(@PathVariable("id") Long id) {
        ProjectStatusVO projectStatus = service.findById(id);

        return buildSuccessResponse(ProjectStatusMapper.toInput(projectStatus));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a pesquisa de status para projeto por filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as status de projeto cadastrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_READ")
    public ResponseEntity<Page<ProjectStatusResponseDTO>> findByFilter(@RequestParam(value = "limit", defaultValue = "30") int limit,
                                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        ProjectStatusFilter filter = new ProjectStatusFilter(limit, page, keyword);

        Page<ProjectStatusVO> listStatus = service.findByFilter(filter);

        return buildSuccessResponse(ProjectStatusMapper.toPage(listStatus));
    }
}
