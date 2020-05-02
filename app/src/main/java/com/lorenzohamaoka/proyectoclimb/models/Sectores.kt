package com.lorenzohamaoka.proyectoclimb.models

class Sectores {

    var referenciaSector: String? = null
    var nombreSector: String? = null
    var referenciaImagen: String? = null
    var vias: MutableList<Vias>? = null
    var arrayGradosPorVia = arrayOf(0, 0, 0, 0, 0, 0)

    constructor(
        referenciaSector: String?,
        nombreSector: String?,
        referenciaImagen: String?,
        vias: MutableList<Vias>?
    ) {
        this.referenciaSector = referenciaSector
        this.nombreSector = nombreSector
        this.referenciaImagen = referenciaImagen
        this.vias = vias
    }
}