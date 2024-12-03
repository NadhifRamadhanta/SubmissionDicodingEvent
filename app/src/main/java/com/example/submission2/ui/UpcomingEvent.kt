package com.example.submission2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.data.response.EventResponse
import com.example.submission2.data.response.ListEventsItem
import com.example.submission2.data.retrofit.ApiConfic
import com.example.submission2.databinding.FragmentUpcomingEventBinding
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpcomingEvent.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpcomingEvent : Fragment() {
    private var _binding: FragmentUpcomingEventBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel by viewModels<MainViewModel>()


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUpcomingEvents.layoutManager = layoutManager


        mainViewModel.listUpcomingEvent.observe(viewLifecycleOwner){
            listEvent ->
            setUpcomingEventData(listEvent)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingEventBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    private fun setUpcomingEventData(eventList: List<ListEventsItem>){
        val adapter = EventAdapter(eventList)
        adapter.submitList(eventList)
        binding.rvUpcomingEvents.adapter = adapter

        adapter.setOnItemClickCallback(object : EventAdapter.OnItemClickCallback{
            override fun onItemClicked(data: ListEventsItem) {
                val intent = Intent(requireActivity(), EventDetail::class.java)
                startActivity(intent.putExtra(EXTRA_EVENT,"${data.id}"))

            }
        })
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_EVENT = "extra_event"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpcomingEvent.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpcomingEvent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}