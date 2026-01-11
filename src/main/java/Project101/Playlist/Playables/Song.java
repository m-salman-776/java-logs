package Project101.Playlist.Playables;
import java.util.HashMap;
import java.util.Map;

public class Song implements Playable{
    private int id;
    private String title;
    private int duration;
    private Map<String,String> attributes;

    public Song(int id, String title, int duration_sec){
        this.id = id;
        this.title = title;
        this.duration = duration_sec;
        this.attributes = new HashMap<>();
    }

    public void setAttribute(String key,String val){
        this.attributes.put(key,val);
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getTitle() { return title; }

    @Override
    public int getDuration() { return duration; }

    @Override
    public void play() {
        System.out.println("Playing Song: " + title);
    }
}
