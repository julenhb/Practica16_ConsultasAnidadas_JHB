package com.example.practica16_consultasanidadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.practica16_consultasanidadas.controller.SqliteHelper
import com.example.practica16_consultasanidadas.model.*

class Detalles : AppCompatActivity() {

    private lateinit var codigoArt : TextView
    private lateinit var nombreArt : TextView
    private lateinit var pvpArt : TextView
    private lateinit var ivaArt : TextView
    private lateinit var codigoProv : TextView
    private lateinit var nombreProv : TextView
    private lateinit var direcProv : TextView
    private lateinit var tlfProv : TextView
    private lateinit var provinciaProv : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalles)

        codigoArt = findViewById(R.id.codArt)
        nombreArt = findViewById(R.id.nameArt)
        pvpArt = findViewById(R.id.pvpArt)
        ivaArt = findViewById(R.id.ivaArt)
        codigoProv = findViewById(R.id.codProv)
        nombreProv = findViewById(R.id.nameProv)
        direcProv = findViewById(R.id.dir)
        tlfProv = findViewById(R.id.telefono)
        provinciaProv = findViewById(R.id.nameProvincia)


        if (intent.hasExtra("Articulo")) {
            val articulo: Articulo = intent.getSerializableExtra("Articulo") as Articulo
            codigoArt.setText(articulo.codigoArticulo)
            nombreArt.setText(articulo.nombreArticulo)
            pvpArt.setText(articulo.pvp.toString())
            ivaArt.setText(articulo.iva.toString())

            val helper = SqliteHelper(this)
            val cursor = helper.consultaMultiple(articulo.nombreArticulo) //AQUÍ HAGO LA CONSULTA CON INNER JOIN A TRAVÉS DEL ARTÍCULO QUE HE ENVIADO EN LA ACTIVITY
            //CON EL RESULTADO DE LA CONSULTA MÚLTIPE RELLENO CON EL CURSOR DE ABAJO LOS CAMPOS DEL PROVEEDOR

            if(cursor.getCount() >= 1) {
            while (cursor.moveToNext()){
                codigoProv.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.CODIGOPROVEEDOR)))
                nombreProv.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.NOMBREPROVEEDOR)))
                direcProv.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.DIRECCION)))
                tlfProv.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProveedorContract.TELEFONO)).toString())
                provinciaProv.setText(cursor.getString(cursor.getColumnIndexOrThrow(ProvinciaContract.NOMBREPROVINCIA)).toString())
            }
                }

        }

    }
}