package LLD.Playlist.Entities;
import java.util.Set;

public class User {
    int userId;
    String name;
    String email;
    Set<Playlist> playlists;
    User(int userId, String name, String email){
        this.userId = userId;
        this.name = name;
        this.email = email;
    }
}
