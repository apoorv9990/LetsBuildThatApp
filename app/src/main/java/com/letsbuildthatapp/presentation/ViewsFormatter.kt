package com.letsbuildthatapp.presentation

import java.util.*

private val suffixes by lazy {
  TreeMap<Long, String>().apply {
    put(1_000, "k")
    put(1_000_000, "M")
    put(1_000_000_000, "G")
    put(1_000_000_000_000, "T")
    put(1_000_000_000_000_000, "P")
    put(1_000_000_000_000_000_000, "E")
  }
}

fun formatViews(views: Int): String = formatViews(views.toLong())

fun formatViews(views: Long): String {
  if (views == Long.MIN_VALUE) return formatViews(Long.MIN_VALUE + 1)
  if (views < 0) return "-" + formatViews(-views)
  if (views < 1000) return views.toString()

  val e = suffixes.floorEntry(views)
  val divideBy = e.key
  val suffix = e.value

  val truncated = views / (divideBy / 10) //the number part of the output times 10
  val hasDecimal = truncated < 100 && truncated / 10.0 != (truncated / 10).toDouble()
  return if (hasDecimal) "${truncated / 10.0}$suffix" else "${truncated / 10}$suffix"
}