package br.com.systec.taskflow.publicapi.v1.controller;

import br.com.systec.taskflow.api.model.User;
import br.com.systec.taskflow.api.service.UserService;
import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.commons.exceptions.ValidationError;
import br.com.systec.taskflow.publicapi.v1.converter.UserConverter;
import br.com.systec.taskflow.publicapi.v1.dto.UserInputDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1 + "/users")
@Tag(name = "Usuarios", description = "Cadastro de Usuarios")
@SecurityRequirement(name = "Authorization")
public class UserControllerV1 extends AbstractController {

    private final UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    public ResponseEntity<UserInputDTO> createUser(@RequestBody UserInputDTO user) {
        User userToCreate = UserConverter.toUser(user);

        User createdUser = userService.save(userToCreate);

        return buildSuccessResponse(UserConverter.toUserInputDTO(createdUser));
    }

}
