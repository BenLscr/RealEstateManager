package com.openclassrooms.realestatemanager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.PropertyList.PropertyListFragment
import com.openclassrooms.realestatemanager.PropertyList.dummy.DummyContent
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), PropertyListFragment.OnListFragmentInteractionListener  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        this.configureToolbar()

        this.configureDrawerLayout()
        this.configureNavigationView()

        this.initFragment()
    }

    private fun configureToolbar() = setSupportActionBar(toolbar)

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
            R.id.create_button -> { println("do something"); true }
            R.id.add_button -> { println("do something"); true }
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
    private fun initFragment() {
        val propertyListFragment: PropertyListFragment = PropertyListFragment.newInstance()
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_home_container, propertyListFragment).commit() //TODO MODIFIER LA MANIERE D APPELER L ID
    }


    override fun onListFragmentInteraction(item: DummyContent.DummyItem?) {
    }
}
