package com.example.submission2.data.retrofit

import com.example.submission2.data.response.DetailEventResponse
import com.example.submission2.data.response.EventResponse
import com.example.submission2.data.response.ListEventsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("events")
    fun getEvent(
        @Query("active") id : String
    ): Call<EventResponse>

    @GET("events/{id}")
    fun getDetailEvent(
        @Path("id") id: String
    ): Call<DetailEventResponse>
}