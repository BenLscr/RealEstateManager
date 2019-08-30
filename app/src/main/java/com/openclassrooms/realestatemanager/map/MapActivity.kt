package com.openclassrooms.realestatemanager.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

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

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        val newYork = LatLng(40.712775, -74.005973)
        mMap.addMarker(MarkerOptions().position(newYork).title("Marker in New York"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(newYork))
    }


    override fun onMarkerClick(p0: Marker?): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}