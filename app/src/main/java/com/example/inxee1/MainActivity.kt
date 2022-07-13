package com.example.inxee1

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.inxee1.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task

class MainActivity : AppCompatActivity() {
    lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    lateinit var binding: ActivityMainBinding

    private var lat:Double = 0.0
    private var long:Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getLocation()

        binding.showOnMaps.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MapsActivity::class.java)
//            val lat = 10.0
//            val long = 20.0
            intent.putExtra("lat",lat)
            intent.putExtra("long",long)
            startActivity(intent)
        })
        binding.showCoordinates.setOnClickListener(View.OnClickListener {
            binding.location.text = "lat: $lat long: $long"
        })
    }

    fun getLocation(){
        if(ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show()
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
            val task: Task<Location> = fusedLocationProviderClient.lastLocation
            task.addOnSuccessListener {
                if(it!=null){
                    Log.d("location", "getLocation: ${it.latitude} and ${it.longitude}")
                    lat = it.latitude
                    long = it.longitude
                }
            }
        }
        else{
            ActivityCompat.requestPermissions(this,
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),23)
        }
    }
}
