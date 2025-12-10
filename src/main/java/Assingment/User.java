package Assingment;

import lombok.Getter;

@Getter
public class User {
    String userId;
    String name;
    public User(String name){
        this.name = name;
    }
}
