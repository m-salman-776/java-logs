package Project101.Playlist.PlaybackStrategies;

public class SequentialStrategy implements PlaybackStrategy{
    @Override
    public int getNextItemIndex(int currentIndex, int playlistSize) {
        return (currentIndex + 1) % playlistSize;
    }
}
