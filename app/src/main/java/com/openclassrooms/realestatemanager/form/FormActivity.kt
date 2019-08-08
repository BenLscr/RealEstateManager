package com.openclassrooms.realestatemanager.form

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.injections.Injection
import com.openclassrooms.realestatemanager.form.models.FormModelRaw
import kotlinx.android.synthetic.main.form.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity() {

    private val formViewModel: FormViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(applicationContext)).get(FormViewModel::class.java) }


    private var calendar = Calendar.getInstance()
    private var formModelRaw = FormModelRaw()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

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
        //Agent
        formViewModel.fullNameAgents.observe(this, androidx.lifecycle.Observer { setDropDownMenuForAgentField(it) })
    }

    private fun setDropDownMenuForAgentField(fullNameAgents: List<String>) {
        val adapter = ArrayAdapter(this, R.layout.dropdown_menu_form_item, fullNameAgents)
        form_full_name_agent.setAdapter(adapter)
    }

    private fun addEveryListener() {
        with(formModelRaw) {
            form_path_edit_text.doAfterTextChanged { path = it.toString() }
            form_complement_edit_text.doAfterTextChanged { complement = it.toString() }
            form_district.doAfterTextChanged { district = it.toString() }
            form_city.doAfterTextChanged { city = it.toString() }
            form_postal_code_edit_text.doAfterTextChanged { postalCode = it.toString() }
            form_country.doAfterTextChanged { country = it.toString() }
            form_price_edit_text.doAfterTextChanged { price = it.toString() }
            form_description_edit_text.doAfterTextChanged { description = it.toString() }
            form_type.doAfterTextChanged { type = it.toString() }
            form_surface_edit_text.doAfterTextChanged { surface = it.toString() }
            form_rooms_edit_text.doAfterTextChanged { rooms = it.toString() }
            form_bathrooms_edit_text.doAfterTextChanged { bathrooms = it.toString() }
            form_bedrooms_edit_text.doAfterTextChanged { bedrooms = it.toString() }
            form_full_name_agent.doAfterTextChanged { fullNameAgent = it.toString() }
            form_select_entry_date.setOnClickListener { initBeginDatePickerDialog() }
            form_cancel_button.setOnClickListener { finish() }
            form_add_button.setOnClickListener {
                shareModelToTheViewModel()
                finish()
            }
        }
    }

    private fun initBeginDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                entryDateSetListener,
                year, month, day)
        Objects.requireNonNull(datePickerDialog.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }

    private var entryDateSetListener = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        val visualFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val entryDateToShow = visualFormat.format(calendar.time)
        form_select_entry_date.text = entryDateToShow
        formModelRaw.entryDate = calendar.timeInMillis
    }

    fun onCheckboxClicked(view: View) {
        with(formModelRaw) {
            when(view as CheckBox) {
                form_school_checkbox -> school = form_school_checkbox.isChecked
                form_commerces_checkbox -> commerces = form_commerces_checkbox.isChecked
                form_park_checkbox -> park = form_park_checkbox.isChecked
                form_subways_checkbox -> subways = form_subways_checkbox.isChecked
                form_train_checkbox -> train = form_train_checkbox.isChecked
            }
        }
    }

    private fun shareModelToTheViewModel() {
        with(formModelRaw) {
            if (path.isNotEmpty()
                    && district.isNotEmpty()
                    && city.isNotEmpty()
                    && postalCode.isNotEmpty()
                    && country.isNotEmpty()
                    && price.isNotEmpty()
                    && type.isNotEmpty()
                    && surface.isNotEmpty()
                    && rooms.isNotEmpty()
                    && bathrooms.isNotEmpty()
                    && bedrooms.isNotEmpty()
                    && fullNameAgent.isNotEmpty()
                    && entryDate > 0) {
                formViewModel.startBuildingModelsForDatabase(formModelRaw)
            } else {
                Toast.makeText(this@FormActivity,
                        "Please complete all mandatory fields.",
                        Toast.LENGTH_LONG).show()
            }
        }
    }

}