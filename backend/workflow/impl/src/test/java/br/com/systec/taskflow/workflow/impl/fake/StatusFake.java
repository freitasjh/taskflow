package br.com.systec.taskflow.workflow.impl.fake;

import br.com.systec.taskflow.workflow.api.model.Status;
import br.com.systec.taskflow.workflow.api.vo.StatusVO;

public class StatusFake {

    public static StatusVO toFakeVO() {
        StatusVO statusVO = new StatusVO();
        statusVO.setId(1L);
        statusVO.setOrder(1);
        statusVO.setName("Backlog");
        statusVO.setDescription("Status inicial para as tarefas");

        return statusVO;
    }

    public static Status toFake() {
        Status status = new Status();
        status.setId(1L);
        status.setOrder(1);
        status.setName("Backlog");
        status.setDescription("Status inicial para as tarefas");

        return status;
    }
}
