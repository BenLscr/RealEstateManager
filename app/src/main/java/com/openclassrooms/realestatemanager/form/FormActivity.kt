package com.openclassrooms.realestatemanager.form

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.injections.Injection
import com.openclassrooms.realestatemanager.form.media.MediaFormFragment
import com.openclassrooms.realestatemanager.form.models.FormModelRaw
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.form.*
import kotlinx.android.synthetic.main.toolbar.*
import java.text.SimpleDateFormat
import java.util.*

class FormActivity : AppCompatActivity(), IPickResult, MediaFormFragment.OnListFragmentInteractionListener {

    private val formViewModel: FormViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(applicationContext)).get(FormViewModel::class.java) }

    private val mediaFormFragment = MediaFormFragment.newInstance()
    private var calendar = Calendar.getInstance()
    private var photo: Bitmap? = null
    private var wording: String = ""
    private val tempListFormPhotoAndWording = mutableListOf<FormPhotoAndWording>()
    private var formModelRaw = FormModelRaw()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        configureToolbar()
        fillEveryDropDownMenu()
        addEveryListener()
        addMediaFormFragment()
    }

    private fun configureToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun fillEveryDropDownMenu() {
        //Wording //
        form_wording.setOnTouchListener { _, _ ->
            form_wording.showDropDown()
            return@setOnTouchListener true
        }
        ArrayAdapter.createFromResource(this, R.array.form_wording, R.layout.dropdown_menu_form_item
        ).also { adapter ->
            adapter.setDropDownViewResource(R.layout.dropdown_menu_form_item)
            form_wording.setAdapter(adapter)
        }
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
        form_photo_button.setOnClickListener { PickImageDialog.build(PickSetup()).show(this) } /*Possibility to take a video*/
        form_wording.doAfterTextChanged { wording = it.toString() }
        form_add_button_photo.setOnClickListener { checkIfFormPhotoAndWordingIsCompleted() }
        form_cancel_button_photo.setOnClickListener { resetPhotoButton() }
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
        }
        form_select_entry_date.setOnClickListener { initBeginDatePickerDialog() }
        form_cancel_button.setOnClickListener { finish() }
        form_add_button.setOnClickListener {
            shareModelToTheViewModel()
            finish()
        }
    }

    override fun onPickResult(r: PickResult) {
        if (r.error == null) {
            if(form_photo_plus.isVisible) {
                form_photo_plus.visibility = View.GONE
                form_photo_filled.visibility = View.VISIBLE
            }
            photo = r.bitmap
            form_photo_filled.setImageBitmap(photo)
        } else {
        }
    }

    private fun checkIfFormPhotoAndWordingIsCompleted() {
        if (photo != null && wording.isNotEmpty()) {
            tempListFormPhotoAndWording.add(FormPhotoAndWording(photo!!, wording))
            shareListToMediaFormFragment()
        } else if (photo == null) {
            Toast.makeText(applicationContext, R.string.form_error_media_photo, Toast.LENGTH_LONG).show()
        } else if (wording.isEmpty()) {
            Toast.makeText(applicationContext, R.string.form_error_media_wording, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, R.string.form_error_mandatory_fields_and_photo, Toast.LENGTH_LONG).show()
        }
    }

    private fun shareListToMediaFormFragment() {
        mediaFormFragment.shareNewElementsInListToRecyclerView(tempListFormPhotoAndWording)
        resetPhotoButton()
    }

    private fun resetPhotoButton() {
        if (!form_photo_plus.isVisible) {
            form_photo_filled.visibility = View.GONE
            form_photo_plus.visibility = View.VISIBLE
            photo = null
        }
        form_wording.text.clear()
        wording = ""
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

    private var entryDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
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
            if (tempListFormPhotoAndWording.isNotEmpty()
                    && path.isNotEmpty()
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
                listFormPhotoAndWording.addAll(tempListFormPhotoAndWording)
                context = applicationContext
                formViewModel.startBuildingModelsForDatabase(formModelRaw)
            } else if (tempListFormPhotoAndWording.isNotEmpty()
                    && path.isEmpty()
                    || district.isEmpty()
                    || city.isEmpty()
                    || postalCode.isEmpty()
                    || country.isEmpty()
                    || price.isEmpty()
                    || type.isEmpty()
                    || surface.isEmpty()
                    || rooms.isEmpty()
                    || bathrooms.isEmpty()
                    || bedrooms.isEmpty()
                    || fullNameAgent.isEmpty()
                    || entryDate > 0) {
                Toast.makeText(this@FormActivity,
                        R.string.form_error_mandatory_fiels,
                        Toast.LENGTH_LONG).show()
            } else if (tempListFormPhotoAndWording.isEmpty()
                    && path.isNotEmpty()
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
                Toast.makeText(this@FormActivity,
                        R.string.form_error_photo,
                        Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this@FormActivity,
                        R.string.form_error_mandatory_fields_and_photo,
                        Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun addMediaFormFragment() {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.form_property_photo_container, mediaFormFragment).commit()
    }


    override fun onListFragmentInteraction(position: Int) {
        showAlertDialogForConfirmation(position)
    }

    private fun showAlertDialogForConfirmation(position: Int) {
        AlertDialog.Builder(this)
                .setMessage("Are you sure you want to delete photo $position ?")
                .setPositiveButton("Yes") { dialog, id -> tempListFormPhotoAndWording.removeAt(position)
                    shareListToMediaFormFragment()
                }
                .setNegativeButton("No", null)
                .show()
    }

}