package br.com.systec.taskflow.workflow.jms;

import br.com.systec.taskflow.workflow.api.vo.StatusVO;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "listStatus")
public class StatusTemplate {

    @XmlElement(name ="status")
    private List<StatusVO> listStatus;

    public List<StatusVO> getListStatus() {
        return listStatus;
    }

    public void setListStatus(List<StatusVO> listStatus) {
        this.listStatus = listStatus;
    }
}
