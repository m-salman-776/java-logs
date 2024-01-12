package LLD.MoviesBooking.Classes;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    String name;
    List<Actor> actorList;
    public Movie(String name){
        this.name = name;
        this.actorList = new ArrayList<>();
    }
    public void addActor(String name){
        this.actorList.add(new Actor(name));
    }
}
