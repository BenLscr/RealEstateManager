package com.openclassrooms.realestatemanager.form

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.toolbar.*

class UpdateFormActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        configureToolbar()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> { finish() ; true }
            else -> super.onOptionsItemSelected(item)
        }
    }

}