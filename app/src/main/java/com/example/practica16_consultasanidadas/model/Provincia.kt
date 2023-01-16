package com.example.practica16_consultasanidadas.model

class Provincia : java.io.Serializable{
    var codigoProvincia : Int
    var nombreProvincia : String

    constructor(codigoProvincia : Int, nombreProvincia: String){
        this.codigoProvincia = codigoProvincia
        this.nombreProvincia = nombreProvincia
    }
}