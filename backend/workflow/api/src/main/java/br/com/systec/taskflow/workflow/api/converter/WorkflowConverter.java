package br.com.systec.taskflow.workflow.api.converter;

import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.model.Workflow;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;


public class WorkflowConverter {


    private WorkflowConverter() {
    }

    public static Workflow toModel(WorkflowVO workflowVO) {
        Workflow workflow = new Workflow();
        workflow.setId(workflowVO.getId());
        workflow.setName(workflowVO.getName());
        workflow.setProjectId(workflowVO.getProjectId());

        if (workflowVO.getListOfStatus() != null && !workflowVO.getListOfStatus().isEmpty()) {
            for (StatusVO statusVO : workflowVO.getListOfStatus()) {
                Status status = StatusConverter.toModel(statusVO);
                if(statusVO.getId() == null) {
                    workflow.addStatus(status);
                } else {
                    workflow.getListOfStatus().add(status);
                }
            }
        }

        return workflow;
    }

    public static WorkflowVO toVO(Workflow workflow) {
        WorkflowVO workflowVO = new WorkflowVO();

        workflowVO.setId(workflow.getId());
        workflowVO.setName(workflow.getName());
        workflowVO.setProjectId(workflow.getProjectId());
        workflowVO.setListOfStatus(StatusConverter.toVOList(workflow.getListOfStatus()));

        return workflowVO;
    }

}