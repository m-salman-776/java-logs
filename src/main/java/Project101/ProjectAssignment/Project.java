package Project101.ProjectAssignment;

public class Project {
    public int id;
    Category category;
    int leadId;
    ProjectStatus status;
    public Project(int id,int leadId,Category category){
        this.id = id;
        this.leadId = leadId;
        this.category = category;
        this.status = ProjectStatus.OPEN;
    }
    public ProjectStatus getProjectStatus(){
        return this.status;
    }
    public void setProjectStatus(ProjectStatus projectStatus){
        this.status = projectStatus;
    }
    public int getLeadId(){
        return this.leadId;
    }
    @Override
    public String toString(){
        return String.format("Project: %d is created by %d in the category %s and current status : %s",this.id,this.leadId,this.category,this.status);
    }
}
