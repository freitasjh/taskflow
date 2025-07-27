package br.com.systec.taskflow.task.status.v1.mapper;

import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;
import br.com.systec.taskflow.task.status.v1.dto.StatusInputDTO;
import br.com.systec.taskflow.task.status.v1.dto.StatusResponseDTO;
import org.hibernate.validator.internal.engine.messageinterpolation.parser.EscapedState;

import java.util.List;

public class StatusMapper {

    private StatusMapper(){}

    public static StatusVO toVO(StatusInputDTO inputDTO) {
        StatusVO status = new StatusVO();
        status.setId(inputDTO.getId());
        status.setName(inputDTO.getName());
        status.setDescription(inputDTO.getDescription());
        status.setOrder(inputDTO.getOrder());
        status.setInitial(inputDTO.isInitial());
        status.setFinish(inputDTO.isFinish());

        if(inputDTO.getWorkflowId() != null) {
            status.setWorkflow(new WorkflowVO(inputDTO.getId()));

        }

        return status;
    }

    public static StatusInputDTO toInputDTO(StatusVO statusVO) {
        StatusInputDTO inputDTO = new StatusInputDTO();
        inputDTO.setId(statusVO.getId());
        inputDTO.setName(statusVO.getName());
        inputDTO.setDescription(statusVO.getDescription());
        inputDTO.setOrder(statusVO.getOrder());
        inputDTO.setInitial(statusVO.isInitial());
        inputDTO.setFinish(statusVO.isFinish());

        if(statusVO.getWorkflow() != null) {
            inputDTO.setWorkflowId(statusVO.getWorkflow().getId());
        }

        return inputDTO;
    }

    public static StatusResponseDTO toResponse(StatusVO statusVO) {
        StatusResponseDTO responseDTO = new StatusResponseDTO();
        responseDTO.setId(statusVO.getId());
        responseDTO.setName(statusVO.getName());
        responseDTO.setOrder(statusVO.getOrder());

        return responseDTO;
    }

    public static List<StatusResponseDTO> toList(List<StatusVO> listSatus) {
        return listSatus.stream().map(StatusMapper::toResponse).toList();
    }

}
