package Project101.ProjectAssignment;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProjectService {
    // Key : ProjectId
    Map<Integer, Project> projects;
    // Key : UserId
    Map<Integer, User> userMap;
    Map<Integer, Set<Integer>> projectRequests;
    // key : devId , val : projectId
    Map<Integer,Integer> projectDevMap;

    AtomicInteger projectIdGen ;
    AtomicInteger userIdGen;
    ScheduledExecutorService scheduler;

    public ProjectService(){
        projects = new ConcurrentHashMap<>();
        userMap = new ConcurrentHashMap<>();
        projectRequests=new ConcurrentHashMap<>();
        projectDevMap = new ConcurrentHashMap<>();

        projectIdGen = new AtomicInteger(0);
        userIdGen = new AtomicInteger(0);
        scheduler = Executors.newScheduledThreadPool(1);
    }

    public void addUser(String name,UserRole role){
        int userId = userIdGen.getAndIncrement();
        User user = new User(userId,name,role);
        userMap.put(userId,user);
    }

    public boolean postProject(int leadId, Category category) {
        User user = userMap.get(leadId);
        if (user == null || user.getUserRole() != UserRole.LEAD){
            System.out.printf("Operation permitted for %s role only",UserRole.LEAD);
            return false;
        }
        int projectId = projectIdGen.getAndIncrement();
        Project project = new Project(projectId, leadId, category);
        projects.put(projectId, project);

        System.out.println(project);

        scheduler.schedule(() -> cancelProject(leadId,projectId), 10, TimeUnit.SECONDS);
        return true;
    }

    public void requestProject(int devId, int projectId) {
        Project project = projects.get(projectId);
        User user = userMap.get(devId);

        if (user == null || project == null || user.getUserRole() != UserRole.DEVELOPER) {
            System.out.println("Request Can't be made");
            return;
        }

        synchronized (project) {

            if (user.getIsBusy() || (project.getProjectStatus() != ProjectStatus.OPEN && project.getProjectStatus() != ProjectStatus.REQUESTED)) {
                System.out.printf("Unable to request the Project %d by user %d ",projectId,devId);
                return;
            }
            project.setProjectStatus(ProjectStatus.REQUESTED);

            System.out.println(project + " Project Requested by : "+devId);

            projectRequests.computeIfAbsent(projectId, k -> new HashSet<>()).add(devId);
        }
    }

    public boolean approveRequest(int leadId, int projectId, int devId) {
        Project project = projects.get(projectId);
        User developer = userMap.get(devId);
        User lead = userMap.get(leadId);
        if (developer == null || lead == null || project == null){
            System.out.println("Unable to approve the project");
            return false;
        }
        if (project.getLeadId() != leadId) {
            System.out.println("Permission Denied");
            return false;
        }
        synchronized (project) {
            if (!developer.compareAndSetBusy(false, true)) {
                System.out.println("Developer is now busy elsewhere.");
                return false;
            }
            projectDevMap.put(projectId,devId);
            project.setProjectStatus(ProjectStatus.ASSIGNED);
            System.out.println(project + " Approved by " + leadId +  " to dev " + devId);
            projectRequests.remove(projectId);
        }
        return true;
    }

    public boolean updateProjectStatus(int devId, int projectId,ProjectStatus status){

        if (projectDevMap.getOrDefault(projectId,0) != devId){
            System.out.printf("Can't update status as Project %d is not assigned to %d",projectId,devId);
            return false;
        }
        Project project = projects.get(projectId);
        if (project ==  null) {
            System.out.printf("Project %d is not found",projectId);
            return false;
        }
        synchronized (project){
            project.setProjectStatus(status);
            System.out.println(project + " Updated by " + devId);
            if (status == ProjectStatus.COMPLETED) {
                User dev = userMap.get(devId);
                if (dev == null) return false;
                dev.setIsBusy(false);
                dev.updateCompletedProjectCount();
                System.out.println(project + " Completed by " + devId);
            }
        }
        return true;
    }

    public ProjectStatus getProjectStatus(int projectId){
        Project project = projects.get(projectId);
        if (project == null){
            System.out.println("Invalid Project");
            return ProjectStatus.INVALID;
        }
        return project.getProjectStatus();
    }

    public boolean cancelProject(int leadId, int projectId) {
        Project project = projects.get(projectId);
        if (project == null){
            System.out.println("Invalid Project");
            return false;
        }
        synchronized (project) {
            if (project.getLeadId() != leadId) {
                System.out.println("Permission Denied");
                return false;
            }

            if (project.getProjectStatus() == ProjectStatus.IN_PROGRESS ||
                    project.getProjectStatus() == ProjectStatus.COMPLETED ||
                    project.getProjectStatus() == ProjectStatus.ASSIGNED) {
                System.out.println("Project already assigned or in progress, can't be cancelled.");
                return false;
            }
            project.setProjectStatus(ProjectStatus.CANCELED);
            System.out.println(project + " Cancelled  by " + leadId);
            projectRequests.remove(projectId);
        }
        return true;
    }

    public Project getProject(int projectId){
        return this.projects.get(projectId);
    }

    public void rateDeveloper(int leadId,int devId,int projectId,double ratings){
        Project project = projects.get(projectId);
        User lead = userMap.get(leadId);
        User developer = userMap.get(devId);
        if (project == null || lead == null || developer == null){
            System.out.println("Unable to rate devs");
            return;
        }
        if (project.getLeadId() == leadId && projectDevMap.get(projectId) == devId){
            developer.rate(ratings);
        }
    }


    public List<Project> viewAllProject(){
        List<Project> availableProjects = new ArrayList<>();
        for (Project project : projects.values()){
            if (project.getProjectStatus() == ProjectStatus.OPEN || project.getProjectStatus() == ProjectStatus.REQUESTED){
                availableProjects.add(project);
            }
        }
        return availableProjects;
    }
}