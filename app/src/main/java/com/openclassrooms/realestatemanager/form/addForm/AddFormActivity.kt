package com.openclassrooms.realestatemanager.form.addForm

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.FormBaseActivity
import com.openclassrooms.realestatemanager.form.addForm.injections.Injection
import com.openclassrooms.realestatemanager.form.addForm.models.AddFormModelRaw
import kotlinx.android.synthetic.main.form.*

class AddFormActivity : FormBaseActivity() {

    private val addFormViewModel: AddFormViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(applicationContext)).get(AddFormViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        configureToolbar()
        fillEveryDropDownMenu()
        addEveryListener()
        addMediaFormFragment()
        setEveryAwesomeValidation()
    }

    override fun getAgentsNameForDropDownMenu() {
        addFormViewModel.fullNameAgents.observe(this, androidx.lifecycle.Observer { setDropDownMenuForAgentField(it) })
    }

    fun onCheckboxClicked(view: View) {
        when(view as CheckBox) {
            form_school_checkbox -> school = form_school_checkbox.isChecked
            form_commerces_checkbox -> commerces = form_commerces_checkbox.isChecked
            form_park_checkbox -> park = form_park_checkbox.isChecked
            form_subways_checkbox -> subways = form_subways_checkbox.isChecked
            form_train_checkbox -> train = form_train_checkbox.isChecked
        }
    }

    override fun shareModelToTheViewModel() {
        if (checkIfFormIsFilled()) {
            val formModelRaw = AddFormModelRaw(
                    listFormPhotoAndWording = listFormPhotoAndWording,
                    path = path,
                    complement = complement,
                    district = district,
                    city = city,
                    postalCode = postalCode,
                    country = country,
                    price = price,
                    description = description,
                    type = type,
                    surface= surface,
                    rooms = rooms,
                    bathrooms = bathrooms,
                    bedrooms = bedrooms,
                    fullNameAgent = fullNameAgent,
                    school = school,
                    commerces = commerces,
                    park = park,
                    subways = subways,
                    train = train,
                    available = available,
                    entryDate = entryDateLong,
                    context = applicationContext
            )
            addFormViewModel.startBuildingModelsForDatabase(formModelRaw)
            finish()
        }
    }

    private fun checkIfFormIsFilled(): Boolean {
        val bAwesomeValidation = mAwesomeValidation.validate()
        val bFormPhotoAndWording: Boolean = if (listFormPhotoAndWording.isEmpty()) {
            form_error_photo.visibility = View.VISIBLE
            false
        } else {
            form_error_photo.visibility = View.GONE
            true
        }
        val bEntryDateLong: Boolean = if (entryDateLong <= 0) {
            form_error_entry_date.visibility = View.VISIBLE
            false
        } else {
            form_error_entry_date.visibility = View.GONE
            true
        }
        return bAwesomeValidation && bFormPhotoAndWording && bEntryDateLong
    }

}