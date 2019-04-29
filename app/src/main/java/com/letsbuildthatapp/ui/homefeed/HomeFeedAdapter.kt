package com.letsbuildthatapp.ui.homefeed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.letsbuildthatapp.R
import com.letsbuildthatapp.model.FeedVideo
import com.letsbuildthatapp.presentation.formatViews
import kotlinx.android.synthetic.main.row_home_feed.view.*
import kotlin.properties.Delegates

class HomeFeedAdapter(feedVideos: List<FeedVideo>, private val onVideoSelected: (FeedVideo) -> Unit) : RecyclerView.Adapter<HomeFeedViewHolder>() {

  var videos by Delegates.observable(feedVideos) { _, _, _ -> notifyDataSetChanged() }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeFeedViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.row_home_feed, parent, false)
    return HomeFeedViewHolder(view, onVideoSelected)
  }

  override fun onBindViewHolder(holder: HomeFeedViewHolder, position: Int) {
    holder.bind(videos[position])
  }

  override fun getItemCount(): Int = videos.size
}

class HomeFeedViewHolder(view: View, private val onVideoSelected: (FeedVideo) -> Unit) : RecyclerView.ViewHolder(view) {
  fun bind(feedVideo: FeedVideo) {
    itemView.video_name_textview.text = feedVideo.name
    itemView.number_of_views_textview.text = formatViews(feedVideo.numberOfViews)

    val glide = Glide.with(itemView)

    glide.load(feedVideo.imageUrl).into(itemView.video_thumbnail_imageview)
    glide.load(feedVideo.channel.profileImageUrl).circleCrop().into(itemView.profile_imageview)

    itemView.home_feed_row_parent.setOnClickListener { onVideoSelected(feedVideo) }
  }
}