package com.openclassrooms.realestatemanager.propertyDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.AddressPropertyListViewHolder
import com.openclassrooms.realestatemanager.models.PropertyPropertyListViewHolder
import kotlinx.android.synthetic.main.property_detail_fragment.*

class PropertyDetailFragment : Fragment() {

    companion object {
        private const val ARG_ADDRESS_HANDLED = "ARG_ADDRESS_HANDLED"
        private const val ARG_PROPERTY_HANDLED = "ARG_PROPERTY_HANDLED"

        fun newInstance(address: AddressPropertyListViewHolder?, property: PropertyPropertyListViewHolder?): PropertyDetailFragment {
            val fragment = PropertyDetailFragment()
            val args = Bundle()
            /*args.putParcelable(ARG_ADDRESS_HANDLED, address)
            args.putParcelable(ARG_PROPERTY_HANDLED, property)
            fragment.arguments = args*/
            return fragment
        }
    }

    private lateinit var property: PropertyPropertyListViewHolder
    private lateinit var address: AddressPropertyListViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*arguments?.let {
            this.address = it.getParcelable(ARG_ADDRESS_HANDLED)
            this.property = it.getParcelable(ARG_PROPERTY_HANDLED)
        }*/
    }

    private lateinit var viewModel: PropertyDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PropertyDetailViewModel::class.java)
        // TODO: Use the ViewModel
        this.updateUi()
    }

    private fun updateUi() {
        /*with(property) {
            property_detail_description.text = description
        }
        //property_detail_description.text = property.description
        property_detail_surface.text = property.surface
        property_detail_rooms.text = property.rooms.toString()
        property_detail_bathrooms.text = property.bathrooms.toString()
        property_detail_bedrooms.text = property.bedrooms.toString()*/
    }

}
