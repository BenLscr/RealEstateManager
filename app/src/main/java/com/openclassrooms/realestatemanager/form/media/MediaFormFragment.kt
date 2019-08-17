package com.openclassrooms.realestatemanager.form.media

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording

class MediaFormFragment : Fragment() {

    companion object {
        @JvmStatic
        fun newInstance() = MediaFormFragment().apply { }
    }

    private var listener: OnListFragmentInteractionListener? = null
    private val mediaFormAdapter: MediaFormRecyclerViewAdapter = MediaFormRecyclerViewAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_media_list, container, false)

        // Set the adapter
        (view as RecyclerView).apply {
            layoutManager = LinearLayoutManager(context)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mediaFormAdapter
        }
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    fun shareNewElementsInListToRecyclerView(listFormPhotoAndWording: List<FormPhotoAndWording>) {
        mediaFormAdapter.receiveNewElementsInList(listFormPhotoAndWording, listener)
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        fun onListFragmentInteraction(position: Int)
    }
}
