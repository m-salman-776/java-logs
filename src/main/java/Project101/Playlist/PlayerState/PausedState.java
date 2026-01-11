package Project101.Playlist.PlayerState;

import Project101.Playlist.Player;

public class PausedState implements PlayerState {
    @Override
    public void play(Player player) {
        System.out.println("State: PAUSED -> PLAYING");
        player.resumePlayback();
        player.changeState(new PlayState());
    }

    @Override
    public void pause(Player player) {
        System.out.println("State: PAUSED. Already paused.");
    }

    @Override
    public void stop(Player player) {
        System.out.println("State: PAUSED -> STOPPED");
        player.stopPlayback();
        player.changeState(new StoppedState());
    }
}
