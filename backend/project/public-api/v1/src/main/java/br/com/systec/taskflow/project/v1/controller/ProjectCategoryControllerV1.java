package br.com.systec.taskflow.project.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.project.api.filter.ProjectCategoryFilter;
import br.com.systec.taskflow.project.api.service.ProjectCategoryService;
import br.com.systec.taskflow.project.api.vo.ProjectCategoryVO;
import br.com.systec.taskflow.project.v1.dto.ProjectCategoryInputDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectCategoryResponseDTO;
import br.com.systec.taskflow.project.v1.dto.ProjectStatusInputDTO;
import br.com.systec.taskflow.project.v1.mapper.ProjectCategoryMapper;
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
@RequestMapping(RestPath.V1 + "/project-categories")
@Tag(name = "Project Category", description = "Cadastro de catgoria de projeto")
public class ProjectCategoryControllerV1 extends AbstractController {

    private final ProjectCategoryService service;

    public ProjectCategoryControllerV1(ProjectCategoryService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza o cadastro de categoria para Projetos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a categoria de projeto cadastrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_CREATE")
    public ResponseEntity<ProjectCategoryInputDTO> create(@RequestBody @Valid ProjectCategoryInputDTO inputDTO) {
        ProjectCategoryVO projectCategory = ProjectCategoryMapper.toVO(inputDTO);

        ProjectCategoryVO projectCategorySaved = service.create(projectCategory);

        return buildSuccessResponse(ProjectCategoryMapper.toInputDTO(projectCategorySaved));
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE }, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a atualização de categoria para Projetos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a categoria de projeto atualizada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_MODIFY")
    public ResponseEntity<ProjectCategoryInputDTO> update(@RequestBody @Valid ProjectCategoryInputDTO inputDTO) {
        ProjectCategoryVO projectCategory = ProjectCategoryMapper.toVO(inputDTO);

        ProjectCategoryVO projectCategorySaved = service.update(projectCategory);

        return buildSuccessResponse(ProjectCategoryMapper.toInputDTO(projectCategorySaved));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a pesquisa de Categoria para projeto por id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a categoria de projeto cadastrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ProjectStatusInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_READ")
    public ResponseEntity<ProjectCategoryInputDTO> findById(@PathVariable("id") Long id) {
        ProjectCategoryVO projectCategorySaved = service.findById(id);

        return buildSuccessResponse(ProjectCategoryMapper.toInputDTO(projectCategorySaved));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza a pesquisa de Categoria para projeto por filtro")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as categorias de projeto cadastrada", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("PROJECT_READ")
    public ResponseEntity<Page<ProjectCategoryResponseDTO>> findByFilter(@RequestParam(value = "limit", defaultValue = "30") int limit,
                                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                                         @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        ProjectCategoryFilter filter = new ProjectCategoryFilter(limit, page, keyword);

        Page<ProjectCategoryVO> listCategory = service.findByFilter(filter);

        return buildSuccessResponse(ProjectCategoryMapper.toPageResponse(listCategory));
    }
}
