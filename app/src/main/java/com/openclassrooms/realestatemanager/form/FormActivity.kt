package com.openclassrooms.realestatemanager.form

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.openclassrooms.realestatemanager.R
import kotlinx.android.synthetic.main.form.*
import kotlinx.android.synthetic.main.toolbar.*

class FormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        configureToolbar()
        prepareForm()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun prepareForm() {
        //District
        ArrayAdapter.createFromResource(this, R.array.form_district, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            form_district.setAdapter(adapter)
        }
        //City
        ArrayAdapter.createFromResource(this, R.array.form_city, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            form_city.setAdapter(adapter)
        }
        //Country
        ArrayAdapter.createFromResource(this, R.array.form_country, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            form_country.setAdapter(adapter)
        }
        //Type
        ArrayAdapter.createFromResource(this, R.array.form_type, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            form_type.setAdapter(adapter)
        }
    }

}