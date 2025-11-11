package LLD.Playlist.Entities;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Playlist {
    int playlistId;
    boolean isPublic;
    String name;
    User createdBy;
    List<Song> songs;
    Playlist(int playlistId, boolean isPublic, String name, User createdBy, List<Song> songs){
        this.playlistId = playlistId;
        this.isPublic = isPublic;
        this.name = name;
        this.createdBy = createdBy;
        this.songs = songs == null ? new java.util.ArrayList<>() : songs;
    }
    void addSong(Song song){
        this.songs.add(song);
    }
    void removeSong(Song song) {
        this.songs.remove(song);
    }
    void test(Song song){
        this.songs.remove(song);
    }
}
