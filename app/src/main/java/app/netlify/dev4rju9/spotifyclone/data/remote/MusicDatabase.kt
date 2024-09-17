package app.netlify.dev4rju9.spotifyclone.data.remote

import app.netlify.dev4rju9.spotifyclone.data.entities.Song
import app.netlify.dev4rju9.spotifyclone.other.Constants.SONG_COLLECTION
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MusicDatabase {

    private val firebase = FirebaseFirestore.getInstance()
    private val collection = firebase.collection(SONG_COLLECTION)

    suspend fun getAllSongs () : List<Song> = try {
        collection.get().await().toObjects(Song::class.java)
    } catch (e: Exception) { emptyList() }

}