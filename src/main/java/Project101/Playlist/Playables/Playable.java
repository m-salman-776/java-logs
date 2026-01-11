package Project101.Playlist.Playables;

public interface Playable {
    int getId();
    String getTitle();
    int getDuration(); // in seconds
    void play();
}
