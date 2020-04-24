package edu.uoc.android

import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import edu.uoc.android.models.Museums
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Activity_Mapa : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    companion object{
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    private lateinit var mMap: GoogleMap
    private lateinit var mMap1: GoogleMap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        var x="dd"

        val museos=ArrayList<MuseoClass>()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://do.diba.cat")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var museumService: MuseumService = retrofit.create(MuseumService::class.java)


        var contador = 0

        museumService.museums("1","10").enqueue(object : Callback<Museums> {
            override fun onResponse(call: Call<Museums>, response: Response<Museums>){
                if (response.code() == 200) {

                    val museums = response.body()!!
                    // Adapter <<− museums // elements
                    val elemento = museums.getElements()
                    //Log.d("Etiqueta", elemento.size)
                    for (oneElement in elemento) {
                        //Log.d("Etiqueta", oneElement.adrecaNom+" "+oneElement.localitzacio)
                        x=oneElement.localitzacio

                        var delimiter = ","

                        val parts = x.split(delimiter)

                        // Add a marker in Sydney and move the camera
                        var z = parts[0].toDouble()
                        var y = parts[1].toDouble()

                        //Log.d("Etiqueta", x)
                        museos.add(MuseoClass(oneElement.adrecaNom,oneElement.imatge[0], z, y))
                        contador++
                    }

                    mMap1 = googleMap
                    contador=0
                    for (oneElement in museos) {
                        //Log.d("Etiqueta", museos.get())

                        mMap1.addMarker(MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.museo))
                            .position(LatLng(museos.get(contador).lat,museos.get(contador).lng)) //museos[0].getlat(),
                            .title(museos.get(contador).name))
                        contador++
                    }


                    val sydney = LatLng(41.3033271, 2.0102172)

                   /* mMap.addMarker(MarkerOptions()
                        .position(sydney)
                        .title("Marker in Sydney"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,13.0f))*/
                    //val adapter=AdapterMuseo(museos)

                    //recyclerView.adapter=adapter
                }
            }

            override fun onFailure(call: Call<Museums>, t: Throwable) { // Log.d(TAG, ””xxxxx”)
                Log.d("Etiqueta","Fallo en llamada",t)
            }
        })
        setUpMap()

    }

    private fun setUpMap(){
        if(ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }


        mMap.isMyLocationEnabled = true

        mMap.mapType=GoogleMap.MAP_TYPE_TERRAIN

        fusedLocationClient.lastLocation.addOnSuccessListener(this){ location ->
            if (location != null){
                lastLocation = location
                val currentLatLong = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLong, 13f))
            }

        }

    }
}
