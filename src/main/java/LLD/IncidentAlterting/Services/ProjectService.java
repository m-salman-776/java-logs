package LLD.IncidentAlterting.Services;

import LLD.IncidentAlterting.Classes.Project;
import LLD.IncidentAlterting.Repository.ProjectRepo;

public class ProjectService {
    ProjectRepo projectRepo;
    ProjectService(ProjectRepo projectRepo){
        this.projectRepo = projectRepo;
    }

    public void addProject(String name, String dest){
        // TODO validate input
        this.addProject(name,dest);
    }
    public Project getProject(String name){
        return this.projectRepo.getProject(name);
    }
}
