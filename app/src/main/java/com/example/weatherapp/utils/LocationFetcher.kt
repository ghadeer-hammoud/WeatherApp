package com.example.weatherapp.utils

import android.content.Context
import android.location.Location
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

open class LocationFetcher {

    interface LocationCallback{
        fun onLocationAvailable(location: Location)
    }


    fun getCurrentLocation(context: Context, locationCallback: LocationCallback){


        val fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

        try {
            fusedLocationProviderClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if(location != null)
                        locationCallback.onLocationAvailable(location!!)
                    else
                        Log.d("LocationFetcher", "getCurrentLocation: NULL")
                }
        }
        catch (e: SecurityException){

        }
        
    }

}