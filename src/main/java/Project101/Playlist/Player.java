package Project101.Playlist;

import Project101.Playlist.Playables.Playable;
import Project101.Playlist.Playback.PlaybackStrategy;
import Project101.Playlist.Playback.SequentialStrategy;
import Project101.Playlist.PlayerState.BasePlayerState;
import Project101.Playlist.PlayerState.PlayState;
import Project101.Playlist.PlayerState.PlayerState;
import Project101.Playlist.PlayerState.StoppedState;

public class Player {
    private Playlist currentPlaylist;
    private int currentIndex;
    private PlayerState playerState;
    private PlaybackStrategy playbackStrategy;

    public Player(Playlist playlist) {
        this.currentPlaylist = playlist;
        this.currentIndex = 0;
        this.playbackStrategy = new SequentialStrategy();
        this.playerState = new StoppedState();
    }
    public void changeState(PlayState playerState){
        this.playerState = playerState;
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
        this.currentIndex = this.playbackStrategy.getNextItemIndex(this.currentIndex,this.currentPlaylist.getItems().size());
        if (currentPlaylist != null && !currentPlaylist.getItems().isEmpty()){
            Playable item = currentPlaylist.getItems().get(currentIndex);
            item.play();
        }
    }

    public void previous() {
        if (currentPlaylist != null && !currentPlaylist.getItems().isEmpty()) {
            currentIndex = (currentIndex - 1 + currentPlaylist.getItems().size()) % currentPlaylist.getItems().size();
            Playable item = currentPlaylist.getItems().get(currentIndex);
            item.play();
        }
    }

    public void setPlaylist(Playlist playlist) {
        this.currentPlaylist = playlist;
        this.currentIndex = 0;
    }
    public void playCurrentItem() {
        Playable item = currentPlaylist.getItems().get(currentIndex);

    }
    public void stopPlayback() {

    }
    public void resumePlayback(){

    }
    public void changePlaybackStrategy(PlaybackStrategy playbackStrategy){
        this.playbackStrategy = playbackStrategy;
    }
}
