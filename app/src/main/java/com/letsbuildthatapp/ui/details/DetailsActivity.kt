package com.letsbuildthatapp.ui.details

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.google.android.material.snackbar.Snackbar
import com.letsbuildthatapp.R
import com.letsbuildthatapp.model.FeedVideo
import com.letsbuildthatapp.presentation.details.DetailsViewModel
import com.letsbuildthatapp.presentation.formatViews
import kotlinx.android.synthetic.main.activity_details.*
import java.lang.Exception

class DetailsActivity : AppCompatActivity() {

  private lateinit var detailsViewModel: DetailsViewModel

  private lateinit var selectedVideo: FeedVideo

  private lateinit var relatedVideosAdapter: RelatedVideosAdapter

  private lateinit var skeletonScreen: SkeletonScreen

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContentView(R.layout.activity_details)

    try {
      supportActionBar?.setDisplayHomeAsUpEnabled(true)

      selectedVideo = intent.getParcelableExtra(SELECTED_VIDEO)

      setupViewModelObservers()

      setDetails()

      setupDetailsRecyclerView()

      detailsViewModel.fetchRelatedVideos(selectedVideo.id)
      tap_to_retry.setOnClickListener { detailsViewModel.fetchRelatedVideos(selectedVideo.id) }
    } catch (exception: Exception) {
      exception.printStackTrace()
    }
  }

  override fun onSaveInstanceState(outState: Bundle?) {
    super.onSaveInstanceState(outState)
    outState?.putParcelable(SELECTED_VIDEO, selectedVideo)
  }

  override fun onOptionsItemSelected(item: MenuItem?): Boolean {
    return  when (item?.itemId) {
      android.R.id.home -> {
        finish()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  private fun setupViewModelObservers() {
    detailsViewModel = ViewModelProviders.of(this).get(DetailsViewModel::class.java)

    detailsViewModel.relatedVideos.observe(this, Observer {
      skeletonScreen.hide()
      relatedVideosAdapter.relatedVideos = it.distinct()
    })

    detailsViewModel.fetchRelatedVideosException.observe(this, Observer {
      if (detailsViewModel.relatedVideos.value == null) {
        details_recyclerview.visibility = View.GONE
        tap_to_retry.visibility = View.VISIBLE
      }
      Snackbar.make(related_videos_parent, R.string.unable_to_fetch_related_videos, Snackbar.LENGTH_SHORT).show()
    })
  }

  private fun setDetails() {
    val glide = Glide.with(this)
    glide.load(selectedVideo.imageUrl).into(video_thumbnail_imageview)

    video_name_textview.text = selectedVideo.name
    number_of_views_textview.text = formatViews(selectedVideo.numberOfViews)
    glide.load(selectedVideo.channel.profileImageUrl).circleCrop().into(channel_profile_imageview)
    channel_name_textview.text = selectedVideo.channel.name
  }

  private fun setupDetailsRecyclerView() {
    relatedVideosAdapter = RelatedVideosAdapter(detailsViewModel.relatedVideos.value ?: listOf())

    skeletonScreen = Skeleton.bind(details_recyclerview)
      .adapter(relatedVideosAdapter)
      .load(R.layout.row_skeleton_details).show()
  }

  companion object {
    const val SELECTED_VIDEO = "video_id"
  }
}