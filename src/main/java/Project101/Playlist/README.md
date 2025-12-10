## Playlist Schema
### playlist
- id
- name
- description
- owner_id
- is_public
- create_at

### media_item
- id
- title
- type : ENUM ('SONG' , 'VIDEO')
- source_url 
- properties : JSON key-val pair

### playlist_item
- id
- playlist_id reference playlist(id)
- media_id reference media_item (id)
- position
- added_at
