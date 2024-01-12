package LLD.MoviesBooking.Classes;

import lombok.Getter;

@Getter
public class User {
    Long useId;
    public String name;
    public String email;
    public User(String name,String email){
        this.name = name;
        this.email = email;
    }
}
