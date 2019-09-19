package com.openclassrooms.realestatemanager.form.updateForm

import android.app.DatePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.CheckBox
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

    private val setUpdateFormViewModel: SetUpdateFormViewModel by lazy { ViewModelProviders.of(this, SetInjection.provideViewModelFactory(applicationContext)).get(SetUpdateFormViewModel::class.java) }
    private val getUpdateFormViewModel: GetUpdateFormViewModel by lazy { ViewModelProviders.of(this, GetInjection.provideViewModelFactory(applicationContext)).get(GetUpdateFormViewModel::class.java) }
    private var propertyId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.form)

        retrievesIntent()
        configureToolbar()
        addEveryListener()
        fillEveryDropDownMenu()
        if (savedInstanceState == null) {
            addMediaFormFragment()
            retrievesDataFromDatabase()
        } else {
            shareListToMediaFormFragment()
        }
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
            setUpdateFormViewModel.entryPropertyModelProcessed = this
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
            this@UpdateFormActivity.entryDateLong = entryDateLong
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
            this@UpdateFormActivity.saleDateLong = saleDateLong
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
        setUpdateFormViewModel.entryListFormPhotoAndWording = listFormPhotoAndWording
        this.listFormPhotoAndWording.addAll(listFormPhotoAndWording)
        shareListToMediaFormFragment()
    }

    private fun filledFormWithLocationsOfInterestData(locationsOfInterestModelProcessed: LocationsOfInterestModelProcessed) {
        getUpdateFormViewModel.entryLocationsOfInterestModelProcessed = locationsOfInterestModelProcessed
        if (locationsOfInterestModelProcessed.school) {
            form_school_checkbox.isChecked = true
            getUpdateFormViewModel.school = locationsOfInterestModelProcessed.school
        }
        if (locationsOfInterestModelProcessed.commerces) {
            form_commerces_checkbox.isChecked = true
            getUpdateFormViewModel.commerces = locationsOfInterestModelProcessed.commerces
        }
        if (locationsOfInterestModelProcessed.park) {
            form_park_checkbox.isChecked = true
            getUpdateFormViewModel.park = locationsOfInterestModelProcessed.park
        }
        if (locationsOfInterestModelProcessed.subways) {
            form_subways_checkbox.isChecked = true
            getUpdateFormViewModel.subways = locationsOfInterestModelProcessed.subways
        }
        if (locationsOfInterestModelProcessed.train) {
            form_train_checkbox.isChecked = true
            getUpdateFormViewModel.train = locationsOfInterestModelProcessed.train
        }
    }

    fun onCheckboxClicked(view: View) {
        when(view as CheckBox) {
            form_school_checkbox -> getUpdateFormViewModel.school = form_school_checkbox.isChecked
            form_commerces_checkbox -> getUpdateFormViewModel.commerces = form_commerces_checkbox.isChecked
            form_park_checkbox -> getUpdateFormViewModel.park = form_park_checkbox.isChecked
            form_subways_checkbox -> getUpdateFormViewModel.subways = form_subways_checkbox.isChecked
            form_train_checkbox -> getUpdateFormViewModel.train = form_train_checkbox.isChecked
        }
    }

    override fun shareModelToTheViewModel() {
        checkIfFormIsFilled()
        checkIfPropertyPhotosWereChanged()
        checkIfAddressWereChanged()
        checkIfPropertyWereChanged()
        getUpdateFormViewModel.updateLocationsOfInterest(propertyId)
        finish()
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
        if (!available && saleDateLong <= 0)  {
            form_error_sale_date.visibility = View.VISIBLE
        } else {
            form_error_sale_date.visibility = View.GONE
        }
    }

    private fun checkIfPropertyPhotosWereChanged() {
        if (listFormPhotoAndWording.isNotEmpty()) {
            val propertyPhotosToDelete = mutableListOf<FormPhotoAndWording>()
            propertyPhotosToDelete.addAll(setUpdateFormViewModel.entryListFormPhotoAndWording!!.asIterable())
            propertyPhotosToDelete.removeAll(listFormPhotoAndWording)
            if (propertyPhotosToDelete.size > 0) {
                getUpdateFormViewModel.deleteCompositionPropertyAndPropertyPhoto(propertyPhotosToDelete, propertyId, applicationContext)
            }

            val propertyPhotosToInsert = mutableListOf<FormPhotoAndWording>()
            propertyPhotosToInsert.addAll(listFormPhotoAndWording)
            propertyPhotosToInsert.removeAll(setUpdateFormViewModel.entryListFormPhotoAndWording!!.asIterable())
            if (propertyPhotosToInsert.size > 0) {
                getUpdateFormViewModel.insertPropertyPhotos(propertyPhotosToInsert, propertyId, setUpdateFormViewModel.entryListFormPhotoAndWording?.last()?.name, applicationContext)
            }

            if (setUpdateFormViewModel.entryListFormPhotoAndWording?.get(0) != listFormPhotoAndWording[0]) {
                getUpdateFormViewModel.updateIllustrationPropertyPhoto(listFormPhotoAndWording[0])
            }
        }
    }
    
    private fun checkIfAddressWereChanged() {
        if (path.isNotEmpty()
                && district.isNotEmpty()
                && city.isNotEmpty()
                && postalCode.isNotEmpty()
                && country.isNotEmpty()) {
            if (path != setUpdateFormViewModel.entryPropertyModelProcessed?.path
                    || complement != setUpdateFormViewModel.entryPropertyModelProcessed?.complement
                    || district != setUpdateFormViewModel.entryPropertyModelProcessed?.district
                    || city != setUpdateFormViewModel.entryPropertyModelProcessed?.city
                    || postalCode != setUpdateFormViewModel.entryPropertyModelProcessed?.postalCode
                    || country != setUpdateFormViewModel.entryPropertyModelProcessed?.country) {
                getUpdateFormViewModel.updateAddress(getNewAddress())
            }
        }
    }

    private fun checkIfPropertyWereChanged() {
        if (type.isNotEmpty()
                && price.isNotEmpty()
                && surface.isNotEmpty()
                && rooms.isNotEmpty()
                && bedrooms.isNotEmpty()
                && bathrooms.isNotEmpty()
                && description.isNotEmpty()
                && entryDate.isNotEmpty()
                && fullNameAgent.isNotEmpty()) {
            if (type != setUpdateFormViewModel.entryPropertyModelProcessed?.type
                    || price != setUpdateFormViewModel.entryPropertyModelProcessed?.price
                    || surface != setUpdateFormViewModel.entryPropertyModelProcessed?.surface
                    || rooms != setUpdateFormViewModel.entryPropertyModelProcessed?.rooms
                    || bedrooms != setUpdateFormViewModel.entryPropertyModelProcessed?.bedrooms
                    || bathrooms != setUpdateFormViewModel.entryPropertyModelProcessed?.bathrooms
                    || description != setUpdateFormViewModel.entryPropertyModelProcessed?.description
                    || available != setUpdateFormViewModel.entryPropertyModelProcessed?.available
                    || entryDate != setUpdateFormViewModel.entryPropertyModelProcessed?.entryDate
                    || fullNameAgent != setUpdateFormViewModel.entryPropertyModelProcessed?.fullNameAgent) {
                getUpdateFormViewModel.updateProperty(getNewProperty())
            }
        }
    }

    private fun getNewAddress() =
            AddressModelRaw(
                    id = setUpdateFormViewModel.entryPropertyModelProcessed!!.addressId,
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
                    addressId = setUpdateFormViewModel.entryPropertyModelProcessed!!.addressId,
                    fullNameAgent = fullNameAgent
            )

}