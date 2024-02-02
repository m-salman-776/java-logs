package IncidentAlterting;

import IncidentAlterting.Classes.*;
import IncidentAlterting.Services.EmployeeService;
import IncidentAlterting.Services.IncidentService;
import IncidentAlterting.Services.ProjectService;

public class IncidentAlerting {
    EmployeeService employeeService;
    ProjectService projectService;
    IncidentService incidentService;
    Notification notification;
    IncidentAlerting(EmployeeService employeeService,
                     ProjectService projectService,
                     IncidentService incidentService,
                     Notification notification){
        this.employeeService = employeeService;
        this.projectService = projectService;
        this.incidentService = incidentService;
        this.notification = notification;
    }

    public void addProject(String name){
        this.projectService.addProject(name,"domedes");
    }

    public void addEmployee(int id ,String name,String email,int phone){
        this.employeeService.addEmployee(id,name,email,phone);
    }

    public int createIncident(String projectName,String desc){
        Project project = this.projectService.getProject(projectName);
        if (project == null){
            // TODO handle exception
        }
        Incident incident = new Incident(desc);
        incident.setProject(project);
        this.incidentService.addIncident(incident);
        return incident.getId();
    }

    public void assignProject(String projectName,int employeeId){
        Project project = this.projectService.getProject(projectName);
        if (project == null){
            // TODO handle exception
        }
        project.assignProject(employeeId);
    }

    public void setLevel(String projectName, int employeeId, int level){
        Project project = this.projectService.getProject(projectName);
        if (project == null){
            // TODO handle exception
        }
        project.addEmployeeLevel(employeeId,level);
    }

    public void unsetLevel(String projectName,int level){
        Project project = this.projectService.getProject(projectName);
        if (project == null){
            // TODO handle exception
        }
        project.unsetLevel(level);
    }

    public void ackIncident(int id){
        this.incidentService.acknowledgeIncident(id);
    }

    public void escalateIncident(int id){
        this.incidentService.escalateIncident(id);
    }

    public void notifyIncident(int id){
        Incident incident = this.incidentService.getIncident(id);
        int employeeId = incident.getProject().getEmployeeIdAtLeve(incident.getCurrentLevel());
        Employee employee = this.employeeService.getEmployeeById(employeeId);
        if (employee == null){
            // TODO handle exception
        }
        this.notification.sendNotification(incident,employee);
    }

}
