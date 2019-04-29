package com.letsbuildthatapp.remote

import com.letsbuildthatapp.model.HomeFeed
import com.letsbuildthatapp.model.RelatedVideo
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface LetsBuildThatAppService {
  @GET("home_feed")
  fun getHomeFeedAsync(): Deferred<HomeFeed>

  @GET("course_detail")
  fun getCourseDetailAsync(@Query("id") courseId: Int): Deferred<List<RelatedVideo>>
}