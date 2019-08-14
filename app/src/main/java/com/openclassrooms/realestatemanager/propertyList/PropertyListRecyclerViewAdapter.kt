package com.openclassrooms.realestatemanager.propertyList


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.propertyList.PropertyListFragment.OnListFragmentInteractionListener
import com.openclassrooms.realestatemanager.propertyList.models.IllustrationModelProcessed
import com.openclassrooms.realestatemanager.propertyList.models.PropertyModelProcessed
import kotlinx.android.synthetic.main.fragment_property.view.*


class PropertyListRecyclerViewAdapter : RecyclerView.Adapter<PropertyListRecyclerViewAdapter.ViewHolder>() {

    private val properties = mutableListOf<PropertyModelProcessed>()
    private var mListener: OnListFragmentInteractionListener? = null
    private val propertiesPhotos = mutableListOf<IllustrationModelProcessed>()
    private var context: Context? = null

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

        if(propertiesPhotos.isNotEmpty()) {
            for (propertyPhoto in propertiesPhotos) {
                if (propertyPhoto.propertyId == property.propertyId) {
                    holder.img.setImageBitmap(Utils.getInternalBitmap(property.path, propertyPhoto.photoName, context))
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

    fun receivePropertiesPhotos(propertiesPhotos: List<IllustrationModelProcessed>, context: Context) {
        this.propertiesPhotos.clear()
        this.propertiesPhotos.addAll(propertiesPhotos)
        this.context = context
        notifyDataSetChanged()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val img: ImageView = mView.property_img
        val type: TextView = mView.property_type
        val district: TextView = mView.property_district
        val price: TextView = mView.property_price

    }
}
