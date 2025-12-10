package Project101.Playlist;

import Project101.Playlist.Playables.Song;
import Models.User;

public class Main {

    public static void main(String[] args) {
        Playlist playlist01 = new Playlist("blackrock",new User("me","you","we"),"free mood");
        Song song01 = new Song("song01");
        playlist01.addItem(song01);
        Player player = new Player(playlist01);
        player.play();
        player.next();
        player.stop();
    }
}
