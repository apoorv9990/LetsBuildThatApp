package com.letsbuildthatapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

// Fields marked as Transient to avoid issues when deserializing API response with GSON
sealed class Video(
  @Transient open val name: String,
  @Transient open val link: String,
  @Transient open val imageUrl: String
)

@Parcelize
data class FeedVideo(
  val id: Int,
  val numberOfViews: Int,
  val channel: Channel,
  override val name: String,
  override val link: String,
  override val imageUrl: String
) : Video(name, link, imageUrl), Parcelable

data class RelatedVideo(
  val number: Int,
  val duration: String,
  override val name: String,
  override val imageUrl: String,
  override val link: String
) : Video(name, link, imageUrl)