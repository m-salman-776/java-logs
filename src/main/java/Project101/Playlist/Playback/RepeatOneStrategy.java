package Project101.Playlist.Playback;

public class RepeatOneStrategy implements PlaybackStrategy{
    @Override
    public int getNextItemIndex(int currentIndex, int playlistSize) {
        return currentIndex;
    }
}
