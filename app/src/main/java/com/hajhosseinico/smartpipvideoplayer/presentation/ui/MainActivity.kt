package com.hajhosseinico.smartpipvideoplayer.presentation.ui

import android.annotation.SuppressLint
import android.app.PictureInPictureParams
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Rect
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hajhosseinico.smartpipvideoplayer.presentation.viewmodel.VideoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val videoViewModel: VideoViewModel by viewModels()

    private val pipReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == VideoViewModel.ACTION_PLAY_PAUSE) {
                videoViewModel.togglePlayPause()
            }
        }
    }

    private var sourceRectHint: Rect? = null // Store the sourceRectHint

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerReceiver(
            pipReceiver, IntentFilter(VideoViewModel.ACTION_PLAY_PAUSE),
            RECEIVER_NOT_EXPORTED
        )

        setContent {
            PipPlayerScreen(
                videoViewModel = videoViewModel,
                onEnterPipMode = { pipParamsBuilder ->
                    // Apply the latest sourceRectHint for smoother transition
                    sourceRectHint?.let { pipParamsBuilder.setSourceRectHint(it) }
                    enterPictureInPictureMode(pipParamsBuilder.build())
                },
                updateSourceRectHint = { rect ->
                    sourceRectHint = rect
                    setPictureInPictureParams(
                        PictureInPictureParams.Builder()
                            .setSourceRectHint(rect)
                            .setAutoEnterEnabled(true) // Enables smoother transition on Android 12+
                            .build()
                    )
                }
            )
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        videoViewModel.updateIsInPipMode(isInPictureInPictureMode)

        val newConfigString = newConfig.toString()
        val topAppBounds = videoViewModel.parseTopAppBounds(newConfigString)

        videoViewModel.handlePipModeChange(isInPictureInPictureMode, topAppBounds)
    }




    @SuppressLint("MissingSuperCall")
    override fun onUserLeaveHint() {
        // Call enterPictureInPictureMode with updated params if needed
        enterPictureInPictureMode(
            PictureInPictureParams.Builder()
                .setSourceRectHint(sourceRectHint) // Use latest sourceRectHint
                .setAutoEnterEnabled(true)
                .build()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(pipReceiver)
    }
}
