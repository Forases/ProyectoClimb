package com.lorenzohamaoka.proyectoclimb.ui.map

import android.app.ActionBar
import android.content.pm.PackageManager
import android.location.Location
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.lorenzohamaoka.proyectoclimb.MainActivity

import com.lorenzohamaoka.proyectoclimb.R
import com.lorenzohamaoka.proyectoclimb.ui.filtro.FiltroViewModel

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        var referenciaZona: String? = null
        var latitudZona: String? = null
        var longitudZona: String? = null
    }
    private var mapView: MapView? = null
    private var gmap: GoogleMap? = null

    lateinit var toolbar: ActionBar
    private lateinit var currentMarker: String
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private val MAP_VIEW_BUNDLE_KEY = "MapViewBundleKey"

    private lateinit var filtroViewModel: FiltroViewModel

    val position = LatLng(-33.920455, 18.466941)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        filtroViewModel =
            ViewModelProviders.of(this).get(FiltroViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY)
        }

        mapView = root.findViewById(R.id.mapView)
        mapView!!.onCreate(mapViewBundle)
        mapView!!.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity!!)


        return root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        var mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY)
        if (mapViewBundle == null) {
            mapViewBundle = Bundle()
            outState.putBundle(MAP_VIEW_BUNDLE_KEY, mapViewBundle)
        }
        mapView?.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView?.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView?.onStop()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        gmap = googleMap
        // Habilitamos los botones del zoom.
        gmap!!.uiSettings.isZoomControlsEnabled = true
        // Habilitamos la brújula, solo aparecerá cuando giremos el mapa.
        gmap!!.uiSettings.isCompassEnabled = true
        setMapMarkers()
        configMap()
        gmap!!.setOnMarkerClickListener(this)
    }

    /**
     * Comprobamos los permisos de ubicación y ubicamos el mapa
     * según nuestra ubicación.
     */
    private fun configMap() {
        if (ActivityCompat.checkSelfPermission(
                activity!!,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        // Añadimos la marca en la ubicación que nos encontramos.
        gmap!!.isMyLocationEnabled = true
        fusedLocationClient.lastLocation.addOnSuccessListener(activity!!) { location ->
            // Vamos a la última ubicación conocida,
            // en algunos casos puede ser null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude,
                    location.longitude)
                gmap!!.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(currentLatLng, 10f)
                )
            }else{
                val currentLatLng = LatLng(38.4041828,-0.529396)
                gmap!!.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(currentLatLng, 9f)
                )

            }
        }
    }

    /**
     * Método para añadir la marca en la localización indicada.
     */
    private fun placeMarkerOnMap(location: LatLng, name: String) {
        // Creamos un objeto MarkerOptions para configurar la marca.
        val markerOptions = MarkerOptions().position(location)
        markerOptions.title(name)
        // Añadimos la marca al mapa.
        gmap!!.addMarker(markerOptions)
    }

    private fun setMapMarkers(){
        val db: FirebaseFirestore = FirebaseFirestore.getInstance()
        val zonasCollection: CollectionReference = db.collection("zonas")

        // Obtener todos los documentos de una colección (sin escucha).
        zonasCollection.get().apply {
            addOnSuccessListener {
                for (document in it) {
                    Log.d("DOC", "${document.id} => ${document.data}")
                    var latLng = LatLng(document["latitud"].toString().toDouble(), document["longitud"].toString().toDouble())
                    placeMarkerOnMap(latLng, document["nombre"].toString())
                }
            }
            addOnFailureListener { exception ->
                Log.d(
                    "DOC",
                    "Error durante la recogida de documentos: ",
                    exception
                )
            }
        }
    }

    /**
     * Se dispara cuando se hace click sobre una marca en el mapa,
     * se implementa al heredar de GoogleMap.OnMarkerClickListener.
     */
    override fun onMarkerClick(p0: Marker?): Boolean {
        Log.d("onMarkerClick", "Click sobre una marca")

        currentMarker = p0!!.title.toString()
        getReferenciaZona()
        getCoordenadasZona()
//        val myIntent = Intent(this, SectoresActivity::class.java).apply{
//            putExtra(NOMBRE_ZONA, currentMarker)
//        }
//        // Lanzamos la activity
//        startActivity(myIntent)
        return false
    }



    private fun getReferenciaZona(){
        for(zona in MainActivity.zonasArray){
            if(zona.nombreZona == currentMarker)
                referenciaZona = zona.referencia
        }
    }

    private fun getCoordenadasZona(){
        for(zona in MainActivity.zonasArray){
            if(zona.nombreZona == currentMarker)
                latitudZona = zona.latitud.toString()
            longitudZona = zona.longitud.toString()
        }
    }
}
