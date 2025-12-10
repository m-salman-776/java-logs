package Project101.Playlist.PlayerState;

import Project101.Playlist.Player;

public class PausedState implements PlayerState{
    @Override
    public void play(Player player) {
        player.changeState(new PlayState());
    }

    @Override
    public void pause(Player player) {

    }

    @Override
    public void stop(Player player) {

    }
}
