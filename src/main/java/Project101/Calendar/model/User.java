package Project101.Calendar.model;

public class User {
    String id;
    String name;

    public User(String userId){
        this.id = userId;
    }

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
