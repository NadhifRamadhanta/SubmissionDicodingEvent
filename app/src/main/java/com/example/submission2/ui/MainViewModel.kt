package com.example.submission2.ui

import android.media.metrics.Event
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submission2.data.response.DetailEvent
import com.example.submission2.data.response.DetailEventResponse
import com.example.submission2.data.response.EventResponse
import com.example.submission2.data.response.ListEventsItem
import com.example.submission2.data.retrofit.ApiConfic
import com.example.submission2.ui.UpcomingEvent.Companion
import retrofit2.Call
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _listUpcomingEvent = MutableLiveData<List<ListEventsItem>>()
    val listUpcomingEvent : LiveData<List<ListEventsItem>> = _listUpcomingEvent

    private val _listFinishedEvent = MutableLiveData<List<ListEventsItem>>()
    val listFinishedEvent : LiveData<List<ListEventsItem>> = _listFinishedEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _detailEvent = MutableLiveData<DetailEvent>()
    val detailEvent: LiveData<DetailEvent> = _detailEvent

    companion object{
        private const val TAG = "MainViewModel"
        private const val EVENT_ID =  "uewq1zg2zlskfw1e867"
    }

    init {
        findUpcomingEvents()
        findFishinedEvents()
    }


    fun findUpcomingEvents(){
        _isLoading.value = true
        val client = ApiConfic.getApiService().getEvent("1")
        client.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _listUpcomingEvent.value = response.body()?.listEvents
                    }
                } else {
                    Log.e(TAG, "onFailure Response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }

    private fun findFishinedEvents(){
        _isLoading.value = true
        val client = ApiConfic.getApiService().getEvent("0")
        client.enqueue(object : retrofit2.Callback<EventResponse> {
            override fun onResponse(
                call: Call<EventResponse>,
                response: Response<EventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    val responseBody = response.body()
                    if (responseBody != null){
                        _listFinishedEvent.value = response.body()?.listEvents
                    }
                } else {
                    Log.e(TAG, "onFailure Response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message}")
            }
        })
    }

    fun getDetailEvent(id : String){
        _isLoading.value = true
        val client = ApiConfic.getApiService().getDetailEvent(id)
        client.enqueue(object :retrofit2.Callback<DetailEventResponse>{
            override fun onResponse(
                call: Call<DetailEventResponse>,
                response: Response<DetailEventResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    Log.d("$TAG Detail Event", response.toString())
                    val responseBody = response.body()
                    if (responseBody != null){
                        _detailEvent.value = response.body()?.event
                    }
                } else {
                    Log.e(TAG, "onFailure Response: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailEventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG,"onFailure: ${t.message}")
            }

        })
    }




}