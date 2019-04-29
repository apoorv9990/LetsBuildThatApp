package com.letsbuildthatapp.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.letsbuildthatapp.R
import com.letsbuildthatapp.model.RelatedVideo
import kotlinx.android.synthetic.main.row_details.view.*
import kotlin.properties.Delegates

class RelatedVideosAdapter(relatedVideos: List<RelatedVideo>) : RecyclerView.Adapter<RelatedVideoViewHolder>() {

  var relatedVideos by Delegates.observable(relatedVideos) { _, _, _ ->
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedVideoViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.row_details, parent, false)
    return RelatedVideoViewHolder(view)
  }

  override fun onBindViewHolder(holder: RelatedVideoViewHolder, position: Int) {
    holder.bind(relatedVideos[position])
  }

  override fun getItemCount(): Int  = relatedVideos.size
}

class RelatedVideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
  fun bind(relatedVideo: RelatedVideo) {
    Glide.with(itemView).load(relatedVideo.imageUrl).into(itemView.related_video_thumnail_imageview)
    itemView.related_video_name_textview.text = relatedVideo.name
  }
}