package com.example.practica16_consultasanidadas.model

class Proveedor : java.io.Serializable{

    var codigoProveedor: String
    var nombreProveedor: String
    var direccion : String
    var telefono : Int
    var provincia : Int

    constructor(codigoProveedor : String, nombreProveedor : String, direccion : String, telefono : Int, provincia : Int){
        this.codigoProveedor = codigoProveedor
        this.nombreProveedor = nombreProveedor
        this.direccion = direccion
        this.telefono = telefono
        this.provincia = provincia
    }
}