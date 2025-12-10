package Project101.IncidentAlterting.Classes;

import lombok.Getter;

@Getter
public class Employee {
    int id;
    String name;
    int mobileNumer;
    String email;
    public Employee(int id, String name, int number, String email){
        this.id = id;
        this.name = name;
        this.mobileNumer = number;
        this.email = email;
    }
}
