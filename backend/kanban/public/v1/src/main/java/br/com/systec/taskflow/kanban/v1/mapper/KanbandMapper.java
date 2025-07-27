package br.com.systec.taskflow.kanban.v1.mapper;

import br.com.systec.taskflow.kanban.api.vo.KanbanVO;
import br.com.systec.taskflow.kanban.v1.dto.KanbanInputDTO;
import br.com.systec.taskflow.kanban.v1.dto.KanbanResponseDTO;
import org.springframework.data.domain.Page;

public class KanbandMapper {

    private KanbandMapper() {}

    public static KanbandMapper of() {
        return new KanbandMapper();
    }

    public KanbanInputDTO toInputDTO(KanbanVO kanban) {
        KanbanInputDTO inputDTO = new KanbanInputDTO();
        inputDTO.setId(kanban.getId());
        inputDTO.setName(kanban.getName());
        inputDTO.setWorkflowId(kanban.getWorkflowId());
        inputDTO.setProjectId(kanban.getProjectId());

        return inputDTO;
    }

    public KanbanVO toVO(KanbanInputDTO inputDTO) {
        KanbanVO kanban = new KanbanVO();
        kanban.setId(inputDTO.getId());
        kanban.setName(inputDTO.getName());
        kanban.setWorkflowId(inputDTO.getWorkflowId());
        kanban.setProjectId(inputDTO.getProjectId());

        return kanban;
    }

    private KanbanResponseDTO toResponseDTO(KanbanVO kanbanVO) {
        KanbanResponseDTO responseDTO = new KanbanResponseDTO();
        responseDTO.setId(kanbanVO.getId());
        responseDTO.setName(kanbanVO.getName());

        return responseDTO;
    }

    public Page<KanbanResponseDTO> toResponse(Page<KanbanVO> pageResult) {
        return pageResult.map(this::toResponseDTO);
    }

}
