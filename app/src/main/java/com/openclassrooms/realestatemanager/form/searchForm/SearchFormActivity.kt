package com.openclassrooms.realestatemanager.form.searchForm

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.searchForm.injections.Injection
import com.openclassrooms.realestatemanager.form.searchForm.models.SearchFormModelRaw
import kotlinx.android.synthetic.main.activity_search_form.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class SearchFormActivity : AppCompatActivity() {

    private val calendar: Calendar = Calendar.getInstance()
    private val searchFormModelRaw = SearchFormModelRaw()
    private val searchFormViewModel: SearchFormViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(applicationContext)).get(SearchFormViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_form)

        configureToolbar()
        fillEveryDropDownMenu()
        addEveryListener()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun fillEveryDropDownMenu() {
        //District
        ArrayAdapter.createFromResource(this, R.array.search_district, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            search_district.setAdapter(adapter)
        }
        //City
        ArrayAdapter.createFromResource(this, R.array.search_city, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            search_city.setAdapter(adapter)
        }
        //Country
        ArrayAdapter.createFromResource(this, R.array.search_country, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            search_country.setAdapter(adapter)
        }
        //Type
        ArrayAdapter.createFromResource(this, R.array.search_type, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            search_type.setAdapter(adapter)
        }
    }

    private fun addEveryListener() {
        with(searchFormModelRaw) {
            search_district.doAfterTextChanged { district = it.toString() }
            search_city.doAfterTextChanged { city = it.toString() }
            search_postal_code_edit_text.doAfterTextChanged { postalCode = it.toString() }
            search_country.doAfterTextChanged { country = it.toString() }
            search_price_min_edit_text.doAfterTextChanged { minPrice = it.toString() }
            search_price_max_edit_text.doAfterTextChanged { maxPrice = it.toString() }
            search_type.doAfterTextChanged { type = it.toString() }
            search_min_surface_edit_text.doAfterTextChanged { minSurface = it.toString() }
            search_max_surface_edit_text.doAfterTextChanged { maxSurface = it.toString() }
            search_rooms_edit_text.doAfterTextChanged { rooms = it.toString() }
            search_bathrooms_edit_text.doAfterTextChanged { bathrooms = it.toString() }
            search_bedrooms_edit_text.doAfterTextChanged { bedrooms = it.toString() }
            search_radio_available.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) { availability = true }
            }
            search_radio_sold.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) { availability = false }
            }
            search_radio_indifferent.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) { availability = null }
            }
            search_radio_indifferent.isChecked = true
            search_clear_date.setOnClickListener {
                search_select_date.text = getString(R.string.select_a_date)
                dateLong = 0
            }
        }
        search_select_date.setOnClickListener { initDatePickerDialog() }
        search_button.setOnClickListener { goSearch() }
        search_cancel_button.setOnClickListener { finish() }
    }

    private fun initDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                dateSetListener,
                year, month, day)
        Objects.requireNonNull(datePickerDialog.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }

    private var dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        val visualFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val date = visualFormat.format(calendar.time)
        search_select_date.text = date
        searchFormModelRaw.dateLong = calendar.timeInMillis
    }

    fun onCheckboxClicked(view: View) {
        when(view as CheckBox) {
            search_school_checkbox -> searchFormModelRaw.school = search_school_checkbox.isChecked
            search_commerces_checkbox -> searchFormModelRaw.commerces = search_commerces_checkbox.isChecked
            search_park_checkbox -> searchFormModelRaw.park = search_park_checkbox.isChecked
            search_subways_checkbox -> searchFormModelRaw.subways = search_subways_checkbox.isChecked
            search_train_checkbox -> searchFormModelRaw.train = search_train_checkbox.isChecked
        }
    }

    private fun goSearch() {
        with(searchFormModelRaw) {
            if (district.isNotEmpty()
                    || city .isNotEmpty()
                    || postalCode.isNotEmpty()
                    || country.isNotEmpty() || minPrice.isNotEmpty()
                    || maxPrice.isNotEmpty()
                    || type.isNotEmpty()
                    || minSurface.isNotEmpty()
                    || maxSurface.isNotEmpty()
                    || rooms.isNotEmpty()
                    || bathrooms.isNotEmpty()
                    || bedrooms.isNotEmpty()
                    || school
                    || commerces
                    || park
                    || subways
                    || train
                    || availability != null
                    || dateLong > 0) {
                searchFormViewModel.searchPropertiesId(searchFormModelRaw)
                        .observe(this@SearchFormActivity,
                                androidx.lifecycle.Observer { it.map { property -> Log.e("observer", property.toString()) }  })
            }  else {
                Log.e("goSearch", "nothing filled")
            }
        }
    }

}