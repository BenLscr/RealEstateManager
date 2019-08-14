package com.openclassrooms.realestatemanager.propertyList

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto
import com.openclassrooms.realestatemanager.models.District
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.models.Type
import com.openclassrooms.realestatemanager.propertyList.models.IllustrationModelProcessed
import com.openclassrooms.realestatemanager.propertyList.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.repositories.AddressDataRepository
import com.openclassrooms.realestatemanager.repositories.AgentDataRepository
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.text.NumberFormat
import java.util.concurrent.Executor

class PropertyListViewModel (
        propertyDataSource: PropertyDataRepository,
        compositionPropertyAndPropertyPhotoDataRepository: CompositionPropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private var _properties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildPropertyModelProcessed(property) } }
    val properties: LiveData<List<PropertyModelProcessed>> = _properties

    private var _illustrationsPropertiesPhotos: LiveData<List<IllustrationModelProcessed>> =
            Transformations.map(compositionPropertyAndPropertyPhotoDataRepository.getIllustrationPropertyPhotos(true)) {
                it.map { compositionPropertyAndPropertyPhoto -> buildUiModelForIllustration(compositionPropertyAndPropertyPhoto) }
            }

    val illustrationsPropertiesPhotos: LiveData<List<IllustrationModelProcessed>> = _illustrationsPropertiesPhotos

    private fun buildPropertyModelProcessed(property: Property) =
            PropertyModelProcessed(
                    propertyId = property.id,
                    path= property.address?.path,
                    type = getTypeIntoStringForUi(property.type),
                    district = getDistrictIntoStringForUi(property.address?.district),
                    price = getPriceIntoStringForUi(property.price)
            )

    private fun getTypeIntoStringForUi(type: Type) =
            when(type) {
                Type.PENTHOUSE -> "Penthouse"
                Type.MANSION -> "Mansion"
                Type.FLAT -> "Flat"
                Type.DUPLEX -> "Duplex"
                Type.HOUSE -> "House"
                Type.LOFT -> "Loft"
                Type.TOWNHOUSE -> "Townhouse"
                Type.CONDO -> "Condo"
            }

    private fun getDistrictIntoStringForUi(district: District?) =
            when(district) {
                District.MANHATTAN -> "Manhattan"
                District.BROOKLYN -> "Brooklyn"
                District.STATEN_ISLAND -> "Staten Island"
                District.QUEENS -> "Queens"
                District.BRONX -> "Bronx"
                else -> null
            }

    private fun getPriceIntoStringForUi(price: Long) = "$" + NumberFormat.getIntegerInstance().format(price)

    private fun buildUiModelForIllustration(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto) =
            IllustrationModelProcessed(
                    propertyId = compositionPropertyAndPropertyPhoto.propertyId,
                    photoName = compositionPropertyAndPropertyPhoto.propertyPhoto?.name
            )

}