package com.openclassrooms.realestatemanager.propertyList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.propertyList.models.IllustrationModelProcessed
import com.openclassrooms.realestatemanager.propertyList.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

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
                    type = Utils.fromTypeToString(property.type),
                    district = Utils.fromDistrictToString(property.address?.district),
                    price = Utils.fromPriceToString(property.price)
            )

    private fun getIllustrationModelProcessed(composition: CompositionPropertyAndPropertyPhoto, path: String?, context: Context) =
            IllustrationModelProcessed(
                    propertyId = composition.propertyId,
                    illustration = Utils.getInternalBitmap(path, composition.propertyPhoto?.name, context)
            )

}