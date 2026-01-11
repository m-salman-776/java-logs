package Project101.Playlist;

import Project101.Playlist.Playables.Playable;
import Models.User;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Playlist {
    String id;
    String name;
    String description;
    String user;
    boolean isPublic;
    private final List<Playable> items; // Removed @Getter from class level for this field effectively

    public Playlist(String name,String user,String description) {
        this.name = name;
        this.description = description;
        this.isPublic = false;
        this.user = user;
        this.items = new ArrayList<>();
    }
    public void addItem(Playable item){
        items.add(item);
    }
    public void removeItem(Playable item){
        items.remove(item);
    }
    
    // Return unmodifiable list to protect internal state
    public List<Playable> getItems() {
        return Collections.unmodifiableList(items);
    }
}
