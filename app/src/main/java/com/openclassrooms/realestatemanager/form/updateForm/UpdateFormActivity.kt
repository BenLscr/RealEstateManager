package com.openclassrooms.realestatemanager.form.updateForm

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.openclassrooms.realestatemanager.INTENT_HOME_TO_UPDATE
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.form.FormBaseActivity
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording
import com.openclassrooms.realestatemanager.form.updateForm.injections.GetInjection
import com.openclassrooms.realestatemanager.form.updateForm.injections.SetInjection
import com.openclassrooms.realestatemanager.form.updateForm.models.AddressModelRaw
import com.openclassrooms.realestatemanager.form.updateForm.models.LocationsOfInterestModelProcessed
import com.openclassrooms.realestatemanager.form.updateForm.models.LocationsOfInterestModelRaw
import com.openclassrooms.realestatemanager.form.updateForm.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.propertyDetail.INTENT_DETAIL_TO_UPDATE
import kotlinx.android.synthetic.main.form.*

class UpdateFormActivity: FormBaseActivity() {

    private val getUpdateFormViewModel: GetUpdateFormViewModel by lazy { ViewModelProviders.of(this, GetInjection.provideViewModelFactory(applicationContext)).get(GetUpdateFormViewModel::class.java) }
    private val setUpdateFormViewModel: SetUpdateFormViewModel by lazy { ViewModelProviders.of(this, SetInjection.provideViewModelFactory(applicationContext)).get(SetUpdateFormViewModel::class.java) }
    private var propertyId: Int = 0

    private lateinit var entryListFormPhotoAndWording: List<FormPhotoAndWording>
    private lateinit var entryPropertyModelProcessed: PropertyModelProcessed
    private lateinit var entryLocationsOfInterestModelProcessed: LocationsOfInterestModelProcessed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        retrievesIntent()
        configureToolbar()
        fillEveryDropDownMenu()
        addEveryListener()
        addMediaFormFragment()
        retrievesDataFromDatabase()
        setEveryAwesomeValidation()
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
        getUpdateFormViewModel.fullNameAgents.observeOnce(this, Observer { setDropDownMenuForAgentField(it) })
    }

    private fun retrievesDataFromDatabase() {
        getUpdateFormViewModel.getProperty(propertyId).observeOnce(this, Observer { filledFormWithPropertyData(it) })
        getUpdateFormViewModel.getLocationsOfInterest(propertyId).observeOnce(this, Observer { filledFormWithLocationsOfInterestData(it) })
    }

    private fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
        observe(lifecycleOwner, object : Observer<T> {
            override fun onChanged(t: T?) {
                observer.onChanged(t)
                removeObserver(this)
            }
        })
    }

    private fun filledFormWithPropertyData(propertyModelProcessed: PropertyModelProcessed) {
        with(propertyModelProcessed) {
            entryPropertyModelProcessed = this
            form_path_edit_text.setText(path)
            form_complement_edit_text.setText(complement)
            form_district.setText(district, false)
            form_city.setText(city, false)
            form_postal_code_edit_text.setText(postalCode)
            form_country.setText(country, false)
            form_price_edit_text.setText(price)
            form_description_edit_text.setText(description)
            form_type.setText(type, false)
            form_surface_edit_text.setText(surface)
            form_rooms_edit_text.setText(rooms)
            form_bathrooms_edit_text.setText(bathrooms)
            form_bedrooms_edit_text.setText(bedrooms)
            form_full_name_agent.setText(agent, false)
            form_select_entry_date.text = entryDate
            form_is_available_switch.visibility = View.VISIBLE
            form_is_available_switch.isChecked = available
            if (!form_is_available_switch.isChecked) {
                form_sale_date_layout.visibility = View.VISIBLE
                form_select_sale_date.text = saleDate
            }
            getUpdateFormViewModel.getPropertyPhotos(propertyId, path , applicationContext).observe(this@UpdateFormActivity, Observer { filledFormWithPropertyPhotos(it) })
        }
    }

    private fun filledFormWithPropertyPhotos(listFormPhotoAndWording: List<FormPhotoAndWording>) {
        entryListFormPhotoAndWording = listFormPhotoAndWording
        this.listFormPhotoAndWording.addAll(listFormPhotoAndWording)
        shareListToMediaFormFragment()
    }

    private fun filledFormWithLocationsOfInterestData(locationsOfInterestModelProcessed: LocationsOfInterestModelProcessed) {
        entryLocationsOfInterestModelProcessed = locationsOfInterestModelProcessed
        if (locationsOfInterestModelProcessed.school) {
            form_school_checkbox.isChecked = true
            school = locationsOfInterestModelProcessed.school
        }
        if (locationsOfInterestModelProcessed.commerces) {
            form_commerces_checkbox.isChecked = true
            commerces = locationsOfInterestModelProcessed.commerces
        }
        if (locationsOfInterestModelProcessed.park) {
            form_park_checkbox.isChecked = true
            park = locationsOfInterestModelProcessed.park
        }
        if (locationsOfInterestModelProcessed.subways) {
            form_subways_checkbox.isChecked = true
            subways = locationsOfInterestModelProcessed.subways
        }
        if (locationsOfInterestModelProcessed.train) {
            form_train_checkbox.isChecked = true
            train = locationsOfInterestModelProcessed.train
        }
    }

    override fun shareModelToTheViewModel() {
        //TODO : update photos
        /*if (entryListFormPhotoAndWording == listFormPhotoAndWording) {
            Log.e("Share", "Are equals")
        } else {
            Log.e("Share", "Are not equals")
        }*/
        if (path != entryPropertyModelProcessed.path
                || complement != entryPropertyModelProcessed.complement
                || district != entryPropertyModelProcessed.district
                || city != entryPropertyModelProcessed.city
                || postalCode != entryPropertyModelProcessed.postalCode
                || country != entryPropertyModelProcessed.country) {
            setUpdateFormViewModel.updateAddress(getNewAddress())
        }
        //TODO : update agent
        //TODO : update property
        setUpdateFormViewModel.updateLocationsOfInterest(getNewLocationsOfInterest())
    }

    private fun getNewAddress() =
            AddressModelRaw(
                    id = entryPropertyModelProcessed.addressId,
                    path = path,
                    complement = complement,
                    district = district,
                    city = city,
                    postalCode = postalCode,
                    country = country
            )

    private fun getNewLocationsOfInterest() =
            LocationsOfInterestModelRaw(
                    propertyId = propertyId,
                    school = if (entryLocationsOfInterestModelProcessed.school != school) { school } else { null },
                    commerces = if (entryLocationsOfInterestModelProcessed.commerces != commerces) { commerces } else { null },
                    park = if (entryLocationsOfInterestModelProcessed.park != park) { park } else { null },
                    subways = if (entryLocationsOfInterestModelProcessed.subways != subways) { subways } else { null },
                    train = if (entryLocationsOfInterestModelProcessed.train != train) { train } else { null }
            )

}