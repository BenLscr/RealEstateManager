package com.openclassrooms.realestatemanager.googleApi

import com.openclassrooms.realestatemanager.googleApi.models.geocoding.GeocodingResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class GoogleStreams {

    companion object {

        fun streamFetchGeocoding(address: String,
                                 apiKey: String): Observable<GeocodingResponse> {
            val googleService = GoogleService.retrofit.create(GoogleService::class.java)
            return googleService.getGeocoding(address, apiKey)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(10, TimeUnit.SECONDS)
        }

    }

}