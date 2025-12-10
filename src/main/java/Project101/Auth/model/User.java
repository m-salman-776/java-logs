package Project101.Auth.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class User {
    @Getter
    private final String id;
    @Getter
    private final String name;
    private final Map<String, String> attributes = new HashMap<>();

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setAttribute(String key, String value) {
        this.attributes.put(key, value);
    }

    public String getAttribute(String key) {
        return attributes.get(key);
    }
}
