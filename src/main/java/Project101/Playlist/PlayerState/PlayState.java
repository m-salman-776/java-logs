package Project101.Playlist.PlayerState;

import Project101.Playlist.Player;

public class PlayState implements PlayerState {
    @Override
    public void play(Player player) {
        System.out.println("State: PLAYING. Already playing.");
    }

    @Override
    public void pause(Player player) {
        System.out.println("State: PLAYING -> PAUSED");
        player.pausePlayback();
        player.changeState(new PausedState());
    }

    @Override
    public void stop(Player player) {
        System.out.println("State: PLAYING -> STOPPED");
        player.stopPlayback();
        player.changeState(new StoppedState());
    }
}
