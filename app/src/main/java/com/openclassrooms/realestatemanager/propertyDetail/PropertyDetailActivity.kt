package com.openclassrooms.realestatemanager.propertyDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.INTENT_PROPERTY_ID
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*

class PropertyDetailActivity : AppCompatActivity() {

    private var propertyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        this.retrievesIntent()
        this.configureToolbar()

        this.initAndAddFragment()
    }

    private fun retrievesIntent() {
        if (intent.hasExtra(INTENT_PROPERTY_ID)) {
            propertyId = intent.getIntExtra(INTENT_PROPERTY_ID, 0)
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAndAddFragment() {
        val propertyDetailFragment: PropertyDetailFragment = PropertyDetailFragment.newInstance(propertyId)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_property_detail_container, propertyDetailFragment).commit()
    }
}