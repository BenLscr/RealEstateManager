package com.openclassrooms.realestatemanager.form.addForm

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.FormBaseActivity
import com.openclassrooms.realestatemanager.form.addForm.injections.Injection
import com.openclassrooms.realestatemanager.form.media.MediaFormFragment
import com.openclassrooms.realestatemanager.form.addForm.models.FormModelRaw
import kotlinx.android.synthetic.main.form.*

class AddFormActivity : FormBaseActivity(), MediaFormFragment.OnListFragmentInteractionListener {

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
                && entryDate > 0) {
            val formModelRaw = FormModelRaw(
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
                    entryDate = entryDate,
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
        if (entryDate <= 0) {
            form_error_entry_date.visibility = View.VISIBLE
        } else {
            form_error_entry_date.visibility = View.GONE
        }
    }

    private fun setEveryAwesomeValidation() {
        mAwesomeValidation.addValidation(this, R.id.form_path_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_district_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_city_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_postal_code_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_country_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_description_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_price_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_type_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_square_meters_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_rooms_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_bathrooms_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_bedrooms_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
        mAwesomeValidation.addValidation(this, R.id.form_agent_layout, RegexTemplate.NOT_EMPTY, R.string.form_error_field_empty)
    }

}