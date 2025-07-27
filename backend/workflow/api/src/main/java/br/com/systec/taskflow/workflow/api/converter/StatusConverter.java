package br.com.systec.taskflow.workflow.api.converter;

import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;

import java.util.ArrayList;
import java.util.List;

public class StatusConverter {

    private StatusConverter() {
        // Private constructor to prevent instantiation
    }

    public static StatusVO toVO(Status status) {
        if (status == null) {
            return new StatusVO();
        }

        StatusVO statusVO = new StatusVO();

        statusVO.setId(status.getId());
        statusVO.setName(status.getName());
        statusVO.setDescription(status.getDescription());
        statusVO.setOrder(status.getOrder());
        statusVO.setInitial(status.isInitial());
        status.setFinish(status.isFinish());

        if(status.getWorkflow() != null) {
            statusVO.setWorkflow(new WorkflowVO(status.getWorkflow().getId()));
        }

        return statusVO;
    }

    public static Status toModel(StatusVO statusVO) {
        if (statusVO == null) {
            return new Status();
        }

        Status status = new Status();
        status.setId(statusVO.getId());
        status.setName(statusVO.getName());
        status.setDescription(statusVO.getDescription());
        status.setOrder(statusVO.getOrder());
        status.setInitial(statusVO.isInitial());
        status.setFinish(status.isFinish());

        if(statusVO.getWorkflow() != null) {
            status.setWorkflow(WorkflowConverter.toModel(statusVO.getWorkflow()));
        }

        return status;
    }

    public static List<StatusVO> toVOList(List<Status> listOfStatus) {
        if (listOfStatus == null) {
            return new ArrayList<>();
        }
        return listOfStatus.stream().map(StatusConverter::toVO).toList();
    }

    public static List<Status> toList(List<StatusVO> listOfStatus) {
        if (listOfStatus == null) {
            return new ArrayList<>();
        }

        return listOfStatus.stream().map(StatusConverter::toModel).toList();
    }
}
