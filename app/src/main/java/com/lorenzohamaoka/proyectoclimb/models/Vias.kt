package com.lorenzohamaoka.proyectoclimb.models

class Vias {
    var referenciaVia: String? = null
    var nombreVia: String? = null
    var gradoVia: String? = null
    var longitud: String? = null
    var tipo: String? = null
    var numero: Int? = null

    constructor(
        referenciaVia: String?,
        nombreVia: String?,
        gradoVia: String?,
        longitud: String?,
        tipo: String?,
        numero: Int?
    ) {
        this.referenciaVia = referenciaVia
        this.nombreVia = nombreVia
        this.gradoVia = gradoVia
        this.longitud = longitud
        this.tipo = tipo
        this.numero = numero
    }
}