package app.netlify.dev4rju9.spotifyclone.exoplayer.callbacks

import app.netlify.dev4rju9.spotifyclone.exoplayer.MusicService
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player

class MusicPlayerEventListener (
    private val musicService: MusicService
) : Player.Listener {
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        if (playbackState == Player.STATE_READY && !playWhenReady)
            musicService.stopForeground(false)
    }

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
    }
}