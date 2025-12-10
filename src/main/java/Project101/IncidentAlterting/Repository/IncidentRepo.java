package Project101.IncidentAlterting.Repository;

import Project101.IncidentAlterting.Classes.Incident;
import Project101.IncidentAlterting.Classes.Status;

import java.util.HashMap;
import java.util.Map;

public class IncidentRepo {
    Map<Integer, Incident> integerIncidentMap;
    int maxLevel;
    IncidentRepo(){
        integerIncidentMap = new HashMap<>();
    }

    public boolean addIncident(Incident incident){
        integerIncidentMap.put(incident.getId(),incident);
        return true;
    }
    public Incident getIncident(int id){
        return this.integerIncidentMap.get(id);
    }
    public boolean escalateIncident(int id){
        //todo validate input
        if (!integerIncidentMap.containsKey(id)) return false;
        Incident incident = integerIncidentMap.get(id);
        incident.setCurrentLevel((incident.getCurrentLevel() + 1) % this.maxLevel);
        integerIncidentMap.put(id,incident);
        return true;
    }

    public boolean acknowledgeIncident(int id){
        if (!integerIncidentMap.containsKey(id)) return false;
        integerIncidentMap.get(id).updateStatus(Status.ACK);
        return true;
    }
}
