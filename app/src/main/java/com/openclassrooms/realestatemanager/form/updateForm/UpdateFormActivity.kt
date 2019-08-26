package com.openclassrooms.realestatemanager.form.updateForm

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.openclassrooms.realestatemanager.form.updateForm.models.*
import com.openclassrooms.realestatemanager.propertyDetail.INTENT_DETAIL_TO_UPDATE
import kotlinx.android.synthetic.main.form.*
import java.text.SimpleDateFormat
import java.util.*

class UpdateFormActivity: FormBaseActivity() {

    private val setUpdateFormViewModel: SetUpdateFormViewModel by lazy { ViewModelProviders.of(this, GetInjection.provideViewModelFactory(applicationContext)).get(SetUpdateFormViewModel::class.java) }
    private val getUpdateFormViewModel: GetUpdateFormViewModel by lazy { ViewModelProviders.of(this, SetInjection.provideViewModelFactory(applicationContext)).get(GetUpdateFormViewModel::class.java) }
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
        setUpdateFormViewModel.fullNameAgents.observeOnce(this, Observer { setDropDownMenuForAgentField(it) })
    }

    private fun retrievesDataFromDatabase() {
        setUpdateFormViewModel.getProperty(propertyId).observeOnce(this, Observer { filledFormWithPropertyData(it) })
        setUpdateFormViewModel.getLocationsOfInterest(propertyId).observeOnce(this, Observer { filledFormWithLocationsOfInterestData(it) })
        setUpdateFormViewModel.getPropertyPhotos(propertyId, applicationContext).observe(this, Observer { filledFormWithPropertyPhotos(it) })
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
            form_full_name_agent.setText(fullNameAgent, false)
            form_select_entry_date.text = entryDate
            this@UpdateFormActivity.entryDate = entryDate
            form_is_available_switch.visibility = View.VISIBLE
            this@UpdateFormActivity.available = available
            form_is_available_switch.isChecked = available
            form_is_available_switch.setOnCheckedChangeListener { _, isChecked ->
                this@UpdateFormActivity.available = isChecked
                if (!isChecked) {
                    form_sale_date_layout.visibility = View.VISIBLE
                    form_select_sale_date.setOnClickListener { initSaleDatePickerDialog() }
                } else {
                    form_sale_date_layout.visibility = View.GONE
                }
            }
            if (!form_is_available_switch.isChecked) {
                form_sale_date_layout.visibility = View.VISIBLE
                form_select_sale_date.setOnClickListener { initSaleDatePickerDialog() }
                form_select_sale_date.text = saleDate
                this@UpdateFormActivity.saleDate = saleDate
            }
        }
    }

    private fun initSaleDatePickerDialog() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                saleDateSetListener,
                year, month, day)
        Objects.requireNonNull(datePickerDialog.window)
                .setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        datePickerDialog.show()
    }

    private var saleDateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
        calendar.set(year, month, dayOfMonth)
        val visualFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        saleDate = visualFormat.format(calendar.time)
        form_select_sale_date.text = saleDate
        saleDateLong = calendar.timeInMillis
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
        checkIfAddressHasBeenChanged()
        checkIfPropertyHasBeenChanged()
        getUpdateFormViewModel.updateLocationsOfInterest(getNewLocationsOfInterest())
    }
    
    private fun checkIfAddressHasBeenChanged() {
        if (path != entryPropertyModelProcessed.path
                || complement != entryPropertyModelProcessed.complement
                || district != entryPropertyModelProcessed.district
                || city != entryPropertyModelProcessed.city
                || postalCode != entryPropertyModelProcessed.postalCode
                || country != entryPropertyModelProcessed.country) {
            getUpdateFormViewModel.updateAddress(getNewAddress())
        }
    }

    private fun checkIfPropertyHasBeenChanged() {
        if (type != entryPropertyModelProcessed.type
                || price != entryPropertyModelProcessed.price
                || surface != entryPropertyModelProcessed.surface
                || rooms != entryPropertyModelProcessed.rooms
                || bedrooms != entryPropertyModelProcessed.bedrooms
                || bathrooms != entryPropertyModelProcessed.bathrooms
                || description != entryPropertyModelProcessed.description
                || available != entryPropertyModelProcessed.available
                || entryDate != entryPropertyModelProcessed.entryDate
                || fullNameAgent != entryPropertyModelProcessed.fullNameAgent) {
            getUpdateFormViewModel.updateProperty(getNewProperty())
        }
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

    private fun getNewProperty() =
            PropertyModelRaw(
                    id = propertyId,
                    type = type,
                    price = price,
                    surface = surface,
                    rooms = rooms,
                    bedrooms = bedrooms,
                    bathrooms = bathrooms,
                    description = description,
                    available = available,
                    entryDate = entryDateLong,
                    saleDate = saleDateLong,
                    addressId = entryPropertyModelProcessed.addressId,
                    fullNameAgent = fullNameAgent
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