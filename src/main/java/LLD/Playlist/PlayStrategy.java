package LLD.Playlist;
import LLD.Playlist.Entities.Playlist;
import LLD.Playlist.Entities.Song;

public interface PlayStrategy {
    Song getNextSong(Playlist playlist, Song currentSong);
}
