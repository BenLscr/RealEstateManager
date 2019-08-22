package com.openclassrooms.realestatemanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.emptyPropertyDetail.EmptyPropertyDetailFragment
import com.openclassrooms.realestatemanager.form.addForm.AddFormActivity
import com.openclassrooms.realestatemanager.form.updateForm.UpdateFormActivity
import com.openclassrooms.realestatemanager.propertyDetail.PropertyDetailActivity
import com.openclassrooms.realestatemanager.propertyDetail.PropertyDetailFragment
import com.openclassrooms.realestatemanager.propertyList.PropertyListFragment
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

const val INTENT_HOME_TO_DETAIL = "INTENT_HOME_TO_DETAIL"
const val INTENT_HOME_TO_UPDATE = "INTENT_HOME_TO_UPDATE"

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
     * Inflate the appropriate menu and add it to the Toolbar.
     */
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        if (activity_property_detail_container != null) {
            menuInflater.inflate(R.menu.home_menu_toolbar_tablet, menu)
        } else {
            menuInflater.inflate(R.menu.home_menu_toolbar_phone, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            R.id.search_button -> { Log.e("Search","do something"); true }
            R.id.update_button -> {
                if (fragmentPropertyDetail != null) {
                    val intent = Intent(this, UpdateFormActivity::class.java)
                    intent.putExtra(INTENT_HOME_TO_UPDATE, propertyId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, R.string.update_button_select_property_first, Toast.LENGTH_LONG).show()
                }
                true
            }
            R.id.add_button -> {
                val intent = Intent(this, AddFormActivity::class.java)
                intent.putExtra(INTENT_HOME_TO_UPDATE, propertyId)
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
    private val fragmentPropertyList = PropertyListFragment.newInstance()
    private var fragmentPropertyDetail: PropertyDetailFragment? = null
    private var fragmentEmptyPropertyDetail: EmptyPropertyDetailFragment? = null
    private var containerPropertyDetail: Fragment? = supportFragmentManager.findFragmentById(R.id.activity_property_detail_container)
    private var propertyId: Int = 0

    private fun initAndAddFragment() {
        fragmentTransaction.add(R.id.activity_property_list_container, fragmentPropertyList)
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
            intent.putExtra(INTENT_HOME_TO_DETAIL, propertyId)
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

}
