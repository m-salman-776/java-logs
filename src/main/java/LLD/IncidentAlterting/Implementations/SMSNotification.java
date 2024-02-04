package LLD.IncidentAlterting.Implementations;

import LLD.IncidentAlterting.Classes.Employee;
import LLD.IncidentAlterting.Classes.Incident;
import LLD.IncidentAlterting.Classes.Notification;

public class SMSNotification implements Notification {
    @Override
    public void sendNotification(Incident incident, Employee employee) {
        System.out.println("Sending SMS Notification to : "+employee.getName() + " issue" + incident.getDesc());
    }
}
