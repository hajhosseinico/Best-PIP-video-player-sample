package com.hajhosseinico.smartpipvideoplayer.domain.repository

import kotlinx.coroutines.flow.Flow
import com.hajhosseinico.smartpipvideoplayer.domain.model.Video

interface VideoRepository {
    fun getVideo(): Flow<Video>
}