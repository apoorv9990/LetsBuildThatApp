package com.letsbuildthatapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Channel(val name: String, val profileImageUrl: String, val numberOfSubscribers: Int) : Parcelable