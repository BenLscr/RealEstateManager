package com.openclassrooms.realestatemanager.form.media

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording
import com.openclassrooms.realestatemanager.form.media.MediaFormFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_media.view.*

class MediaFormRecyclerViewAdapter : RecyclerView.Adapter<MediaFormRecyclerViewAdapter.ViewHolder>() {

    private val listFormPhotoAndWording = mutableListOf<FormPhotoAndWording>()
    private var mListener: OnListFragmentInteractionListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val formPhotoAndWording: FormPhotoAndWording = listFormPhotoAndWording[position]

        with(holder) {
            photo.setImageBitmap(formPhotoAndWording.photo)
            wording.text = formPhotoAndWording.wording
            mView.setOnClickListener { mListener?.onListFragmentInteraction(position) }
        }
    }

    override fun getItemCount() = listFormPhotoAndWording.size

    fun receiveNewElementsInList(listFormPhotoAndWording: List<FormPhotoAndWording>, listener: OnListFragmentInteractionListener?) {
        this.listFormPhotoAndWording.clear()
        this.listFormPhotoAndWording.addAll(listFormPhotoAndWording)
        mListener = listener
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val photo: ImageView = mView.media_photo
        val wording: TextView = mView.media_wording

    }
}
