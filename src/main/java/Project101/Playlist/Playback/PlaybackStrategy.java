package Project101.Playlist.Playback;

public interface PlaybackStrategy {
    int getNextItemIndex(int currentIndex,int playlistSize);
}
