package IncidentAlterting.Classes;

import lombok.Getter;

@Getter
public class Incident {
    int id;
    String desc;
    int currentLevel;
    Status status;
    Project project;
    public Incident(String desc){
        this.status = Status.PENDING;
        this.desc = desc;
    }
    public void setCurrentLevel(int currentLevel){
        this.currentLevel = currentLevel;
        status = Status.PENDING;
    }
    public void updateStatus(Status status){
        this.status = status;
    }

    public void setProject(Project project){
        this.project = project;
    }
}
