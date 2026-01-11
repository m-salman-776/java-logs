package Project101.Playlist.PlayerState;

import Project101.Playlist.Player;

public class StoppedState implements PlayerState {

    @Override
    public void play(Player player) {
        System.out.println("State: STOPPED -> PLAYING");
        player.resumePlayback(); // Actually start playing!
        player.changeState(new PlayState());
    }

    @Override
    public void pause(Player player) {
        System.out.println("State: STOPPED. Cannot pause.");
    }

    @Override
    public void stop(Player player) {
        System.out.println("State: STOPPED. Already stopped.");
    }
}
