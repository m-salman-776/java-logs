package Project101.Calendar.model;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class User {
    long id;
    String name;
    Set<Long> eventSet;
    public User(long id,String name){
        this.id = id;
        this.name = name;
        this.eventSet = new HashSet<>();
    }
}
