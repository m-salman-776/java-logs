package LLD.Playlist.Entities;
import LLD.Playlist.PlayStrategy;

import java.util.List;

public class SeguentialPlayStrategy implements PlayStrategy {
    @Override
    public Song getNextSong(Playlist playlist, Song currentSong) {
        List<Song> songs = playlist.getSongs();
        int currentIndex = songs.indexOf(currentSong);
        if (currentIndex == -1 || currentIndex + 1 >= songs.size()) {
            return null; // No next song
        }
        return songs.get(currentIndex + 1);
    }
}
