package com.letsbuildthatapp.presentation.homefeed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letsbuildthatapp.model.FeedVideo
import com.letsbuildthatapp.remote.LetsBuildThatAppServiceFactory
import kotlinx.coroutines.launch

class HomeFeedViewModel : ViewModel() {

  val videos: MutableLiveData<List<FeedVideo>> = MutableLiveData()

  val fetchHomeFeedException: MutableLiveData<Boolean> = MutableLiveData()

  init {
    fetchHomeFeed()
  }

  fun fetchHomeFeed() {
    viewModelScope.launch {
      try {
        val letsBuildThatAppService = LetsBuildThatAppServiceFactory.letsBuildThatAppService
        videos.value = letsBuildThatAppService.getHomeFeedAsync().await().feedVideos
      } catch (exception: Exception) {
        fetchHomeFeedException.value = true
      }
    }
  }
}