package com.openclassrooms.realestatemanager.propertyList

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto
import com.openclassrooms.realestatemanager.models.District
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.models.Type
import com.openclassrooms.realestatemanager.propertyList.models.IllustrationModelProcessed
import com.openclassrooms.realestatemanager.propertyList.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository
import java.text.NumberFormat

class PropertyListViewModel (
        propertyDataSource: PropertyDataRepository,
        private val compositionPropertyAndPropertyPhotoDataRepository: CompositionPropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private var _properties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getProperties()) { it.map { property -> buildPropertyModelProcessed(property) } }
    val properties: LiveData<List<PropertyModelProcessed>> = _properties

    fun getPropertyIllustration(propertyId: Int, path: String?, context: Context): LiveData<IllustrationModelProcessed> =
            Transformations.map(compositionPropertyAndPropertyPhotoDataRepository.getPropertyIllustration(propertyId, true)) { getIllustrationModelProcessed(it, path, context) }


    //---FACTORY---\\
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
                else -> "District unknown"
            }

    private fun getPriceIntoStringForUi(price: Long) = "$" + NumberFormat.getIntegerInstance().format(price)

    private fun getIllustrationModelProcessed(composition: CompositionPropertyAndPropertyPhoto, path: String?, context: Context) =
            IllustrationModelProcessed(
                    propertyId = composition.propertyId,
                    illustration = Utils.getInternalBitmap(path, composition.propertyPhoto?.name, context)
            )

}