package br.com.systec.taskflow.team.v1.dto;

import java.io.Serial;
import java.io.Serializable;

public class TeamResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8269423283950977146L;

    private Long id;
    private String name;
    private int totalMembers;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(int totalMembers) {
        this.totalMembers = totalMembers;
    }
}
