package com.openclassrooms.realestatemanager.map

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.openclassrooms.realestatemanager.BuildConfig
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.googleApi.GoogleStreams
import com.openclassrooms.realestatemanager.googleApi.models.geocoding.GeocodingResponse
import com.openclassrooms.realestatemanager.map.injections.Injection
import com.openclassrooms.realestatemanager.map.models.PropertyModelProcessed
import com.sembozdemir.permissionskt.askPermissions
import com.sembozdemir.permissionskt.handlePermissionsResult
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import kotlinx.android.synthetic.main.activity_map.*
import kotlinx.android.synthetic.main.toolbar.*

const val PICK_PROPERTY_DATA = "PICK_PROPERTY_DATA"

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap
    private val mapViewModel: MapViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(applicationContext)).get(MapViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        configureToolbar()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handlePermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        askPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION) {
            onGranted {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MapActivity)
                fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            val latitude =  location?.latitude
                            val longitude = location?.longitude
                            mMap.isMyLocationEnabled = true
                            mMap.uiSettings.isMyLocationButtonEnabled = true
                            if (latitude != null && longitude != null) {
                                val myLocation = LatLng(latitude, longitude)
                                val zoom = 15.toFloat()
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, zoom))
                                mMap.setOnMarkerClickListener(this@MapActivity)
                            }
                        }

                mapViewModel.allProperties.observe(this@MapActivity, Observer { it.map { propertyModelProcessed ->  setMarkerOnMap(propertyModelProcessed) } })
            }
            onShowRationale { request ->
                Snackbar.make(coordinatorLayout_map_activity, getString(R.string.map_need_location_permission), Snackbar.LENGTH_INDEFINITE)
                        .setAction(getString(R.string.map_location_permission_retry)) { request.retry() }
                        .setActionTextColor(ContextCompat.getColor(applicationContext, R.color.colorAccent))
                        .show()

            }
        }

    }

    private fun setMarkerOnMap(propertyModelProcessed: PropertyModelProcessed) {
        val disposable: Disposable =
                GoogleStreams.streamFetchGeocoding(propertyModelProcessed.addressGeocoding, BuildConfig.GEOCODING_API_KEY)
                .subscribeWith(object : DisposableObserver<GeocodingResponse>() {
            override fun onNext(geocodingResponse: GeocodingResponse) {
                Log.e("Geocoding", "On Next")
                when {
                    geocodingResponse.status == "OK" -> {
                        val position = LatLng(
                                geocodingResponse.results[0].geometry.location.lat,
                                geocodingResponse.results[0].geometry.location.lng
                        )
                        mMap.addMarker(MarkerOptions().position(position)).tag = propertyModelProcessed.propertyId
                    }
                    geocodingResponse.status == "ZERO_RESULTS" -> { }
                    geocodingResponse.status == "UNKNOWN_ERROR" -> { }
                }
            }
            override fun onError(e: Throwable) { Log.e("Geocoding", "On Error" + Log.getStackTraceString(e)) }
            override fun onComplete() { Log.e("Geocoding", "On Complete !!") }
        })
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val propertyId = marker.tag as Int
        val returnIntent = Intent()
        returnIntent.putExtra(PICK_PROPERTY_DATA, propertyId)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
        return false
    }

}