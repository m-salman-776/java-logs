package Project101.Playlist.Playback;

import java.util.Random;

public class ShuffleStrategy implements PlaybackStrategy{
    private final Random random = new Random();
    @Override
    public int getNextItemIndex(int currentIndex, int playlistSize) {
        return random.nextInt(playlistSize);
    }
}
