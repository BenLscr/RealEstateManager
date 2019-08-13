package com.openclassrooms.realestatemanager.propertyDetail.media


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.propertyDetail.media.models.PhotoModelProcessed
import kotlinx.android.synthetic.main.fragment_media.view.*

class MediaRecyclerViewAdapter : RecyclerView.Adapter<MediaRecyclerViewAdapter.ViewHolder>() {

    private var propertyPath: String? = null
    private val propertyPhotos = mutableListOf<PhotoModelProcessed>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_media, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val propertyPhoto: PhotoModelProcessed = propertyPhotos[position]

        with(holder) {
            photo.setImageBitmap(Utils.getInternalBitmap(propertyPath, propertyPhoto.name, context))
            wording.text = propertyPhoto.wording
        }
    }

    override fun getItemCount(): Int = propertyPhotos.size

    fun receivePropertyPath(propertyPath: String) {
        this.propertyPath = propertyPath
        notifyDataSetChanged()
    }

    fun receivePropertyPhotos(propertyPhotos: List<PhotoModelProcessed>, context: Context) {
        this.propertyPhotos.clear()
        this.propertyPhotos.addAll(propertyPhotos)
        this.context = context
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val photo: ImageView = mView.media_photo
        val wording: TextView = mView.media_wording

    }
}
