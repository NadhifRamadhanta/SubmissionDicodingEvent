package com.example.submission2.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submission2.R
import com.example.submission2.data.response.ListEventsItem
import com.example.submission2.databinding.FragmentFinishedEventBinding
import com.example.submission2.databinding.FragmentUpcomingEventBinding
import com.example.submission2.ui.UpcomingEvent.Companion.EXTRA_EVENT

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FinishedEvent.newInstance] factory method to
 * create an instance of this fragment.
 */
class FinishedEvent : Fragment() {
    private var _binding: FragmentFinishedEventBinding? = null
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
        binding.rvFinishedEvents.layoutManager = layoutManager


        mainViewModel.listFinishedEvent.observe(viewLifecycleOwner){
                listEvent ->
            setFinishedEventData(listEvent)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner){
            showLoading(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFinishedEventBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private fun setFinishedEventData(eventList: List<ListEventsItem>){
        val adapter = EventAdapter(eventList)
        adapter.submitList(eventList)
        binding.rvFinishedEvents.adapter = adapter

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
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FinishedEvent.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FinishedEvent().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}