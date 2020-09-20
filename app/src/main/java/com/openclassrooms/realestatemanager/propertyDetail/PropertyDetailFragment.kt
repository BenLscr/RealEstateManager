package com.openclassrooms.realestatemanager.propertyDetail

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.googleApi.GoogleStreams
import com.openclassrooms.realestatemanager.googleApi.models.geocoding.GeocodingResponse
import com.openclassrooms.realestatemanager.propertyDetail.injections.Injection
import com.openclassrooms.realestatemanager.propertyDetail.media.MediaFragment
import com.openclassrooms.realestatemanager.propertyDetail.models.LocationsOfInterestModelProcessed
import com.openclassrooms.realestatemanager.propertyDetail.models.PropertyModelProcessed
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.property_detail_fragment.*

private const val ARG_PROPERTY_DETAIL_PROPERTY_ID = "ARG_PROPERTY_DETAIL_PROPERTY_ID"

class PropertyDetailFragment : Fragment() {

    companion object {
        fun newInstance(propertyId: Int) =
                PropertyDetailFragment().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PROPERTY_DETAIL_PROPERTY_ID, propertyId)
                    }
                }
    }

    private var propertyId: Int = 0
    private var mediaFragment = MediaFragment.newInstance()
    private val propertyDetailViewModel: PropertyDetailViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(requireContext())).get(PropertyDetailViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            propertyId = it.getInt(ARG_PROPERTY_DETAIL_PROPERTY_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.property_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        addMediaFragment()
        propertyDetailViewModel.getProperty(propertyId).observe(this, Observer {
            updateUiWithPropertyData(it)
            propertyDetailViewModel.getPropertyPhotos(propertyId, requireContext()).observe(this, Observer {propertyPhotos -> mediaFragment.receivePropertyPhotos(propertyPhotos) })
        })
        propertyDetailViewModel.getLocationsOfInterest(propertyId).observe(this, Observer { updateUiWithLocationsOfInterestData(it) })
    }

    private fun addMediaFragment() {
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.add(R.id.property_detail_media_container, mediaFragment)?.commit()
    }

    private fun updateUiWithPropertyData(model: PropertyModelProcessed) {
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
            if (saleDate.isNotEmpty()) {
                property_detail_sale_date_layout.visibility = View.VISIBLE
                property_detail_sale_date.text = saleDate
            } else {
                property_detail_sale_date_layout.visibility = View.GONE
            }
            path?.let { executeHttpRequestWithRetrofitGeocoding(it, city) }
        }
    }

    private fun updateUiWithLocationsOfInterestData(model: LocationsOfInterestModelProcessed) {
        with(model) {
            if (school) {
                property_detail_school.visibility = View.VISIBLE
            } else {
                property_detail_school.visibility = View.GONE
            }
            if (commerces) {
                property_detail_commerces.visibility = View.VISIBLE
            } else {
                property_detail_commerces.visibility = View.GONE
            }
            if (park) {
                property_detail_park.visibility = View.VISIBLE
            } else {
                property_detail_park.visibility = View.GONE
            }
            if (subways) {
                property_detail_subways.visibility = View.VISIBLE
            } else {
                property_detail_subways.visibility = View.GONE
            }
            if (train) {
                property_detail_train.visibility = View.VISIBLE
            } else {
                property_detail_train.visibility = View.GONE
            }
            if (!school && !commerces && !park && !subways && !train) {
                property_detail_empty_location_of_interest.visibility = View.VISIBLE
            }
        }
    }

    private fun executeHttpRequestWithRetrofitGeocoding(path: String, city: String) {
        val addressGeocoding = "$path, $city"
        val disposable: Disposable =
                GoogleStreams.streamFetchGeocoding(addressGeocoding, BuildConfig.GEOCODING_API_KEY)
                        .subscribeWith(object : DisposableObserver<GeocodingResponse>() {
            override fun onNext(geocodingResponse: GeocodingResponse) {
                Log.e("Geocoding", "On Next")
                when (geocodingResponse.status) {
                    "OK" -> determineDensityOfThePhone(
                            geocodingResponse.results[0].geometry.location.lat,
                            geocodingResponse.results[0].geometry.location.lng
                    )
                    "ZERO_RESULTS" -> {
                        property_detail_maps_static.visibility = View.GONE
                        property_detail_maps_static_error.visibility = View.VISIBLE
                        property_detail_maps_static_error.text = getString(R.string.maps_static_error_zero_results)
                    }
                    "UNKNOWN_ERROR" -> {
                        property_detail_maps_static.visibility = View.GONE
                        property_detail_maps_static_error.visibility = View.VISIBLE
                    }
                }
            }

            override fun onError(e: Throwable) {
                Log.e("Geocoding", "On Error" + Log.getStackTraceString(e))
                property_detail_maps_static_error.visibility = View.VISIBLE
            }

            override fun onComplete() {
                Log.e("Geocoding", "On Complete !!")
            }
        })
    }

    private fun determineDensityOfThePhone(lat: Double, lng: Double) {
        when (context?.resources?.displayMetrics?.densityDpi) {
            DisplayMetrics.DENSITY_MEDIUM -> updateUiWithMapsStatic(lat, lng, "320x320")
            DisplayMetrics.DENSITY_HIGH -> updateUiWithMapsStatic(lat, lng, "480x480")
            DisplayMetrics.DENSITY_XHIGH -> updateUiWithMapsStatic(lat, lng, "720x720")
            DisplayMetrics.DENSITY_XXHIGH -> updateUiWithMapsStatic(lat, lng, "1080x1080")
            DisplayMetrics.DENSITY_560 -> updateUiWithMapsStatic(lat, lng, "1440x1440")
            DisplayMetrics.DENSITY_XXXHIGH -> updateUiWithMapsStatic(lat, lng, "1440x1440")
            else -> updateUiWithMapsStatic(lat, lng, "720x720")
        }

    }

    private fun updateUiWithMapsStatic(lat: Double, lng: Double, size: String) {
        val color = "red"
        val zoom = "16"
        val mapsStaticApiKey = BuildConfig.MAPS_STATIC_API_KEY
        val url = "https://maps.googleapis.com/maps/api/staticmap?center=$lat,$lng&zoom=$zoom&size=$size&markers=color:$color%7C$lat,$lng&key=$mapsStaticApiKey"
        property_detail_maps_static.visibility = View.VISIBLE
        Glide.with(this@PropertyDetailFragment).load(url).into(property_detail_maps_static)
    }

}
