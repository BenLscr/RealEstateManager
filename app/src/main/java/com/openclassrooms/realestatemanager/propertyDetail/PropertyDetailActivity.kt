package com.openclassrooms.realestatemanager.propertyDetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.INTENT_ADDRESS_HANDLED
import com.openclassrooms.realestatemanager.INTENT_PROPERTY_HANDLED
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.models.AddressPropertyListViewHolder
import com.openclassrooms.realestatemanager.models.PropertyPropertyListViewHolder
import kotlinx.android.synthetic.main.toolbar.*

class PropertyDetailActivity : AppCompatActivity() {

    private lateinit var property: PropertyPropertyListViewHolder
    private lateinit var address: AddressPropertyListViewHolder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)

        //this.retrievesIntent()
        this.configureToolbar()

        this.initAndAddFragment()
    }

    /*private fun retrievesIntent() {
        if (intent.hasExtra(INTENT_ADDRESS_HANDLED)) {
            address = intent.getParcelableExtra(INTENT_ADDRESS_HANDLED)
        }
        if (intent.hasExtra(INTENT_PROPERTY_HANDLED)) {
            property = intent.getParcelableExtra(INTENT_PROPERTY_HANDLED)
        }
    }*/

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initAndAddFragment() {
        val propertyDetailFragment: PropertyDetailFragment = PropertyDetailFragment.newInstance(address, property)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_property_detail_container, propertyDetailFragment).commit()
    }
}