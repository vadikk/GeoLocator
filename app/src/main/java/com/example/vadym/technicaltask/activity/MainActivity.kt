package com.example.vadym.technicaltask.activity

import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.databinding.DataBindingUtil
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import com.example.vadym.technicaltask.R
import com.example.vadym.technicaltask.dagger.App
import com.example.vadym.technicaltask.databinding.ActivityMainBinding
import com.example.vadym.technicaltask.mvvm.Coordinate
import com.example.vadym.technicaltask.mvvm.ViewModel
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import javax.inject.Inject


class MainActivity : AppCompatActivity(),
        OnMapReadyCallback {

    @Inject
    lateinit var viewModel: ViewModel

    lateinit var fusedLocationProvider: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback

    private val REQUEST_PERMISSION_CODE = 34
    private val UPDATE_INTERVAL = (10 * 1000).toLong()  /* 10 secs */
    private val FASTEST_INTERVAL = (2 * 1000).toLong() /* 2 sec */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.appComponent.inject(this)

        val mainActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main) as ActivityMainBinding
        mainActivityBinding.viewModel = viewModel

        startLocationUpdates()

        val mapFragment = SupportMapFragment.newInstance()

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.map, mapFragment)
        fragmentTransaction.commit()

        mapFragment.getMapAsync(this)
    }


    @SuppressLint("MissingPermission")
    override fun onResume() {
        super.onResume()
        fusedLocationProvider.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper())


    }

    override fun onPause() {
        super.onPause()
        fusedLocationProvider.removeLocationUpdates(locationCallback)
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this)

        // Create the location request to start receiving updates
        locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val locationSettingsRequest = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
                .build()

        // Check whether location settings are satisfied
        val settingsClient = LocationServices.getSettingsClient(this)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                if (!checkPermission()) {
                    requestPermission()
                } else {
                    onLocationChanged(p0.getLastLocation())
                }
            }
        }

    }

    fun onLocationChanged(location: Location) {

        val coordinate = Coordinate("Я тут", (location.latitude).toFloat(), (location.longitude).toFloat())
        viewModel.addToList(coordinate)
    }

    fun checkPermission() = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PERMISSION_GRANTED

    fun requestPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_CODE)
    }

    override fun onMapReady(p0: GoogleMap) {
        viewModel.getMap(p0)
        viewModel.downloadData()
    }
}
