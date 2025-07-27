package br.com.systec.taskflow.task.v1.mapper;

import br.com.systec.taskflow.task.api.vo.TaskFilterResponseVO;
import br.com.systec.taskflow.task.api.vo.TaskCommentVO;
import br.com.systec.taskflow.task.api.vo.TaskVO;
import br.com.systec.taskflow.task.v1.dto.TaskInputDTO;
import br.com.systec.taskflow.task.v1.dto.TaskCommentDTO;
import br.com.systec.taskflow.task.v1.dto.TaskResponseDTO;
import org.springframework.data.domain.Page;

import java.time.LocalTime;
import java.util.ArrayList;

public class TaskMapper {

    private TaskMapper(){}

    public static TaskMapper of() {
        return new TaskMapper();
    }

    public TaskVO toVO(TaskInputDTO inputDTO){
        TaskVO task = new TaskVO();
        task.setId(inputDTO.getId());
        task.setCode(inputDTO.getCode());
        task.setDescription(inputDTO.getDescription());
        task.setObservation(inputDTO.getObservation());
        task.setCreatedBy(inputDTO.getCreatedBy());
        task.setAccountable(inputDTO.getAccountable());
        task.setTeamId(inputDTO.getTeamId());
        task.setWorkflowStatus(inputDTO.getWorkflowStatusId());
        task.setPriority(inputDTO.getPriority());
        task.setProjectId(inputDTO.getProjectId());

        return task;
    }

    public TaskInputDTO toInputDTO(TaskVO taskVO) {
        TaskInputDTO inputDTO = new TaskInputDTO();
        inputDTO.setId(taskVO.getId());
        inputDTO.setCode(taskVO.getCode());
        inputDTO.setCreatedBy(taskVO.getCreatedBy());
        inputDTO.setAccountable(taskVO.getAccountable());
        inputDTO.setDateCreate(taskVO.getDateCreate().atTime(LocalTime.now()));
        inputDTO.setDateUpdate(taskVO.getDateUpdate().atTime(LocalTime.now()));
        inputDTO.setDateClose(taskVO.getDateClose());
        inputDTO.setWorkflowStatusId(taskVO.getWorkflowStatus());
        inputDTO.setTeamId(taskVO.getTeamId());
        inputDTO.setStatus(taskVO.getStatus().name());
        inputDTO.setPriority(taskVO.getPriority());
        inputDTO.setObservation(taskVO.getObservation());
        inputDTO.setDescription(taskVO.getDescription());
        inputDTO.setProjectId(taskVO.getProjectId());

        for(TaskCommentVO item : taskVO.getListComments()) {
            TaskCommentDTO observation = new TaskCommentDTO();
            observation.setId(item.getId());
            observation.setContent(item.getContent());
            observation.setCreatedBy(item.getCreateBy());

            if (inputDTO.getListComments() == null) {
                inputDTO.setListComments(new ArrayList<>());
            }
            inputDTO.getListComments().add(observation);
        }

        return inputDTO;
    }

    public TaskResponseDTO toResponseDTO(TaskFilterResponseVO filterResponse) {
        TaskResponseDTO taskResponse = new TaskResponseDTO();
        taskResponse.setId(filterResponse.getId());
        taskResponse.setAccountableName(filterResponse.getAccountableName());
        taskResponse.setCreateByName(filterResponse.getCreateByName());
        taskResponse.setDateCreated(filterResponse.getDateCreate());
        taskResponse.setDateUpdate(filterResponse.getDateUpdate());
        taskResponse.setDescription(filterResponse.getDescription());
        taskResponse.setTeamName(filterResponse.getTeamName());
        taskResponse.setObservation(filterResponse.getObservation());
        taskResponse.setWorkflowStatusName(filterResponse.getWorkFlowStatusName());

        return taskResponse;
    }

    public Page<TaskResponseDTO> toResponsePage(Page<TaskFilterResponseVO> pageResponseResult) {
        return pageResponseResult.map(this::toResponseDTO);
    }

}
