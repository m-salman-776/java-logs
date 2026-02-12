package Project101.ProjectAssignment;
public class Main {
    public static void main(String[] args) {
        //  TODO :Added some case in Test file this need to put in test directory
        ProjectService projectService = new ProjectService();

        // Req 1. Onboard Dev / Lead
        projectService.addUser("Lead1", UserRole.LEAD); // user id : 0
        projectService.addUser("Lead2", UserRole.LEAD); // user id : 2

        projectService.addUser("Dev1", UserRole.DEVELOPER);// user id : 3


        // 2. Post Project
        projectService.postProject(0, Category.BACKEND); // projectId = 0
        projectService.postProject(1, Category.FRONTEND); // projectId = 1

        System.out.println("Project Created: " + projectService.getProjectStatus(0));

        // 3. Request & Approve
        projectService.requestProject(3, 0);


        projectService.approveRequest(0, 0, 3);

        System.out.println("Project Assigned: " + projectService.getProjectStatus(0));


        projectService.cancelProject(1,1);
        System.out.println("Cancelling the project : " + projectService.getProject(1));


        // 4. Complete
        projectService.updateProjectStatus(3, 0, ProjectStatus.COMPLETED);
        System.out.println("Project Completed: " + projectService.getProjectStatus(0));

        projectService.scheduler.shutdown();
    }
}
