package Project101.Playlist;

import Project101.Playlist.Playables.Playable;
import Models.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Playlist {
    String id;
    String name;
    String description;
    User owner;
    boolean isPublic;
    List<Playable> items;
    public Playlist(String name,User user,String description) {
        this.name = name;
        this.description = description;
        this.isPublic = false;
        this.owner = user;
        this.items = new ArrayList<>();
    }
    public void addItem(Playable item){
        items.add(item);
    }
    public void removeItem(Playable item){
        items.remove(item);
    }
}
