package com.example.submission2.data.response

import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class DetailEventResponse(
	val error: Boolean,
	val message: String,
	val event: DetailEvent
) : Parcelable

@Parcelize
data class DetailEvent(
	val summary: String,
	val mediaCover: String,
	val registrants: Int,
	val imageLogo: String,
	val link: String,
	val description: String,
	val ownerName: String,
	val cityName: String,
	val quota: Int,
	val name: String,
	val id: Int,
	val beginTime: String,
	val endTime: String,
	val category: String
) : Parcelable
