package com.letsbuildthatapp.remote

import com.google.gson.Gson
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.letsbuildthatapp.model.HomeFeed
import com.letsbuildthatapp.model.FeedVideo
import java.lang.reflect.Type

class HomeFeedDeserializer : JsonDeserializer<HomeFeed> {
  override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): HomeFeed {
    val homeFeedJson = json.asJsonObject.getAsJsonArray("videos")
    val videos = homeFeedJson.map { Gson().fromJson(it, FeedVideo::class.java) }
    return HomeFeed(videos)
  }
}