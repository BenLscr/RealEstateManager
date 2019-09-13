package com.openclassrooms.realestatemanager.form.searchForm

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.sqlite.db.SimpleSQLiteQuery
import com.openclassrooms.realestatemanager.Utils
import com.openclassrooms.realestatemanager.form.searchForm.models.SearchFormModelRaw
import com.openclassrooms.realestatemanager.models.LocationOfInterest
import com.openclassrooms.realestatemanager.repositories.PropertyDataRepository

class SearchFormViewModel(private val propertyDataSource: PropertyDataRepository): ViewModel() {

    fun searchPropertiesId(searchFormModelRaw: SearchFormModelRaw): LiveData<List<Int>> =
            propertyDataSource.customSearchPropertiesId(buildPropertyRequest(searchFormModelRaw))

    private fun buildPropertyRequest(searchFormModelRaw: SearchFormModelRaw): SimpleSQLiteQuery {
        val queryList = mutableListOf<String>()
        val valueList = ArrayList<Any>()
        val questionMarkList = ArrayList<String>()
        with(searchFormModelRaw) {
            if (minPrice.isNotEmpty() && maxPrice.isEmpty()) {
                queryList.add("price >= ?")
                valueList.add(minPrice)
            }
            if (minPrice.isEmpty() && maxPrice.isNotEmpty()) {
                queryList.add("price <= ?")
                valueList.add(maxPrice)
            }
            if (minPrice.isNotEmpty() && maxPrice.isNotEmpty()) {
                queryList.add("price >= ? AND price <= ?")
                valueList.add(minPrice)
                valueList.add(maxPrice)
            }
            if (type.isNotEmpty()) {
                queryList.add("type = ?")
                valueList.add(Utils.fromStringToType(type).ordinal.toString())
            }
            if (minSurface.isNotEmpty() && maxSurface.isEmpty()) {
                queryList.add("surface >= ?")
                valueList.add(minSurface)
            }
            if (minSurface.isEmpty() && maxSurface.isNotEmpty()) {
                queryList.add("surface <= ?")
                valueList.add(maxSurface)
            }
            if (minSurface.isNotEmpty() && maxSurface.isNotEmpty()) {
                queryList.add("surface >= ? AND surface <= ?")
                valueList.add(minSurface)
                valueList.add(maxSurface)
            }
            if (rooms.isNotEmpty()) {
                queryList.add("rooms >= ?")
                valueList.add(rooms)
            }
            if (bathrooms.isNotEmpty()) {
                queryList.add("bathrooms >= ?")
                valueList.add(bathrooms)
            }
            if (bedrooms.isNotEmpty()) {
                queryList.add("bedrooms >= ?")
                valueList.add(bedrooms)
            }
            if (availability != null && dateLong.toInt() == 0) {
                queryList.add("available = ?")
                valueList.add(if (availability == true) "1" else "0")
            }
            if (availability == null && dateLong > 0 ) {
                queryList.add("entryDate >= ?")
                valueList.add(dateLong.toString())
            }
            if (availability != null && dateLong > 0) {
                queryList.add("available = ?")
                if (availability == true) {
                    valueList.add("1")
                    queryList.add("entryDate >= ?")
                } else {
                    valueList.add("0")
                    queryList.add("saleDate >= ?")
                }
                valueList.add(dateLong.toString())
            }
            if (district.isNotEmpty()) {
                queryList.add("Address.district = ?")
                valueList.add(Utils.fromStringToDistrict(district).ordinal.toString())
            }
            if (city.isNotEmpty()) {
                queryList.add("Address.city = ?")
                valueList.add(Utils.fromStringToCity(city).ordinal.toString())
            }
            if (postalCode.isNotEmpty()) {
                queryList.add("Address.postalCode = ?")
                valueList.add(postalCode)
            }
            if (country.isNotEmpty()) {
                queryList.add("Address.country = ?")
                valueList.add(Utils.fromStringToCountry(country).ordinal.toString())
            }
            if (school || commerces || park || subways || train) {
                if (school) {
                    questionMarkList.add("?")
                    valueList.add(LocationOfInterest.SCHOOL.ordinal.toString())
                }
                if (commerces) {
                    questionMarkList.add("?")
                    valueList.add(LocationOfInterest.COMMERCES.ordinal.toString())
                }
                if (park) {
                    questionMarkList.add("?")
                    valueList.add(LocationOfInterest.PARK.ordinal.toString())
                }
                if (subways) {
                    questionMarkList.add("?")
                    valueList.add(LocationOfInterest.SUBWAYS.ordinal.toString())
                }
                if (train) {
                    questionMarkList.add("?")
                    valueList.add(LocationOfInterest.TRAIN.ordinal.toString())
                }
                val questionMarkJoin: String = TextUtils.join(", ", questionMarkList)
                queryList.add("Property.id IN ( SELECT CompositionPropertyAndLocationOfInterest.propertyId FROM CompositionPropertyAndLocationOfInterest WHERE locationOfInterestId IN ($questionMarkJoin) )")
            }
        }
        val queryJoin: String = TextUtils.join(" AND ", queryList)
        return SimpleSQLiteQuery("SELECT id FROM Property INNER JOIN Address ON Property.addressId = Address.address_id INNER JOIN Agent ON Property.agentId = Agent.agent_id WHERE $queryJoin", valueList.toArray())
    }

}