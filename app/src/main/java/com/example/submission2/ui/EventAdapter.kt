package com.example.submission2.ui

import android.media.metrics.Event
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submission2.data.response.ListEventsItem
import com.example.submission2.databinding.ItemEventBinding
import com.example.submission2.other.GlideApp

class EventAdapter(private val listEvent: List<ListEventsItem>) : ListAdapter<ListEventsItem, EventAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }


    class MyViewHolder(val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem){
            GlideApp.with(binding.root.context).load(event.imageLogo).into(binding.eventImage)
            binding.eventTitle.text = event.name
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listEvent[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:ListEventsItem)
    }


    companion object{
      val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>(){
          override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem):Boolean{
              return oldItem == newItem
          }

          override fun areContentsTheSame(
              oldItem: ListEventsItem,
              newItem: ListEventsItem
          ): Boolean {
              return oldItem == newItem
          }
      }
    }

}