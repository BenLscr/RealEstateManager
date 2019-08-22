package com.openclassrooms.realestatemanager.propertyList

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.propertyList.PropertyListFragment.OnListFragmentInteractionListener
import com.openclassrooms.realestatemanager.propertyList.models.IllustrationModelProcessed
import com.openclassrooms.realestatemanager.propertyList.models.PropertyModelProcessed
import kotlinx.android.synthetic.main.fragment_property.view.*

class PropertyListRecyclerViewAdapter : RecyclerView.Adapter<PropertyListRecyclerViewAdapter.ViewHolder>() {

    private val properties = mutableListOf<PropertyModelProcessed>()
    private var mListener: OnListFragmentInteractionListener? = null
    private val illustrations = mutableListOf<IllustrationModelProcessed>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val property: PropertyModelProcessed = properties[position]

        with(holder) {
            type.text = property.type
            district.text = property.district
            price.text = property.price
            mView.setOnClickListener { mListener?.onListFragmentInteraction(property.propertyId) }
        }

        if(illustrations.isNotEmpty()) {
            for (illustration in illustrations) {
                if (illustration.propertyId == property.propertyId) {
                    holder.img.setImageBitmap(illustration.illustration)
                    break
                }
            }
        }
    }

    override fun getItemCount() = properties.size

    fun receivePropertiesDataAndListener(properties: List<PropertyModelProcessed>, listener: OnListFragmentInteractionListener?) {
        this.properties.clear()
        this.properties.addAll(properties)
        mListener = listener
        notifyDataSetChanged()
    }

    fun receiveIllustrations(illustrations: List<IllustrationModelProcessed>) {
        this.illustrations.clear()
        this.illustrations.addAll(illustrations)
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val img: ImageView = mView.property_img
        val type: TextView = mView.property_type
        val district: TextView = mView.property_district
        val price: TextView = mView.property_price

    }
}
