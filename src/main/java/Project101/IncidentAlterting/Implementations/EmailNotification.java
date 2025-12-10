package Project101.IncidentAlterting.Implementations;

import Project101.IncidentAlterting.Classes.Employee;
import Project101.IncidentAlterting.Classes.Incident;
import Project101.IncidentAlterting.Classes.Notification;

public class EmailNotification implements Notification {
    @Override
    public void sendNotification(Incident incident, Employee employee) {
        System.out.println("Sending email Notification to : "+employee.getName() + " issue" + incident.getDesc());
    }
}
