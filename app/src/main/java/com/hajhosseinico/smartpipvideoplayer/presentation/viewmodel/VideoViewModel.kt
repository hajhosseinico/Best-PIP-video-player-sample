package com.hajhosseinico.smartpipvideoplayer.presentation.viewmodel

import android.app.Application
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.util.Rational
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import com.hajhosseinico.smartpipvideoplayer.domain.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.hajhosseinico.smartpipvideoplayer.R
import kotlinx.coroutines.flow.asStateFlow

@HiltViewModel
class VideoViewModel @Inject constructor(
    application: Application,
    private val videoRepository: VideoRepository
) : AndroidViewModel(application) {

    companion object {
        const val ACTION_PLAY_PAUSE = "com.hajhosseinico.smartpipvideoplayer.ACTION_PLAY_PAUSE"
    }

    private val _isPlaying = MutableStateFlow(false)
    val isPlaying: StateFlow<Boolean> = _isPlaying

    private val _videoTitle = MutableStateFlow("")
    val videoTitle: StateFlow<String> = _videoTitle.asStateFlow()

    val exoPlayer: ExoPlayer by lazy {
        ExoPlayer.Builder(application).build()
    }

    init {
        loadVideo()
    }

    private fun loadVideo() {
        viewModelScope.launch {
            videoRepository.getVideo().collect { video ->
                _videoTitle.value = video.title
                val mediaItem = MediaItem.fromUri(video.url)
                exoPlayer.setMediaItem(mediaItem)
                exoPlayer.prepare()
                _isPlaying.value = false
            }
        }

        exoPlayer.addListener(object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                _isPlaying.value = isPlaying
            }
        })
    }

    fun togglePlayPause() {
        if (exoPlayer.isPlaying) {
            exoPlayer.pause()
        } else {
            exoPlayer.play()
        }
    }
    fun preparePlayerForPiP() {
        exoPlayer.playWhenReady = true  // Ensures playback resumes smoothly in PiP
    }

    fun updatePipActions(context: Context): PictureInPictureParams.Builder {
        val playPauseIntent = PendingIntent.getBroadcast(
            context,
            0,
            Intent(ACTION_PLAY_PAUSE),
            PendingIntent.FLAG_IMMUTABLE
        )

        val playPauseAction = android.app.RemoteAction(
            Icon.createWithResource(context, R.drawable.ic_launcher_foreground),
            if (_isPlaying.value) "Pause" else "Play",
            "Play or Pause the Video",
            playPauseIntent
        )

        return PictureInPictureParams.Builder()
            .setAspectRatio(Rational(16, 9))
            .setActions(listOf(playPauseAction))
            .setAutoEnterEnabled(true)
            .setSeamlessResizeEnabled(false)
    }

    private val _isInPipMode = MutableStateFlow(false)
    val isInPipMode: StateFlow<Boolean> = _isInPipMode

    fun updateIsInPipMode(isInPipMode: Boolean) {
        _isInPipMode.value = isInPipMode
    }

    override fun onCleared() {
        super.onCleared()
        exoPlayer.release()
    }

    fun resumePlayback() {
        if (!exoPlayer.isPlaying) {
            exoPlayer.play()
        }
    }

    fun stopPlayback() {
        if (exoPlayer.isPlaying) {
            exoPlayer.stop()
        }
    }

    fun handlePipModeChange(isInPipMode: Boolean, topAppBounds: Int) {
        updateIsInPipMode(isInPipMode)

        if (isInPipMode) {
            preparePlayerForPiP()
        } else {
            if (topAppBounds == 0) {
                resumePlayback()  // Expanded back to fullscreen
            } else {
                stopPlayback()  // Minimized or closed
            }
        }
    }

    fun parseTopAppBounds(configString: String): Int {
        val regex = "mAppBounds=Rect\\(\\d+, (\\d+)".toRegex()
        val matchResult = regex.find(configString)
        return matchResult?.groups?.get(1)?.value?.toInt() ?: 0
    }


}
