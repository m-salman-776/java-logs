package ContestManagement.Models;

import lombok.Getter;

@Getter
public class Department {
    private long id;
    private String name;
    public Department(long id, String name){
        this.id = id;
        this.name = name;
    }
}
