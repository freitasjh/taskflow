package br.com.systec.taskflow.employee.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.commons.exceptions.BaseException;
import br.com.systec.taskflow.commons.exceptions.StandardError;
import br.com.systec.taskflow.commons.exceptions.ValidationError;
import br.com.systec.taskflow.employee.api.filter.EmployeeFilter;
import br.com.systec.taskflow.employee.api.service.EmployeeService;
import br.com.systec.taskflow.employee.api.vo.EmployeeVO;
import br.com.systec.taskflow.employee.v1.converter.EmployeeMapper;
import br.com.systec.taskflow.employee.v1.dto.EmployeeInputDTO;
import br.com.systec.taskflow.employee.v1.dto.EmployeeResponseDTO;
import br.com.systec.taskflow.security.permission.HasPermission;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RestPath.V1+ "/employees")
@Tag(name = "Funcionarios", description = "Cadastro de Funcionarios")
@SecurityRequirement(name = "Authorization")
public class EmployeeControllerV1 extends AbstractController {

    private final EmployeeService service;

    public EmployeeControllerV1(EmployeeService service) {
        this.service = service;
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeResponseDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("EMPLOYEE_CREATE")
    public ResponseEntity<EmployeeResponseDTO> save(@RequestBody @Valid EmployeeInputDTO employeeInput) throws BaseException {
        EmployeeVO employeeVO = EmployeeMapper.toVO(employeeInput);

        EmployeeVO employeeAfterSave = service.save(employeeVO);

        return buildSuccessResponse(EmployeeMapper.toResponseDTO(employeeAfterSave));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeInputDTO.class))
            }),
            @ApiResponse(responseCode = "406", description = "Erro de validação", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationError.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("EMPLOYEE_MODIFY")
    public ResponseEntity<EmployeeResponseDTO> update(@RequestBody @Valid EmployeeInputDTO employeeInput, @PathVariable(value = "id") Long id) {
        EmployeeVO employeeVO = EmployeeMapper.toVO(employeeInput);

        EmployeeVO employeeafterUpdate = service.update(employeeVO);

        return buildSuccessResponse(EmployeeMapper.toResponseDTO(employeeafterUpdate));
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission("EMPLOYEE_READ")
    public ResponseEntity<EmployeeInputDTO> findById(@PathVariable(value = "id") Long id) {
        EmployeeVO employeeVO = service.findById(id);

        return buildSuccessResponse(EmployeeMapper.toInputDTO(employeeVO));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = EmployeeInputDTO.class))
            }),
            @ApiResponse(responseCode = "401", description = "Sem permissão", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            }),
            @ApiResponse(responseCode = "500", description = "Erro generico", content = {
                    @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = StandardError.class))
            })
    })
    @HasPermission(value = "EMPLOYEE_READ")
    public ResponseEntity<Page<EmployeeResponseDTO>> findByFilter(
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "limit", defaultValue = "30", required = false) int limit,
            @RequestParam(value = "keyword", required = false) String keyword) {
        EmployeeFilter filter = new EmployeeFilter(limit, page, keyword);

        Page<EmployeeVO> employeePage = service.findByFilter(filter);

        return buildSuccessResponse(employeePage.map(EmployeeMapper::toResponseDTO));
    }
}
