package ContestManagement.Repositories;


import ContestManagement.Models.Contestant;

import java.util.ArrayList;
import java.util.List;

public class ContestantRepository {
    private List<Contestant> contestantList = new ArrayList<>(); // base

    public void addContestant(Contestant contestant) {
        contestantList.add(contestant);
    }

    public List<Contestant> getAllContestants() {
        return new ArrayList<>(contestantList);
    }
}
