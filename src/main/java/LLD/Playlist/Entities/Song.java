package LLD.Playlist.Entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Song{
    int songId;
    String title;
    String artist;
    String album;
    int duration; // in seconds
    Song(int songId, String title, String artist, String album, int duration){
        this.songId = songId;
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }
}
