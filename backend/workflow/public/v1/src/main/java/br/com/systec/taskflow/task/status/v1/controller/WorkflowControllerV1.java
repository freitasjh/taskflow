package br.com.systec.taskflow.task.status.v1.controller;

import br.com.systec.taskflow.commons.controller.AbstractController;
import br.com.systec.taskflow.commons.controller.RestPath;
import br.com.systec.taskflow.task.status.v1.dto.WorkflowResponseDTO;
import br.com.systec.taskflow.workflow.api.filter.WorkflowFilter;
import br.com.systec.taskflow.workflow.api.service.WorkflowService;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import br.com.systec.taskflow.task.status.v1.dto.WorkflowInputDTO;
import br.com.systec.taskflow.task.status.v1.mapper.WorkflowMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping(RestPath.V1+"/workflows")
@Tag(name = "Workflow de tarefas", description = "Cadastro de worflow de tarefas")
@SecurityRequirement(name = "Authorization")
public class WorkflowControllerV1 extends AbstractController {

    private final WorkflowService service;

    public WorkflowControllerV1(WorkflowService service) {
        this.service = service;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Cadastro de worfklow")
    public ResponseEntity<WorkflowInputDTO> create(@RequestBody WorkflowInputDTO inputDTO) {
        WorkflowVO workflow = WorkflowMapper.of().toVO(inputDTO);

        WorkflowVO workflowSaved = service.create(workflow);

        return buildSuccessResponse(WorkflowMapper.of().toInputDTO(workflowSaved));
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Atualização de worfklow")
    public ResponseEntity<WorkflowInputDTO> update(@RequestBody WorkflowInputDTO inputDTO) {
        WorkflowVO workflow = WorkflowMapper.of().toVO(inputDTO);

        WorkflowVO workflowUpdate = service.update(workflow);

        return buildSuccessResponse(WorkflowMapper.of().toInputDTO(workflowUpdate));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Pesquisa Workflow pelo ID")
    public ResponseEntity<WorkflowInputDTO> findById(@PathVariable("id") Long id) {
        WorkflowVO workflowVO = service.findById(id);

        return buildSuccessResponse(WorkflowMapper.of().toInputDTO(workflowVO));
    }

    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(description = "Realiza pesquisa de workflow por filtro")
    public ResponseEntity<Page<WorkflowResponseDTO>> findByFilter(@RequestParam(value = "page", defaultValue = "0", required = false) int page,
                                                                  @RequestParam(value = "limit", defaultValue = "30", required = false) int limit,
                                                                  @RequestParam(value = "keyword", required = false) String keyword) {
        WorkflowFilter filter = new WorkflowFilter(limit, page, keyword);
        Page<WorkflowVO> pageWorkflowResult = service.findByFilter(filter);

        return buildSuccessResponse(WorkflowMapper.of().toPageResponse(pageWorkflowResult));
    }
}
