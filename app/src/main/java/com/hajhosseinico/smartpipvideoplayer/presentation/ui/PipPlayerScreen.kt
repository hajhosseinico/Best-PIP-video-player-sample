package com.hajhosseinico.smartpipvideoplayer.presentation.ui

import android.app.PictureInPictureParams
import android.graphics.Rect
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.UnstableApi
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.hajhosseinico.smartpipvideoplayer.presentation.viewmodel.VideoViewModel

@OptIn(UnstableApi::class)
@Composable
fun PipPlayerScreen(
    videoViewModel: VideoViewModel = hiltViewModel(),
    onEnterPipMode: (PictureInPictureParams.Builder) -> Unit,
    updateSourceRectHint: (Rect) -> Unit // New callback to update sourceRectHint
) {
    val context = LocalContext.current
    val isPlaying by videoViewModel.isPlaying.collectAsState()
    val isInPipMode by videoViewModel.isInPipMode.collectAsState()
    val videoTitle by videoViewModel.videoTitle.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Render only the PlayerView in PiP mode
        if (!isInPipMode) {
            Text(modifier = Modifier.padding(24.dp),
                text = videoTitle, style = MaterialTheme.typography.titleLarge
            )
        }

        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = videoViewModel.exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL

                    // Set up sourceRectHint listener for smoother PiP transition
                    addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                        val sourceRectHint = Rect()
                        getGlobalVisibleRect(sourceRectHint)
                        updateSourceRectHint(sourceRectHint) // Pass rect to MainActivity
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16 / 9f)
        )

        if (!isInPipMode) {
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                Button(onClick = { videoViewModel.togglePlayPause() }) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                        contentDescription = if (isPlaying) "Pause" else "Play"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (isPlaying) "Pause" else "Play")
                }

                Spacer(modifier = Modifier.height(8.dp))

                Button(onClick = {
                    val pipParams = videoViewModel.updatePipActions(context)
                    onEnterPipMode(pipParams)
                }) {
                    Text("Enter PiP Mode")
                }
            }
        }
    }
}
