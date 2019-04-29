package com.letsbuildthatapp.ui.homefeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ethanhua.skeleton.Skeleton
import com.ethanhua.skeleton.SkeletonScreen
import com.google.android.material.snackbar.Snackbar
import com.letsbuildthatapp.presentation.homefeed.HomeFeedViewModel
import com.letsbuildthatapp.R
import com.letsbuildthatapp.ui.details.DetailsActivity
import kotlinx.android.synthetic.main.activity_home_feed.*

class HomeFeedActivity : AppCompatActivity() {

  private lateinit var homeFeedViewModel: HomeFeedViewModel

  private lateinit var homeFeedAdapter: HomeFeedAdapter

  private lateinit var skeletonScreen: SkeletonScreen

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_home_feed)

    homeFeedViewModel = ViewModelProviders.of(this).get(HomeFeedViewModel::class.java)

    home_feed_swipe_refresh.setOnRefreshListener { refreshHomeFeed() }
    tap_to_retry.setOnClickListener { refreshHomeFeed() }

    setupHomeFeedRecyclerView()

    setupViewModelObservers()
  }

  private fun refreshHomeFeed() {
    home_feed_swipe_refresh.isRefreshing = true
    homeFeedViewModel.fetchHomeFeed()
  }

  private fun setupViewModelObservers() {
    homeFeedViewModel.videos.observe(this, Observer {
      skeletonScreen.hide()
      home_feed_recyclerview.visibility = View.VISIBLE
      tap_to_retry.visibility = View.GONE
      home_feed_swipe_refresh.isRefreshing = false
      homeFeedAdapter.videos = it
    })

    homeFeedViewModel.fetchHomeFeedException.observe(this, Observer {
      val snackbarMessage: Int
      if (homeFeedViewModel.videos.value == null) {
        home_feed_recyclerview.visibility = View.GONE
        tap_to_retry.visibility = View.VISIBLE
        snackbarMessage = R.string.unable_to_fetch_home_feed
      } else {
        snackbarMessage = R.string.unable_to_refresh_home_feed
      }
      home_feed_swipe_refresh.isRefreshing = false
      Snackbar.make(home_feed_swipe_refresh, snackbarMessage, Snackbar.LENGTH_SHORT).show()
    })
  }

  private fun setupHomeFeedRecyclerView() {
    homeFeedAdapter = HomeFeedAdapter(homeFeedViewModel.videos.value ?: listOf()) {
      val intent = Intent(this, DetailsActivity::class.java)
      intent.putExtra(DetailsActivity.SELECTED_VIDEO, it)
      startActivity(intent)
    }

    skeletonScreen = Skeleton.bind(home_feed_recyclerview)
      .adapter(homeFeedAdapter)
      .load(R.layout.row_skeleton_home_feed)
      .show()
  }
}
