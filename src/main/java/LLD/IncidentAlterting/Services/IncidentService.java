package LLD.IncidentAlterting.Services;

import LLD.IncidentAlterting.Classes.Incident;
import LLD.IncidentAlterting.Repository.IncidentRepo;

public class IncidentService {
    IncidentRepo incidentRepo;
    IncidentService(IncidentRepo repo){
        this.incidentRepo = repo;
    }

    public void addIncident(Incident incident){
        this.incidentRepo.addIncident(incident);
    }

    public Incident getIncident(int id){
        return this.incidentRepo.getIncident(id);
    }
    public boolean escalateIncident(int id){
        this.incidentRepo.escalateIncident(id);
        return true;
    }

    public boolean acknowledgeIncident(int id){
        this.incidentRepo.acknowledgeIncident(id);
        return true;
    }
}
