package dam.lorenzohamaoka.climbingapp.models

class Sectores {

    var referenciaSector: String? = null
    var nombreSector: String? = null
    var referenciaImagen: String? = null

    constructor(referenciaSector: String?, nombreSector: String?, referenciaImagen: String?) {
        this.referenciaSector = referenciaSector
        this.nombreSector = nombreSector
        this.referenciaImagen = referenciaImagen
    }
}