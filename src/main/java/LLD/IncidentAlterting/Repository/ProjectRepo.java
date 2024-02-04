package LLD.IncidentAlterting.Repository;

import LLD.IncidentAlterting.Classes.Project;

import java.util.HashMap;
import java.util.Map;

public class ProjectRepo<K,V> {
    Map<String, Project> projectMap;
    ProjectRepo(){
        projectMap = new HashMap<>();
    }

    public boolean addProject(String name,int id,String dest){
        // TODO validate input
        Project project = new Project(name,dest);
        projectMap.put(project.getName(),project);
        return true;
    }

    public Project getProject(String name){
        if (!this.projectMap.containsKey(name)) return null;
        return projectMap.get(name);
    }
}
