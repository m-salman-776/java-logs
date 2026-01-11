package Project101.Playlist.PlaybackStrategies;

public interface PlaybackStrategy {
    int getNextItemIndex(int currentIndex,int playlistSize);
}
