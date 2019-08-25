package com.openclassrooms.realestatemanager.propertyDetail.media


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.propertyDetail.models.PhotoModelProcessed
import kotlinx.android.synthetic.main.fragment_media.view.*

class MediaRecyclerViewAdapter : RecyclerView.Adapter<MediaRecyclerViewAdapter.ViewHolder>() {

    private val propertyPhotos = mutableListOf<PhotoModelProcessed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val propertyPhoto: PhotoModelProcessed = propertyPhotos[position]

        with(holder) {
            photo.setImageBitmap(propertyPhoto.photo)
            wording.text = propertyPhoto.wording
        }
    }

    override fun getItemCount() = propertyPhotos.size

    fun receivePropertyPhotos(propertyPhotos: List<PhotoModelProcessed>) {
        this.propertyPhotos.clear()
        this.propertyPhotos.addAll(propertyPhotos)
        notifyDataSetChanged()
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {

        val photo: ImageView = mView.media_photo
        val wording: TextView = mView.media_wording

    }
}
