## System Design LLD

### Playlist Service
Features to Support:
- A User can create, delete, and modify Playlists.
- A Playlist contains Songs in a specific order.
- A user can:
  - Add or remove songs from a playlist.
  - Reorder songs.
  - Mark playlists as public or private.
  - Follow other users' public playlists.
-  A Song has metadata like title, artist, album, duration, etc.
-  The Playlist should support different playback options:
   - Sequential order
   - Shuffle
   - Repeat (single or whole playlist)
- Solution
  - entities