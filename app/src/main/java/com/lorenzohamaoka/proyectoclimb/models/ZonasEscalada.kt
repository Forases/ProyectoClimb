package dam.lorenzohamaoka.climbingapp.models

import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore

class ZonasEscalada {
    var referenciaPortada: String? = null
    var referencia: String? = null
    var nombreZona: String? = null
    var latitud: Double? = null
    var longitud: Double? = null
    var localidad: String? = null
    var imagenZona: String? = null
    var orientacion: String? = null
    var restricciones: String? = null
    var tipoRoca: String? = null
    var tipoEscalada: String? = null
    var sectores : MutableList<Sectores>? = null
    var distancia : Int? = null

    constructor(
        referenciaPortada: String?,
        referencia: String?,
        nombreZona: String?,
        latitud: Double?,
        longitud: Double?,
        localidad: String?,
        imagenZona: String?,
        orientacion: String?,
        restricciones: String?,
        tipoRoca: String?,
        tipoEscalada: String?,
        sectores: MutableList<Sectores>?
    ) {
        this.referenciaPortada = referenciaPortada
        this.referencia = referencia
        this.nombreZona = nombreZona
        this.latitud = latitud
        this.longitud = longitud
        this.localidad = localidad
        this.imagenZona = imagenZona
        this.orientacion = orientacion
        this.restricciones = restricciones
        this.tipoRoca = tipoRoca
        this.tipoEscalada = tipoEscalada
        this.sectores = sectores
    }
}