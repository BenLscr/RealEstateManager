package com.openclassrooms.realestatemanager.propertyDetail.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.propertyDetail.models.PhotoModelProcessed

class MediaFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() =
                MediaFragment().apply { }
    }
    private val mediaAdapter: MediaRecyclerViewAdapter = MediaRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_media_list, container, false)

        // Set the adapter
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mediaAdapter
        }

        return view
    }

    fun receivePropertyPhotos(propertyPhotos: List<PhotoModelProcessed>) = mediaAdapter.receivePropertyPhotos(propertyPhotos)

}
