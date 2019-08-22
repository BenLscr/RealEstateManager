package com.openclassrooms.realestatemanager.form.updateForm

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.INTENT_HOME_TO_UPDATE
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.FormBaseActivity
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording
import com.openclassrooms.realestatemanager.form.updateForm.injections.Injection
import com.openclassrooms.realestatemanager.form.updateForm.models.LocationsOfInterestModelProcessed
import com.openclassrooms.realestatemanager.form.updateForm.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.propertyDetail.INTENT_DETAIL_TO_UPDATE
import kotlinx.android.synthetic.main.form.*
import kotlinx.android.synthetic.main.property_detail_fragment.*

class UpdateFormActivity: FormBaseActivity() {

    private val updateFormViewModel: UpdateFormViewModel by lazy { ViewModelProviders.of(this, Injection.provideViewModelFactory(applicationContext)).get(UpdateFormViewModel::class.java) }
    private var propertyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        retrievesIntent()
        configureToolbar()
        fillEveryDropDownMenu()
        addEveryListener()
        addMediaFormFragment()
        retrievesDataFromDatabase()
    }

    private fun retrievesIntent() {
        if (intent.hasExtra(INTENT_DETAIL_TO_UPDATE)) {
            propertyId = intent.getIntExtra(INTENT_DETAIL_TO_UPDATE, 0)
        } else if (intent.hasExtra(INTENT_HOME_TO_UPDATE)) {
            propertyId = intent.getIntExtra(INTENT_HOME_TO_UPDATE, 0)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId) {
            android.R.id.home -> { finish() ; true }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun getAgentsNameForDropDownMenu() {
        updateFormViewModel.fullNameAgents.observe(this, Observer { setDropDownMenuForAgentField(it) })
    }

    private fun retrievesDataFromDatabase() {
        updateFormViewModel.getProperty(propertyId).observe(this, Observer { filledFormWithPropertyData(it) })
        updateFormViewModel.getLocationsOfInterest(propertyId).observe(this, Observer { filledFormWithLocationsOfInterestData(it) })
    }

    private fun filledFormWithPropertyData(propertyModelProcessed: PropertyModelProcessed) {
        with(propertyModelProcessed) {
            form_path_edit_text.setText(path)
            form_complement_edit_text.setText(complement)
            if (district != null) {
                form_district.setText(form_district.adapter.getItem(district).toString(), false)
            }
            if (city != null) {
                form_city.setText(form_city.adapter.getItem(city).toString(), false)
            }
            form_postal_code_edit_text.setText(postalCode)
            if (country != null) {
                form_country.setText(form_country.adapter.getItem(country).toString(), false)
            }
            form_price_edit_text.setText(price)
            form_description_edit_text.setText(description)
            form_type.setText(form_type.adapter.getItem(type).toString(), false)
            form_surface_edit_text.setText(surface)
            form_rooms_edit_text.setText(rooms)
            form_bathrooms_edit_text.setText(bathrooms)
            form_bedrooms_edit_text.setText(bedrooms)
            form_full_name_agent.setText(form_full_name_agent.adapter.getItem(agent).toString(), false)
            form_select_entry_date.text = entryDate
            form_is_available_switch.visibility = View.VISIBLE
            form_is_available_switch.isChecked = available
            if (!form_is_available_switch.isChecked) {
                form_sale_date_layout.visibility = View.VISIBLE
                form_select_sale_date.text = saleDate
            }
            updateFormViewModel.getPropertyPhotos(propertyId, path , applicationContext).observe(this@UpdateFormActivity, Observer { filledFormWithPropertyPhotos(it) })
        }
    }

    private fun filledFormWithPropertyPhotos(listFormPhotoAndWording: List<FormPhotoAndWording>) {
        this.listFormPhotoAndWording.addAll(listFormPhotoAndWording)
        shareListToMediaFormFragment()
    }

    private fun filledFormWithLocationsOfInterestData(locationsOfInterestModelProcessed: LocationsOfInterestModelProcessed) {
        with(locationsOfInterestModelProcessed) {
            if (school) {
                form_school_checkbox.isChecked = true
            }
            if (commerces) {
                form_commerces_checkbox.isChecked = true
            }
            if (park) {
                form_park_checkbox.isChecked = true
            }
            if (subways) {
                form_subways_checkbox.isChecked = true
            }
            if (train) {
                form_train_checkbox.isChecked = true
            }
        }
    }

    override fun shareModelToTheViewModel() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}