package com.letsbuildthatapp.presentation.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsbuildthatapp.model.RelatedVideo
import com.letsbuildthatapp.remote.LetsBuildThatAppServiceFactory
import kotlinx.coroutines.launch

class DetailsViewModel : ViewModel() {
  val fetchRelatedVideosException: MutableLiveData<Boolean> = MutableLiveData()

  val relatedVideos = MutableLiveData<List<RelatedVideo>>()

  private var lastFetchedVideoId = -1

  fun fetchRelatedVideos(videoId: Int) {
    if (lastFetchedVideoId == videoId) return

    viewModelScope.launch {
      try {
        val letsBuildThatAppService = LetsBuildThatAppServiceFactory.letsBuildThatAppService
        relatedVideos.value = letsBuildThatAppService.getCourseDetailAsync(videoId).await()
        lastFetchedVideoId = videoId
      } catch (exception: Exception) {
        fetchRelatedVideosException.value = true
      }
    }
  }
}