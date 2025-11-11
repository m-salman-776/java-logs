//package LLD.Playlist;
//
//import LLD.Playlist.Entities.Song;
//import Models.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class UserRepository {
//    // Methods related to user data storage and retrieval
//    List<User> allUsers;
//    public UserRepository(){
//        allUsers = new ArrayList<>();
//    }
//    public void addUser(User user) {
//        allUsers.add(user);
//    }
//    public User getUserById(int userId) {
//        for (User user : allUsers) {
//            if (user.getUserId().equals( userId)) {
//                return user;
//            }
//        }
//        return null; // or throw an exception if user not found
//    }
//}
//class SongRepository {
//    // Methods related to song data storage and retrieval
//    List<Song> allSongs;
//
//    public SongRepository() {
//        allSongs = new ArrayList<>();
//    }
//
//    public void addSong(Song song) {
//        allSongs.add(song);
//    }
//
//    public Song getSongById(int songId) {
//        for (Song song : allSongs) {
//            if (song.getSongId() == songId) {
//                return song;
//            }
//        }
//        return null; // or throw an exception if song not found
//    }
//}
//class PlaylistRepository {
//    // Methods related to playlist data storage and retrieval
//    List<Playlist> allPlaylists;
//    public PlaylistRepository() {
//        allPlaylists = new ArrayList<>();
//    }
//    public void addPlaylist(Playlist playlist) {
//        allPlaylists.add(playlist);
//    }
//    public Playlist getPlaylistById(int playlistId) {
//        for (Playlist playlist : allPlaylists) {
//            if (playlist.getPlaylistId() == playlistId) {
//                return playlist;
//            }
//        }
//        return null; // or throw an exception if playlist not found
//    }
//}
//
//public class Repository {
//}