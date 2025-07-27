package br.com.systec.taskflow.workflow.api.fake;

import br.com.systec.taskflow.workflow.api.model.Workflow;
import br.com.systec.taskflow.workflow.api.vo.WorkflowVO;

public class WorkflowFake {

    public static Workflow toFake() {
        Workflow workflow = new Workflow();
        workflow.setId(1L);
        workflow.setName("Workflow 1");
        workflow.setProjectId(1L);

        return workflow;
    }

    public static WorkflowVO toFakeVO() {
        WorkflowVO workflowVO = new WorkflowVO();
        workflowVO.setId(1L);
        workflowVO.setName("Workflow 1");
        workflowVO.setProjectId(1L);

        return workflowVO;
    }


}
