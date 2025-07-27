package br.com.systec.taskflow.commons.exceptions;

import br.com.systec.taskflow.i18n.I18nTranslate;

import java.io.Serial;
import java.io.Serializable;

public class StandardError implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer status;
    private String msg;
    private String detailMessage;
    private Long timeStamp;

    public StandardError(Integer status, String msg, Long timeStamp) {
        super();
        this.status = status;
        this.msg = I18nTranslate.toLocale(msg);
        this.timeStamp = timeStamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDetailMessage() {
        return detailMessage;
    }

    public void setDetailMessage(String detailMessage) {
        if(detailMessage != null && !detailMessage.isEmpty()) {
            this.detailMessage = detailMessage;
        } else{
            this.detailMessage = "";
        }
    }
}