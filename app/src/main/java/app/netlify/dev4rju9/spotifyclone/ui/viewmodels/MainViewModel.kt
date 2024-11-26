package app.netlify.dev4rju9.spotifyclone.ui.viewmodels

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.SubscriptionCallback
import android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.netlify.dev4rju9.spotifyclone.data.entities.Song
import app.netlify.dev4rju9.spotifyclone.exoplayer.MusicServiceConnection
import app.netlify.dev4rju9.spotifyclone.exoplayer.isPlayEnabled
import app.netlify.dev4rju9.spotifyclone.exoplayer.isPlaying
import app.netlify.dev4rju9.spotifyclone.other.Constants.MEDIA_ROOT_ID
import app.netlify.dev4rju9.spotifyclone.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {
    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.currentPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe(MEDIA_ROOT_ID, object : SubscriptionCallback() {
            override fun onChildrenLoaded(
                parentId: String,
                children: MutableList<MediaBrowserCompat.MediaItem>
            ) {
                super.onChildrenLoaded(parentId, children)
                val items = children.mapNotNull {
                    val mediaId = it.mediaId ?: return@mapNotNull null
                    val title = it.description.title?.toString() ?: "Unknown Title"
                    val subtitle = it.description.subtitle?.toString() ?: "Unknown Subtitle"
                    val mediaUri = it.description.mediaUri?.toString() ?: "Unknown Media URI"
                    val iconUri = it.description.iconUri?.toString() ?: "Unknown Icon URI"

                    Song(mediaId, title, subtitle, mediaUri, iconUri)
                }
                _mediaItems.postValue(Resource.success(items))
            }
        })
    }

    fun skipToNextSong  () {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong () {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo (pos: Long) {
        musicServiceConnection.transportControls.seekTo(pos)
    }

    fun playOrToggleSong (mediaItem: Song, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPlaying ?: false
        if (isPrepared && mediaItem.mediaId ==
            curPlayingSong.value?.getString(METADATA_KEY_MEDIA_ID)) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying -> if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled -> musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.mediaId, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(MEDIA_ROOT_ID, object : SubscriptionCallback() {})
    }

}