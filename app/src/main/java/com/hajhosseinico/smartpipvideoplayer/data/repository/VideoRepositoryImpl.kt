package com.hajhosseinico.smartpipvideoplayer.data.repository

import com.hajhosseinico.smartpipvideoplayer.domain.model.Video
import com.hajhosseinico.smartpipvideoplayer.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class VideoRepositoryImpl @Inject constructor() : VideoRepository {

    override fun getVideo(): Flow<Video> = flow {
        emit(Video(id = "1", url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", title = "My PIP video player"))
    }
}
