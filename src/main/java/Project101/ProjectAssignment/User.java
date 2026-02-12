package Project101.ProjectAssignment;


import java.util.concurrent.atomic.AtomicBoolean;

public class User {
    int id;
    String name;
    UserRole userRole;
    AtomicBoolean isBusy;
    int completedProject;
    double rating;
    public User(int id,String name,UserRole role){
        this.id = id;
        this.name = name;
        this.userRole = role;
        this.isBusy = new AtomicBoolean(false);
        this.rating = 0;
        this.completedProject = 0;
    }
    public boolean getIsBusy(){
        return this.isBusy.get();
    }
    public void setIsBusy(boolean busy){
        this.isBusy.set(busy);
    }
    public UserRole getUserRole(){
        return this.userRole;
    }
    public boolean compareAndSetBusy(boolean expect, boolean update) {
        return this.isBusy.compareAndSet(expect, update);
    }
    public synchronized void rate(double rating){
        this.rating += rating;
    }
    public synchronized void updateCompletedProjectCount(){
        this.completedProject += 1;
    }
}