package Project101.IncidentAlterting.Services;

import Project101.IncidentAlterting.Classes.Incident;
import Project101.IncidentAlterting.Repository.IncidentRepo;

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
