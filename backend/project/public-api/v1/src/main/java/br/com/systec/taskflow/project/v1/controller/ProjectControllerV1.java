package br.com.systec.taskflow.project.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.project.api.filter.ProjectFilter;
import br.com.systec.taskflow.project.api.service.ProjectService;
import br.com.systec.taskflow.project.api.vo.ProjectVO;
import br.com.systec.taskflow.project.v1.dto.ProjectInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectResponseDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectStatusInputDTO;
import br.com.systec.taskflow.project.v1.mapper.ProjectMapper;
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

@RestController
@RequestMapping(RestPath.V1 + "/projects")
@Tag(name = "Project", description = "Cadastro de Projetos")
public class ProjectControllerV1 extends AbstractController {

    private final ProjectService service;

    public ProjectControllerV1(ProjectService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza o cadastro de projetos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o projeto cadastrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_CREATE")
    public ResponseEntity<ProjectInputDTO> create(@RequestBody @Valid ProjectInputDTO inputDTO) {
        ProjectVO projectToSave = ProjectMapper.toVO(inputDTO);

        ProjectVO projectSaved = service.create(projectToSave);

        return buildSuccessResponse(ProjectMapper.toInputDTO(projectSaved));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a atualização de  Projeto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o projeto atualizada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_MODIFY")
    public ResponseEntity<ProjectInputDTO> update(@RequestBody @Valid ProjectInputDTO inputDTO) {
        ProjectVO projectToSave = ProjectMapper.toVO(inputDTO);

        ProjectVO projectSaved = service.update(projectToSave);

        return buildSuccessResponse(ProjectMapper.toInputDTO(projectSaved));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a pesquisa de projeto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o projeto cadastrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_READ")
    public ResponseEntity<ProjectInputDTO> findById(@PathVariable("id") Long id) {
        ProjectVO projectSaved = service.findById(id);

        return buildSuccessResponse(ProjectMapper.toInputDTO(projectSaved));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a pesquisa de  projeto por filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os projetos cadastrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_READ")
    public ResponseEntity<Page<ProjectResponseDTO>> findByFilter(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "limit", defaultValue = "30", required = false) int limit,
            @RequestParam(value = "keyword", required = false) String keyword) {

        ProjectFilter filter = new ProjectFilter(limit, page, keyword);

        Page<ProjectVO> projectsPage = service.findByFilter(filter);
        Page<ProjectResponseDTO> responsePage = projectsPage.map(ProjectMapper::toResponseDTO);

        return buildSuccessResponse(responsePage);
    }
}
