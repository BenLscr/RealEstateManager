package com.openclassrooms.realestatemanager.propertyList

import android.content.Context
import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.models.CompositionPropertyAndPropertyPhoto
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.propertyList.models.IllustrationModelProcessed
import com.openclassrooms.realestatemanager.propertyList.models.PropertyModelProcessed
import com.openclassrooms.realestatemanager.repositories.CompositionPropertyAndPropertyPhotoDataRepository
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class PropertyListViewModel (
        private val propertyDataSource: PropertyDataRepository,
        private val compositionPropertyAndPropertyPhotoDataRepository: CompositionPropertyAndPropertyPhotoDataRepository) : ViewModel() {

    private var _allProperties: LiveData<List<PropertyModelProcessed>> = Transformations.map(propertyDataSource.getAllProperties()) { it.map { property -> buildPropertyModelProcessed(property) } }
    val allProperties: LiveData<List<PropertyModelProcessed>> = _allProperties

    fun getProperties(propertiesId: List<Int>): LiveData<List<PropertyModelProcessed>> =
            Transformations.map(propertyDataSource.getProperties(buildRequest(propertiesId))) { it.map { property -> buildPropertyModelProcessed(property) } }

    fun getPropertyIllustration(propertyId: Int, context: Context): LiveData<IllustrationModelProcessed> =
            Transformations.map(compositionPropertyAndPropertyPhotoDataRepository.getPropertyIllustration(propertyId, true)) { getIllustrationModelProcessed(it, context) }

    //---FACTORY---\\
    private fun buildPropertyModelProcessed(property: Property) =
            PropertyModelProcessed(
                    propertyId = property.id,
                    path= property.address?.path,
                    type = Utils.fromTypeToString(property.type),
                    district = Utils.fromDistrictToString(property.address?.district),
                    price = Utils.fromPriceToString(property.price)
            )

    private fun buildRequest(propertiesId: List<Int>): SimpleSQLiteQuery {
        val valueList = ArrayList<Any>()
        val questionMarkList = ArrayList<String>()
        for (propertyId in propertiesId) {
            valueList.add(propertyId)
            questionMarkList.add("?")
        }
        val questionMarkJoin = TextUtils.join(", ", questionMarkList)
        return SimpleSQLiteQuery("SELECT * FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE Property.id IN ($questionMarkJoin)", valueList.toArray())
    }

    private fun getIllustrationModelProcessed(composition: CompositionPropertyAndPropertyPhoto, context: Context) =
            IllustrationModelProcessed(
                    propertyId = composition.propertyId,
                    illustration = Utils.getInternalBitmap(composition.propertyId.toString(), composition.propertyPhoto?.name, context)
            )

}