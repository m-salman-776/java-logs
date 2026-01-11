package Project101.Playlist.PlayerState;

import Project101.Playlist.Player;

/**
 * Deprecated. State classes now implement PlayerState directly for clarity.
 */
public class BasePlayerState implements PlayerState {
    @Override public void play(Player player) {}
    @Override public void pause(Player player) {}
    @Override public void stop(Player player) {}
}
