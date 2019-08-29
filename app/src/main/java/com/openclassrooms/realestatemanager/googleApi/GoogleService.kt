package com.openclassrooms.realestatemanager.googleApi

import com.openclassrooms.realestatemanager.googleApi.models.geocoding.GeocodingResponse
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleService {

    @GET("geocode/json")
    fun getGeocoding(@Query("address") address: String,
                     @Query("key") apiKey: String): Observable<GeocodingResponse>

    companion object {

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

    }

}