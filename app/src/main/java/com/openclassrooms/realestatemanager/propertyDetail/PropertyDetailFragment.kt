package com.openclassrooms.realestatemanager.propertyDetail

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer

import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.propertyDetail.injections.Injection
import com.openclassrooms.realestatemanager.propertyDetail.models.ModelsProcessedPropertyDetail
import kotlinx.android.synthetic.main.property_detail_fragment.*

class PropertyDetailFragment : Fragment() {

    companion object {
        private const val ARG_PROPERTY_ID = "ARG_PROPERTY_ID"

        fun newInstance(propertyId: Int?): PropertyDetailFragment {
            val fragment = PropertyDetailFragment()
            propertyId?.let {
                val args = Bundle()
                args.putInt(ARG_PROPERTY_ID, propertyId)
                fragment.arguments = args
            }
            return fragment
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
        if (propertyId == 0) {
            getFirstProperty()
        } else {
            getProperty()
        }
    }

    private fun getProperty() = propertyDetailViewModel.getProperty(propertyId).observe(this, Observer { updateUi(it) })

    private fun getFirstProperty() = propertyDetailViewModel.getFirstProperty().observe(this, Observer { updateUi(it) })

    private fun updateUi(model: ModelsProcessedPropertyDetail) {
        with(model) {
            property_detail_description.text = description
            property_detail_surface.text = surface
            property_detail_rooms.text = rooms
            property_detail_bathrooms.text = bathrooms
            property_detail_bedrooms.text = bedrooms
            property_detail_path.text = path
            complement?.let {
                property_detail_complement.visibility = View.VISIBLE
                property_detail_complement.text = complement
            }
            property_detail_city.text = city
            property_detail_postal_code.text = postalCode
            property_detail_country.text = country
        }
    }

}
