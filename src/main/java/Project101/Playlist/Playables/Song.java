package Project101.Playlist.Playables;
import java.util.HashMap;
import java.util.Map;

public class Song implements Playable{
    String name;
    String id;
    Map<String,String> attributes;
    public Song(String name){
        this.name = name;
        this.attributes = new HashMap<>();
    }
    public void setAttribute(String key,String val){
        this.attributes.put(key,val);
    }
    @Override
    public void play() {
        System.out.println("Playing song");
    }
}
