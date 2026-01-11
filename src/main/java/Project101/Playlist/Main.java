package Project101.Playlist;

import Project101.Playlist.Playables.Song;
import Models.User;

public class Main {

    public static void main(String[] args) {
        
        Song song01 = new Song(1, "Bohemian Rhapsody", 355);
        Song song02 = new Song(2, "Stairway to Heaven", 482);
        Song song03 = new Song(3, "Hotel California", 390);

        Playlist alicePlaylist = new Playlist("Playlist01","alice","my solo");

        alicePlaylist.addItem(song01);
        alicePlaylist.addItem(song02);
        alicePlaylist.addItem(song03);

        // 2. Initialize Player
        Player player = new Player(); // Use the constructor we defined
        player.setPlaylist(alicePlaylist);

        System.out.println("--- Starting Playback ---");
        // 3. User presses Play
        player.play(); // State: Stopped -> Playing. Plays Song 1.

        System.out.println("\n--- Skipping Track ---");
        // 4. User presses Next
        player.next(); // Plays Song 2.

        System.out.println("\n--- Pausing ---");
        // 5. User presses Pause
        player.pause(); // State: Playing -> Paused.

        System.out.println("\n--- Resuming ---");
        // 6. User presses Play again
        player.play(); // State: Paused -> Playing. Resumes Song 2.

        System.out.println("\n--- Stopping ---");
        // 7. User presses Stop
        player.stop(); // State: Playing -> Stopped. Resets to start.
    }
}
