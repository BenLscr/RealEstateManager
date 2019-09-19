package com.openclassrooms.realestatemanager.result

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.emptyPropertyDetail.EmptyPropertyDetailFragment
import com.openclassrooms.realestatemanager.form.searchForm.INTENT_SEARCH_TO_RESULT
import com.openclassrooms.realestatemanager.propertyDetail.PropertyDetailActivity
import com.openclassrooms.realestatemanager.propertyDetail.PropertyDetailFragment
import com.openclassrooms.realestatemanager.propertyList.PropertyListFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

const val INTENT_RESULT_TO_DETAIL = "INTENT_RESULT_TO_DETAIL"

class ResultActivity : AppCompatActivity(), PropertyListFragment.OnListFragmentInteractionListener {

    private val propertiesId = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        configureToolbar()
        retrievesIntent()
        if (savedInstanceState == null) {
            initAndAddFragment()
        } else {
            findsFragment()
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun retrievesIntent() {
        if (intent.hasExtra(INTENT_SEARCH_TO_RESULT)) {
            this.propertiesId.addAll(intent.getIntArrayExtra(INTENT_SEARCH_TO_RESULT).toMutableList())
        }
    }

    private var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    private var fragmentPropertyDetail: Fragment? = null
    private var fragmentEmptyPropertyDetail: EmptyPropertyDetailFragment? = null
    private var containerPropertyDetail: Fragment? = supportFragmentManager.findFragmentById(R.id.activity_property_detail_container)
    private var propertyId: Int = 0

    private fun initAndAddFragment() {
        fragmentTransaction.add(R.id.activity_property_list_container, PropertyListFragment.newInstance(propertiesId.toIntArray()))
        if (containerPropertyDetail == null && activity_property_detail_container != null) {
            fragmentEmptyPropertyDetail = EmptyPropertyDetailFragment.newInstance()
            fragmentTransaction.add(R.id.activity_property_detail_container, fragmentEmptyPropertyDetail!!)
        }
        fragmentTransaction.commit()
    }

    override fun onListFragmentInteraction(propertyId: Int) {
        this.propertyId = propertyId
        if (fragmentPropertyDetail == null && fragmentEmptyPropertyDetail == null) {
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(INTENT_RESULT_TO_DETAIL, propertyId)
            startActivity(intent)
        } else if (fragmentPropertyDetail == null && fragmentEmptyPropertyDetail != null) {
            addFragment(propertyId, fragmentEmptyPropertyDetail!!)
        } else {
            addFragment(propertyId, fragmentPropertyDetail!!)
        }
    }

    private fun addFragment(propertyId: Int, removeThisFragment: Fragment) {
        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.remove(removeThisFragment)
        fragmentPropertyDetail = PropertyDetailFragment.newInstance(propertyId)
        fragmentTransaction.add(R.id.activity_property_detail_container, fragmentPropertyDetail!!)
        fragmentTransaction.commit()
    }

    private fun findsFragment() {
        supportFragmentManager.findFragmentById(R.id.activity_property_list_container)
        if (containerPropertyDetail == null && activity_property_detail_container != null) {
            fragmentPropertyDetail = supportFragmentManager.findFragmentById(R.id.activity_property_detail_container)
            if (fragmentPropertyDetail == null) {
                activity_property_list_container.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            }
        }
    }

}