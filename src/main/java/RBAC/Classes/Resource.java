package RBAC.Classes;

import lombok.Getter;

public abstract class Resource {
    private String name;
    public Resource(String name) {
        this.name = name;
    }

}
