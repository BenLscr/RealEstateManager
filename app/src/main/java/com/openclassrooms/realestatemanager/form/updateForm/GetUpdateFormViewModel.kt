package com.openclassrooms.realestatemanager.form.updateForm

import android.content.Context
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.form.media.models.FormPhotoAndWording
import com.openclassrooms.realestatemanager.form.updateForm.models.AddressModelRaw
import com.openclassrooms.realestatemanager.form.updateForm.models.LocationsOfInterestModelRaw
import com.openclassrooms.realestatemanager.form.updateForm.models.PropertyModelRaw
import com.openclassrooms.realestatemanager.models.*
import com.openclassrooms.realestatemanager.repositories.*
import java.util.concurrent.Executor

class GetUpdateFormViewModel(
        private val propertyDataSource: PropertyDataRepository,
        private val addressDataSource: AddressDataRepository,
        private val compositionPropertyAndLocationOfInterestDataSource: CompositionPropertyAndLocationOfInterestDataRepository,
        private val propertyPhotoDataSource: PropertyPhotoDataRepository,
        private val compositionPropertyAndPropertyPhotoDataSource: CompositionPropertyAndPropertyPhotoDataRepository,
        private val executor: Executor) : ViewModel() {

    fun deleteCompositionPropertyAndPropertyPhoto(propertyPhotos: List<FormPhotoAndWording>, propertyId: Int, context: Context) =
            executor.execute {
                propertyPhotos.forEach { formPhotoAndWording ->
                    if (formPhotoAndWording.id != null) {
                        compositionPropertyAndPropertyPhotoDataSource.deletePropertyPhoto(propertyId, formPhotoAndWording.id)
                    }
                    deletePropertyPhotoOnInternalStorage(propertyId, formPhotoAndWording.name, context)
                    deletePropertyPhoto(formPhotoAndWording.id)
                }
            }

    private fun deletePropertyPhotoOnInternalStorage(propertyId: Int, name: String?, context: Context) {
        Utils.deleteInternalBitmap(propertyId.toString(), name, context)
    }

    private fun deletePropertyPhoto(propertyPhotoId: Int?) =
            executor.execute {
                if (propertyPhotoId != null) {
                    propertyPhotoDataSource.deletePropertyPhoto(propertyPhotoId)
                }
            }

    fun insertPropertyPhotos(propertyPhotos: List<FormPhotoAndWording>, propertyId: Int, lastName: String?,  context: Context) =
            executor.execute {
                propertyPhotos.forEachIndexed { index, formPhotoAndWording ->
                    val lastInt = lastName?.removeSuffix(".jpg")?.toInt()
                    val name = lastInt?.plus(index)?.plus(1).toString()
                    Utils.setInternalBitmap(formPhotoAndWording.photo, propertyId.toString(), name, context)
                    val propertyPhoto = PropertyPhoto(
                            name = name,
                            wording = Utils.fromStringToWording(formPhotoAndWording.wording),
                            isThisTheIllustration = false
                    )
                    val rowIdPropertyPhoto = propertyPhotoDataSource.insertPropertyPhoto(propertyPhoto)
                    buildCompositionPropertyAndPropertyPhoto(propertyId, rowIdPropertyPhoto)
                }
            }


    private fun buildCompositionPropertyAndPropertyPhoto(propertyId: Int, rowIdPropertyPhoto: Long) {
        val compositionPropertyAndPropertyPhoto = CompositionPropertyAndPropertyPhoto(
                propertyId = propertyId,
                propertyPhotoId = rowIdPropertyPhoto.toInt()
        )
        insertCompositionPropertyAndPropertyPhoto(compositionPropertyAndPropertyPhoto)
    }

    fun updateIllustrationPropertyPhoto(formPhotoAndWording: FormPhotoAndWording) =
            executor.execute {
                if (formPhotoAndWording.id != null && formPhotoAndWording.name != null) {
                    val propertyPhoto = PropertyPhoto(
                            id = formPhotoAndWording.id,
                            name = formPhotoAndWording.name,
                            wording = Utils.fromStringToWording(formPhotoAndWording.wording),
                            isThisTheIllustration = true
                    )
                    propertyPhotoDataSource.updatePropertyPhoto(propertyPhoto)
                }
            }

    private fun insertCompositionPropertyAndPropertyPhoto(compositionPropertyAndPropertyPhoto: CompositionPropertyAndPropertyPhoto) =
            executor.execute {
                compositionPropertyAndPropertyPhotoDataSource.insertPropertyPhoto(compositionPropertyAndPropertyPhoto)
            }

    fun updateAddress(addressModelRaw: AddressModelRaw) = executor.execute { addressDataSource.updateAddress(buildAddressForDatabase(addressModelRaw)) }

    fun updateProperty(propertyModelRaw: PropertyModelRaw) = executor.execute { propertyDataSource.updateProperty(buildPropertyForDatabase(propertyModelRaw)) }

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

    private fun buildAddressForDatabase(addressModelRaw: AddressModelRaw) =
            with(addressModelRaw) {
                Address(
                        id = id,
                        path = path,
                        complement = Utils.returnComplementOrNull(complement),
                        district = Utils.fromStringToDistrict(district),
                        city = Utils.fromStringToCity(city),
                        postalCode = postalCode,
                        country = Utils.fromStringToCountry(country)
                )
            }

    private fun buildPropertyForDatabase(propertyModelRaw: PropertyModelRaw) =
            with(propertyModelRaw) {
                Property(
                        id = id,
                        type = Utils.fromStringToType(type),
                        price = price.toLong(),
                        surface = surface.toInt(),
                        rooms = rooms.toInt(),
                        bedrooms = bedrooms.toInt(),
                        bathrooms = bathrooms.toInt(),
                        description = description,
                        addressId = addressId,
                        available = available,
                        entryDate = entryDate,
                        saleDate = if (!available) { saleDate } else { null },
                        agentId = Utils.fromStringToAgentId(fullNameAgent)
                )
            }

}