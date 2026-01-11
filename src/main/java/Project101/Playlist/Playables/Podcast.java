package Project101.Playlist.Playables;

public class Podcast implements Playable{
    private int id;
    private String title;
    private int duration;

    public Podcast(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    @Override
    public int getId() { return id; }

    @Override
    public String getTitle() { return title; }

    @Override
    public int getDuration() { return duration; }

    @Override
    public void play() {
        System.out.println("Playing Podcast: " + title);
    }
}
