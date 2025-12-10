package Project101.Playlist.PlayerState;

import Project101.Playlist.Player;

public interface PlayerState {
    void play(Player player);
    void pause(Player player);
    void stop(Player player);
}
