package com.openclassrooms.realestatemanager.propertyList


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.AddressHandled
import com.openclassrooms.realestatemanager.models.PropertyHandled
import com.openclassrooms.realestatemanager.propertyList.PropertyListFragment.OnListFragmentInteractionListener
import kotlinx.android.synthetic.main.fragment_property.view.*


class PropertyListRecyclerViewAdapter(
        private val properties: MutableList<PropertyHandled>?,
        private val addresses: MutableList<AddressHandled>?,
        private val mListener: OnListFragmentInteractionListener?)
    : RecyclerView.Adapter<PropertyListRecyclerViewAdapter.ViewHolder>() {

    //private val mOnClickListener: View.OnClickListener

    private var mOnClickListener: View.OnClickListener? = null

    /*init {
        mOnClickListener = View.OnClickListener { v ->
            //val item = v.tag as DummyItem

            // Notify the active callbacks interface (the activity, if the fragment is attached to
            // one) that an item has been selected.
            mListener?.onListFragmentInteraction(address, property)
        }
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_property, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //val item = mValues[position]
        holder.img.setImageResource(R.drawable.ic_launcher_background)

        val property: PropertyHandled = properties!![position]
        val address: AddressHandled = addresses!![position]
        //holder.img.setImageBitmap(property.images[0])
        holder.type.text = property.type
        holder.district.text = address.district
        holder.price.text = property.price

        holder.mView.setOnClickListener { mListener?.onListFragmentInteraction(address, property) }

        /*with(holder.mView) {
            tag = item

            setOnClickListener(mOnClickListener)
        }*/
    }

    override fun getItemCount(): Int = properties!!.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {

        val img: ImageView = mView.property_img
        val type: TextView = mView.property_type
        val district: TextView = mView.property_district
        val price: TextView = mView.property_price

        override fun toString(): String {
            return super.toString() + " '" + "mContentView.text" + "'"
        }
    }
}
