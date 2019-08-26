package com.openclassrooms.realestatemanager.propertyDetail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndLocationOfInterest
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.propertyDetail.models.LocationsOfInterestModelProcessed
import com.openclassrooms.realestatemanager.propertyDetail.models.PhotoModelProcessed
import com.openclassrooms.realestatemanager.propertyDetail.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndLocationOfInterestDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class PropertyDetailViewModel(
        private val propertyDataSource: PropertyDataRepository,
        private val compositionPropertyAndPropertyPhotoDataSource: CompositionPropertyAndPropertyPhotoDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository) : ViewModel() {

    fun getProperty(propertyId: Int): LiveData<PropertyModelProcessed> =
            Transformations.map(propertyDataSource.getProperty(propertyId)) { buildPropertyModelProcessed(it) }

    fun getPropertyPhotos(propertyId: Int, context: Context): LiveData<List<PhotoModelProcessed>> =
            Transformations.map(compositionPropertyAndPropertyPhotoDataSource.getPropertyPhotos(propertyId)) { it.map { propertyPhoto -> buildPhotoModelProcessed(propertyPhoto, context) } }

    fun getLocationsOfInterest(propertyId: Int): LiveData<LocationsOfInterestModelProcessed> =
            Transformations.map(compositionPropertyAndLocationOfInterestDataSource.getLocationsOfInterest(propertyId)) { buildLocationOfInterestModelProcessed(it) }

    //---FACTORY---\\
    private fun buildPropertyModelProcessed(property: Property) =
            PropertyModelProcessed(
                    description = property.description,
                    surface = Utils.fromSurfaceToString(property.surface),
                    rooms = property.rooms.toString(),
                    bathrooms = property.bathrooms.toString(),
                    bedrooms = property.bedrooms.toString(),
                    available = property.available,
                    path = property.address?.path,
                    complement = property.address?.complement,
                    city = Utils.fromCityToString(property.address?.city),
                    postalCode = property.address?.postalCode,
                    country = Utils.fromCountryToString(property.address?.country),
                    agentFullName = Utils.fromAgentToString(property.agent?.firstName, property.agent?.name),
                    entryDate = Utils.fromEntryDateToString(property.entryDate),
                    saleDate = Utils.fromSaleDateToString(property.saleDate)
            )

    private fun buildPhotoModelProcessed(propertyPhoto: CompositionPropertyAndPropertyPhoto, context: Context?) =
            PhotoModelProcessed(
                    photo = Utils.getInternalBitmap(propertyPhoto.propertyId.toString(), propertyPhoto.propertyPhoto?.name, context),
                    wording = Utils.fromWordingToString(propertyPhoto.propertyPhoto?.wording)
            )

    private fun buildLocationOfInterestModelProcessed(composition: List<CompositionPropertyAndLocationOfInterest>): LocationsOfInterestModelProcessed {
        var school = false
        var commerces= false
        var park = false
        var subways = false
        var train = false
        for(locationOfInterest in composition) {
            when(locationOfInterest.locationOfInterestId) {
                0 -> school = true
                1 -> commerces = true
                2 -> park = true
                3 -> subways = true
                4 -> train = true
            }
        }
        return LocationsOfInterestModelProcessed(
                school = school,
                commerces = commerces,
                park = park,
                subways = subways,
                train = train
        )
    }

}
