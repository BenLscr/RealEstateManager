package com.openclassrooms.realestatemanager.propertyDetail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.openclassrooms.realestatemanager.INTENT_HOME_TO_DETAIL
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.updateForm.UpdateFormActivity
import com.openclassrooms.realestatemanager.result.INTENT_RESULT_TO_DETAIL
import kotlinx.android.synthetic.main.toolbar.*

const val INTENT_DETAIL_TO_UPDATE = "INTENT_DETAIL_TO_UPDATE"

class PropertyDetailActivity : AppCompatActivity() {

    private var propertyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_detail)
        retrievesIntent()
        configureToolbar()
        initAndAddFragment()
    }

    private fun retrievesIntent() {
        if (intent.hasExtra(INTENT_HOME_TO_DETAIL)) {
            propertyId = intent.getIntExtra(INTENT_HOME_TO_DETAIL, 0)
        } else if (intent.hasExtra(INTENT_RESULT_TO_DETAIL)){
            propertyId = intent.getIntExtra(INTENT_RESULT_TO_DETAIL, 0)
        }
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu_toolbar_phone, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> { finish(); true }
            R.id.update_button -> {
                if (propertyId != 0) {
                    val intent = Intent(this, UpdateFormActivity::class.java)
                    intent.putExtra(INTENT_DETAIL_TO_UPDATE, propertyId)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, R.string.update_button_property_detail, Toast.LENGTH_LONG).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initAndAddFragment() {
        val propertyDetailFragment: PropertyDetailFragment = PropertyDetailFragment.newInstance(propertyId)
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.activity_property_detail_container, propertyDetailFragment).commit()
    }

}