package com.openclassrooms.realestatemanager.form.addForm

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.FormBaseActivity
import com.openclassrooms.realestatemanager.form.addForm.injections.Injection
import com.openclassrooms.realestatemanager.form.media.MediaFormFragment
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

    override fun shareModelToTheViewModel() {
        checkIfFormIsFilled()
        if (listFormPhotoAndWording.isNotEmpty()
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
                && entryDateLong > 0) {
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

    private fun checkIfFormIsFilled() {
        mAwesomeValidation.validate()
        if (listFormPhotoAndWording.isEmpty()) {
            form_error_photo.visibility = View.VISIBLE
        } else {
            form_error_photo.visibility = View.GONE
        }
        if (entryDateLong <= 0) {
            form_error_entry_date.visibility = View.VISIBLE
        } else {
            form_error_entry_date.visibility = View.GONE
        }
    }

}