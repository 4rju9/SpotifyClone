package app.netlify.dev4rju9.spotifyclone.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import app.netlify.dev4rju9.spotifyclone.R
import app.netlify.dev4rju9.spotifyclone.data.entities.Song
import app.netlify.dev4rju9.spotifyclone.other.Constants.SONG_COLLECTION
import com.bumptech.glide.RequestManager
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = Firebase.auth
        auth.signInWithEmailAndPassword(
            "ohinarendra3550@gmail.com",
            "Aruu@3550"
        )

    }

}