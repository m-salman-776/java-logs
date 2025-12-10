package Project101.Playlist.PlayerState;

import Project101.Playlist.Player;

public class PlayState implements PlayerState{
    @Override
    public void play(Player player) {
        // do nothing no-ops
    }

    @Override
    public void pause(Player player) {
        System.out.println("Pausing Player");
        player.changeState(new PlayState());
    }

    @Override
    public void stop(Player player) {

    }
}
