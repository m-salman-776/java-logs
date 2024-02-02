package IncidentAlterting.Implementations;

import IncidentAlterting.Classes.Employee;
import IncidentAlterting.Classes.Incident;
import IncidentAlterting.Classes.Notification;

public class EmailNotification implements Notification {
    @Override
    public boolean sendNotification(Incident incident, Employee employee) {
        System.out.println("Sending email Notification to : "+employee.getName() + " issue" + incident.getDesc());
        return true;
    }
}
