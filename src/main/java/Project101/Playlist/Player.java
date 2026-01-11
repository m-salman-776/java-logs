package Project101.Playlist;

import Project101.Playlist.Playables.Playable;
import Project101.Playlist.PlaybackStrategies.PlaybackStrategy;
import Project101.Playlist.PlaybackStrategies.SequentialStrategy;
import Project101.Playlist.PlayerState.PlayerState;
import Project101.Playlist.PlayerState.StoppedState;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private Playlist currentPlaylist;
    private int currentIndex;
    private PlayerState playerState;
    private PlaybackStrategy playbackStrategy;
    private List<Integer> playedSongList = new ArrayList<>();

    public Player() {
        this.currentPlaylist = null;
        this.currentIndex = 0;
        this.playbackStrategy = new SequentialStrategy();
        this.playerState = new StoppedState();
    }

    public void changeState(PlayerState playerState){
        this.playerState = playerState;
    }
    public void setPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        this.currentIndex = 0;
    }

    public void changePlaybackStrategy(PlaybackStrategy playbackStrategy){
        this.playbackStrategy = playbackStrategy;
    }

    public void play() {
        this.playerState.play(this);
    }
    public void pause() {
        this.playerState.pause(this);
    }
    public void stop() {
        this.playerState.stop(this);
    }

    public void next() {
        if (currentPlaylist == null || currentPlaylist.getItems().isEmpty()) return;
        this.currentIndex = this.playbackStrategy.getNextItemIndex(this.currentIndex, this.currentPlaylist.getItems().size());
        System.out.println("Skipping to next track...");
        playCurrentItem();
    }

    public void previous() {
        if (currentPlaylist != null && !currentPlaylist.getItems().isEmpty()) {
            // TODO: Use a strategy for previous as well, or history.
            currentIndex = (currentIndex - 1 + currentPlaylist.getItems().size()) % currentPlaylist.getItems().size();
            System.out.println("Skipping to previous track...");
            playCurrentItem();
        }
    }

    
    private void playCurrentItem() {
        if (currentPlaylist != null && !currentPlaylist.getItems().isEmpty()) {
            Playable item = currentPlaylist.getItems().get(currentIndex);
            System.out.println("...Rendering media...");
            item.play();
        }
    }
    
    public void stopPlayback() {
        System.out.println("...Stopping media rendering...");
        this.currentIndex = 0; // Reset to start? Or keep position? Stopped usually resets.
    }
    
    public void resumePlayback(){
        System.out.println("...Resuming media rendering...");
        playCurrentItem();
    }
    
    public void pausePlayback() {
        System.out.println("...Pausing media rendering...");
    }
}
