package br.com.systec.taskflow.team.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.commons.exceptions.ValidationError;
import br.com.systec.taskflow.security.permission.HasPermission;
import br.com.systec.taskflow.team.api.filter.TeamFilter;
import br.com.systec.taskflow.team.api.service.TeamService;
import br.com.systec.taskflow.team.api.vo.TeamVO;
import br.com.systec.taskflow.team.v1.dto.TeamInputDTO;
import br.com.systec.taskflow.team.v1.dto.TeamResponseDTO;
import br.com.systec.taskflow.team.v1.mapper.TeamMapper;
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
@RequestMapping(RestPath.V1 + "/teams")
@Tag(name = "Team", description = "Cadastro de equipes")
@SecurityRequirement(name = "Authorization")
public class TeamControllerV1 extends AbstractController {

    private final TeamService service;

    public TeamControllerV1(TeamService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(description = "Cadastra uma equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a equipe cadastrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TeamInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "406", description = "Validação de informação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
    })
    @HasPermission("TEAM_CREATE")
    public ResponseEntity<TeamInputDTO> create(@RequestBody @Valid TeamInputDTO inputDTO) {
        TeamVO teamToSave = TeamMapper.toVO(inputDTO);
        TeamVO teamSaved = service.create(teamToSave);

        return buildSuccessResponse(TeamMapper.toInputDTO(teamSaved));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Atualiza uma equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a equipe cadastrado", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TeamInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "406", description = "Validação de informação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
    })
    @HasPermission("TEAM_MODIFY")
    public ResponseEntity<TeamInputDTO> update(@RequestBody @Valid TeamInputDTO inputDTO) {
        TeamVO teamToUpdate = TeamMapper.toVO(inputDTO);

        TeamVO teamUpdated = service.update(teamToUpdate);

        return buildSuccessResponse(TeamMapper.toInputDTO(teamUpdated));
    }

    @PostMapping("/members/{teamId}/{employeeId}")
    @Operation(description = "Adiciona um membro à equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("TEAM_MODIFY")
    public ResponseEntity<Void> addMember(@PathVariable("teamId") Long teamId, @PathVariable("employeeId") Long employeeId) {
        service.addMember(teamId, employeeId);

        return buildSuccessResponseNoContent();
    }

    @DeleteMapping("/members/{teamId}/{memberId}")
    @Operation(description = "Remove um membro da equipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OK"),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    @HasPermission("TEAM_MODIFY")
    public ResponseEntity<Void> removeMember(@PathVariable("teamId") Long teamId, @PathVariable("memberId") Long memberId) {
        service.removeMember(teamId, memberId);

        return buildSuccessResponseNoContent();
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Page.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TEAM_READ")
    public ResponseEntity<Page<TeamResponseDTO>> findByFilter(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                              @RequestParam(value = "limit", defaultValue = "30", required = false) int limit,
                                                              @RequestParam(value = "keyword", required = false) String keyword) {
        TeamFilter filter = new TeamFilter(limit, page, keyword);
        Page<TeamVO> teamPage = service.findByFilter(filter);

        return buildSuccessResponse(teamPage.map(TeamMapper::toResponseDTO));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Busca uma equipe por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TeamInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("TEAM_READ")
    public ResponseEntity<TeamInputDTO> findById(@PathVariable("id") Long id) {
        TeamVO team = service.findById(id);
        TeamInputDTO teamInputDTO = TeamMapper.toInputDTO(team);

        return buildSuccessResponse(teamInputDTO);
    }
}
