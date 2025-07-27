package br.com.systec.taskflow.api.v1.controller;

import br.com.systec.taskflow.api.v1.dto.LoginDTO;
import br.com.systec.taskflow.api.v1.dto.LoginResponseDTO;
import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.security.service.LoginService;
import br.com.systec.taskflow.security.vo.LoginAuthenticateVO;
import br.com.systec.taskflow.security.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1+"/login")
@Tag(name = "Login", description = "Realiza a authenticação do usuario")
public class LoginControllerV1 extends AbstractController {

    private final LoginService service;

    public LoginControllerV1(LoginService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @Operation(description = "Realiza a authenticação do usuario e retorna o Token de acesso")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o token de acesso", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponseDTO.class))
            }),
            @ApiResponse(responseCode = "403", description = "Erro ao realizar o login", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
    })
    public ResponseEntity<LoginAuthenticateVO> login(@RequestBody @Valid LoginDTO loginDTO) {
        LoginAuthenticateVO loginResponse = service.login(new LoginVO(loginDTO.getUsername(), loginDTO.getPassword()));

        return buildSuccessResponse(loginResponse);
    }

}
