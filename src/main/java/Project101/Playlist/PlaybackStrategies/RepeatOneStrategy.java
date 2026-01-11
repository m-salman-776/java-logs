package Project101.Playlist.PlaybackStrategies;

public class RepeatOneStrategy implements PlaybackStrategy{
    @Override
    public int getNextItemIndex(int currentIndex, int playlistSize) {
        return currentIndex;
    }
}
