package br.com.systec.board.kanban.api.vo;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class KanbanVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8081246326833630090L;
    private List<KanbanStatusVO> status;

    public List<KanbanStatusVO> getStatus() {
        return status;
    }

    public void setStatus(List<KanbanStatusVO> status) {
        this.status = status;
    }
}
