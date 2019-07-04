package com.openclassrooms.realestatemanager.propertyDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*

class PropertyDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        this.configureToolbar()

        this.initAndAddFragment()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    private fun initAndAddFragment() {
        val propertyDetailFragment: PropertyDetailFragment = PropertyDetailFragment.newInstance()
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_property_detail_container, propertyDetailFragment).commit()
    }
}