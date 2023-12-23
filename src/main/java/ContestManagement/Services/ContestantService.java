package ContestManagement.Services;

import ContestManagement.Models.Contestant;
import ContestManagement.Repositories.ContestantRepository;

import java.util.List;

public class ContestantService {
    private ContestantRepository contestantRepository ;
    public ContestantService(ContestantRepository contestantRepository){
        this.contestantRepository = contestantRepository;
    }
    public void addContestant(Contestant contestant){
        this.contestantRepository.addContestant(contestant);
    }
    public Contestant getContestantById(Long id){
        for (Contestant contestant : contestantRepository.getAllContestants()){
            if (contestant.getId() == id){
                return contestant;
            }
        }
        return null;
    }
    // TODO calculating leader
    public List<Contestant> getAllContestant(){
        return this.contestantRepository.getAllContestants();
    }
}
