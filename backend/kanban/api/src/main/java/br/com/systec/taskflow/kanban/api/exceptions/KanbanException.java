package br.com.systec.taskflow.kanban.api.exceptions;

import br.com.systec.taskflow.commons.exceptions.BaseException;

import java.io.Serial;

public class KanbanException extends BaseException {
    @Serial
    private static final long serialVersionUID = -1726847835246898487L;

    public KanbanException() {
    }

    public KanbanException(String message) {
        super(message);
    }

    public KanbanException(Throwable e) {
        super(e);
    }

    public KanbanException(String message, Throwable cause) {
        super(message, cause);
    }
}
