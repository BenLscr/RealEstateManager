package com.openclassrooms.realestatemanager.form.updateForm

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.form.updateForm.models.AddressModelRaw
import com.openclassrooms.realestatemanager.form.updateForm.models.LocationsOfInterestModelRaw
import com.openclassrooms.realestatemanager.models.Address
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndLocationOfInterest
import com.openclassrooms.realestatemanager.models.LocationOfInterest
import com.openclassrooms.realestatemanager.repositories.*
import java.util.concurrent.Executor

class SetUpdateFormViewModel(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val agentDataSource: AgentDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository,
        private val propertyPhotoDataSource: PropertyPhotoDataRepository,
        private val compositionPropertyAndPropertyPhotoDataSource: CompositionPropertyAndPropertyPhotoDataRepository,
        private val executor: Executor) : ViewModel() {

    fun updateAddress(addressModelRaw: AddressModelRaw) = executor.execute { addressDataSource.updateAddress(getAddressForDatabase(addressModelRaw)) }

    fun updateLocationsOfInterest(locationsOfInterestModelRaw: LocationsOfInterestModelRaw) {
        with(locationsOfInterestModelRaw) {
            checkBooleanAndInsertOrDeleteIt(school, propertyId, LocationOfInterest.SCHOOL.ordinal)
            checkBooleanAndInsertOrDeleteIt(commerces, propertyId, LocationOfInterest.COMMERCES.ordinal)
            checkBooleanAndInsertOrDeleteIt(park, propertyId, LocationOfInterest.PARK.ordinal)
            checkBooleanAndInsertOrDeleteIt(subways, propertyId, LocationOfInterest.SUBWAYS.ordinal)
            checkBooleanAndInsertOrDeleteIt(train, propertyId, LocationOfInterest.TRAIN.ordinal)
        }
    }

    private fun checkBooleanAndInsertOrDeleteIt(boolean: Boolean?, propertyId: Int, locationOfInterestId: Int) {
        if (boolean == true) {
            val compositionPropertyAndLocationOfInterest = CompositionPropertyAndLocationOfInterest(propertyId, locationOfInterestId)
            executor.execute { compositionPropertyAndLocationOfInterestDataSource.insertLocationOfInterest(compositionPropertyAndLocationOfInterest) }

        } else if (boolean == false) {
            executor.execute { compositionPropertyAndLocationOfInterestDataSource.deleteLocationOfInterest(propertyId, locationOfInterestId) }
        }
    }

    //---FACTORY---\\

    private fun getAddressForDatabase(addressModelRaw: AddressModelRaw) =
            with(addressModelRaw) {
                Address(
                        id = id,
                        path = path,
                        complement = Utils.returnComplementOrNull(complement),
                        district = Utils.getDistrictForDatabaseFromString(district),
                        city = Utils.getCityForDatabaseFromString(city),
                        postalCode = postalCode,
                        country = Utils.getCountryForDatabaseFromString(country)
                )
            }

}