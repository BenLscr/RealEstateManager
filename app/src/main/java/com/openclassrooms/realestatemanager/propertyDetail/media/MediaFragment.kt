package com.openclassrooms.realestatemanager.propertyDetail.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.propertyDetail.media.injections.Injection

const val ARG_MEDIA_PROPERTY_ID = "ARG_MEDIA_PROPERTY_ID"

class MediaFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance(propertyId: Int) =
                MediaFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_MEDIA_PROPERTY_ID, propertyId)
                    }
                }
    }

    private var propertyId: Int = 0
    private val mediaViewModel: MediaViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(requireContext())).get(MediaViewModel::class.java) }
    private val mediaAdapter: MediaRecyclerViewAdapter = MediaRecyclerViewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            propertyId = it.getInt(ARG_MEDIA_PROPERTY_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_media_list, container, false)

        // Set the adapter
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mediaAdapter
        }

        getPropertyPath()
        getPropertyPhoto()
        return view
    }

    private fun getPropertyPath() = mediaViewModel.getPropertyPath(propertyId).observe(this, Observer { mediaAdapter.receivePropertyPath(it) })

    private fun getPropertyPhoto() = mediaViewModel.getPropertyPhotos(propertyId).observe(this, Observer { mediaAdapter.receivePropertyPhotos(it, requireContext()) })

}
