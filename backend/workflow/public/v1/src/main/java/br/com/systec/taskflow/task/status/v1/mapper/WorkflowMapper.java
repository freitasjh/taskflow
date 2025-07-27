package br.com.systec.taskflow.task.status.v1.mapper;

import br.com.systec.taskflow.task.status.v1.dto.WorkflowResponseDTO;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import br.com.systec.taskflow.task.status.v1.dto.StatusInputDTO;
import br.com.systec.taskflow.task.status.v1.dto.WorkflowInputDTO;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

public class WorkflowMapper {

    private WorkflowMapper() {}

    public static WorkflowMapper of() {
        return new WorkflowMapper();
    }

    public WorkflowVO toVO(WorkflowInputDTO inputDTO) {
        WorkflowVO workflow = new WorkflowVO();

        workflow.setId(inputDTO.getId());
        workflow.setName(inputDTO.getName());
        workflow.setListOfStatus(convertListStatus(inputDTO.getListOfStatus()));

        return workflow;
    }

    private List<StatusVO> convertListStatus(List<StatusInputDTO> listStatus) {
        if(listStatus == null) {
            return new ArrayList<>();
        }
        return listStatus.stream().map(StatusMapper::toVO).toList();
    }

    public WorkflowInputDTO toInputDTO(WorkflowVO workflowVO) {
        WorkflowInputDTO inputDTO = new WorkflowInputDTO();
        inputDTO.setId(workflowVO.getId());
        inputDTO.setProjectId(workflowVO.getProjectId());
        inputDTO.setName(workflowVO.getName());
        if(workflowVO.getListOfStatus() != null) {
            inputDTO.setListOfStatus(workflowVO.getListOfStatus().stream()
                    .map(StatusMapper::toInputDTO).toList()
            );
        }

        return inputDTO;
    }

    public WorkflowResponseDTO toResponse(WorkflowVO workflow) {
        WorkflowResponseDTO responseDTO = new WorkflowResponseDTO();
        responseDTO.setId(workflow.getId());
        responseDTO.setName(workflow.getName());
        responseDTO.setTotalStatus(workflow.getListOfStatus().size());

        return responseDTO;
    }

    public Page<WorkflowResponseDTO> toPageResponse(Page<WorkflowVO> pageWorkflowResult) {
        return pageWorkflowResult.map(this::toResponse);
    }
}
