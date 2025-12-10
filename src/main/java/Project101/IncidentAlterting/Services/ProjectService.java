package Project101.IncidentAlterting.Services;

import Project101.IncidentAlterting.Classes.Project;
import Project101.IncidentAlterting.Repository.ProjectRepo;

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
