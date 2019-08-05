package com.openclassrooms.realestatemanager.propertyDetail

import android.opengl.Visibility
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.propertyDetail.injections.Injection
import com.openclassrooms.realestatemanager.propertyDetail.models.LocationOfInterestModelsProcessed
import com.openclassrooms.realestatemanager.propertyDetail.models.PropertyModelsProcessed
import kotlinx.android.synthetic.main.property_detail_fragment.*

private const val ARG_PROPERTY_ID = "ARG_PROPERTY_ID"

class PropertyDetailFragment : Fragment() {

    companion object {
        fun newInstance(propertyId: Int) =
                PropertyDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PROPERTY_ID, propertyId)
                    }
                }
    }

    private var propertyId: Int = 0
    private val propertyDetailViewModel: PropertyDetailViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(requireContext())).get(PropertyDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            propertyId = it.getInt(ARG_PROPERTY_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        propertyDetailViewModel.getProperty(propertyId).observe(this, Observer { updateUiWithPropertyData(it) })
        propertyDetailViewModel.getLocationsOfInterest(propertyId).observe(this, Observer { updateUiWithLocationsOfInterestData(it) })
    }

    private fun updateUiWithPropertyData(model: PropertyModelsProcessed) {
        with(model) {
            property_detail_description.text = description
            property_detail_surface.text = surface
            property_detail_rooms.text = rooms
            property_detail_bathrooms.text = bathrooms
            property_detail_bedrooms.text = bedrooms
            if (available) {
                property_detail_available.text = resources.getString(R.string.available)
                property_detail_available.setTextColor(resources.getColor(android.R.color.holo_green_dark))
            } else {
                property_detail_available.text = resources.getString(R.string.sold)
                property_detail_available.setTextColor(resources.getColor(android.R.color.holo_red_dark))
            }
            property_detail_path.text = path
            complement?.let {
                property_detail_complement.visibility = View.VISIBLE
                property_detail_complement.text = complement
            }
            property_detail_city.text = city
            property_detail_postal_code.text = postalCode
            property_detail_country.text = country
            property_detail_agent.text = agentFullName
            property_detail_entry_date.text = entryDate
            if (saleDate != null) {
                property_detail_sale_date.text = saleDate
            } else {
                property_detail_sale_date_layout.visibility = View.GONE
            }
        }
    }

    private fun updateUiWithLocationsOfInterestData(model: LocationOfInterestModelsProcessed) {
        with(model) {
            if (!school) {
                property_detail_school.visibility = View.GONE
            }
            if (!commerces) {
                property_detail_commerces.visibility = View.GONE
            }
            if (!park) {
                property_detail_park.visibility = View.GONE
            }
            if (!subways) {
                property_detail_subways.visibility = View.GONE
            }
            if (!train) {
                property_detail_train.visibility = View.GONE
            }
            if (!school && !commerces && !park && !subways && !train) {
                property_detail_empty_location_of_interest.visibility = View.VISIBLE
            }
        }
    }

}
