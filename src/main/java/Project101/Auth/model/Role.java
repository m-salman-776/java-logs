package Project101.Auth.model;

import lombok.Getter;

@Getter
public class Role {
    private final String id;
    private final String name;

    public Role(String id, String name) {
        this.id = id;
        this.name = name;
    }

}
