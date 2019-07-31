package com.openclassrooms.realestatemanager

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.form.FormActivity
import com.openclassrooms.realestatemanager.propertyDetail.PropertyDetailActivity
import com.openclassrooms.realestatemanager.propertyDetail.PropertyDetailFragment
import com.openclassrooms.realestatemanager.propertyList.PropertyListFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

const val INTENT_PROPERTY_ID = "INTENT_PROPERTY_ID"

class HomeActivity : AppCompatActivity(), PropertyListFragment.OnListFragmentInteractionListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        configureToolbar()

        configureDrawerLayout()
        configureNavigationView()

        initAndAddFragment()
    }

    private fun configureToolbar() { setSupportActionBar(toolbar) }

    /**
     * Inflate the menu and add it to the Toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.search_button -> { println("do something"); true }
            R.id.update_button -> { println("do something"); true }
            R.id.add_button -> {
                val intent = Intent(this, FormActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun configureDrawerLayout() {
        val toggle = ActionBarDrawerToggle(this, activity_home_drawer_layout,
                toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        activity_home_drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun configureNavigationView() {
        activity_home_nav_view.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.activity_home_drawer_temp -> { println("do something"); true }
                else -> false
            }
            this.activity_home_drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    //---FRAGMENT---\\
    private var fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
    private lateinit var fragmentPropertyList: PropertyListFragment
    private var fragmentPropertyDetail: PropertyDetailFragment? = null
    private var containerPropertyDetail: Fragment? = null

    private fun initAndAddFragment() {
        fragmentPropertyList = PropertyListFragment.newInstance()
        fragmentTransaction.add(R.id.activity_property_list_container, fragmentPropertyList)
        containerPropertyDetail = supportFragmentManager.findFragmentById(R.id.activity_property_detail_container)
        if (containerPropertyDetail == null && activity_property_detail_container != null) {
            addFragment()
        }
        fragmentTransaction.commit()
    }

    private fun addFragment(propertyId: Int? = null) {
        fragmentPropertyDetail = PropertyDetailFragment.newInstance(propertyId)
        fragmentTransaction.add(R.id.activity_property_detail_container, fragmentPropertyDetail!!)
    }

    override fun onListFragmentInteraction(propertyId: Int) {
        if (fragmentPropertyDetail == null) {
            val intent = Intent(this, PropertyDetailActivity::class.java)
            intent.putExtra(INTENT_PROPERTY_ID, propertyId)
            startActivity(intent)
        } else {
            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.remove(fragmentPropertyDetail!!)
            addFragment(propertyId)
            fragmentTransaction.commit()
        }
    }

}
