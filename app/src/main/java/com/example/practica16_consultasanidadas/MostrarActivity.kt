package com.example.practica16_consultasanidadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.practica16_consultasanidadas.R
import com.example.practica16_consultasanidadas.controller.SqliteHelper
import com.example.practica16_consultasanidadas.model.Articulo
import com.example.practica16_consultasanidadas.model.ArticuloContract
import com.example.practica16_consultasanidadas.model.Proveedor
import com.example.practica16_consultasanidadas.model.ProveedorContract

class MostrarActivity : AppCompatActivity(), AdapterView.OnItemClickListener {

    private lateinit var listView: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mostrar)

        listView = findViewById(R.id.listView)

        val helper = SqliteHelper(this)
        val nombres = helper.leerArticulos()

        val arrayNombres = ArrayList<String>()
        val cursorNombres = helper.leerArticulos()
        while (cursorNombres.moveToNext()) {
            arrayNombres.add(
                cursorNombres.getString(cursorNombres.getColumnIndexOrThrow(ArticuloContract.NOMBREARTICULO))
            )
        }

        val adaptadorArticulo = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1, arrayNombres
        )
        listView.adapter = adaptadorArticulo
        listView.setOnItemClickListener(this)

    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        val helper = SqliteHelper(this)
        val itemSelected = parent?.getItemAtPosition(position)
        val cursor = helper.encontrarArticulo(itemSelected.toString())
        val articulos = ArrayList<Articulo>()


        while (cursor.moveToNext()) {
            articulos.add(
                Articulo(
                    cursor.getString(cursor.getColumnIndexOrThrow(ArticuloContract.CODIGOARTICULO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ArticuloContract.NOMBREARTICULO)),
                    cursor.getDouble(cursor.getColumnIndexOrThrow(ArticuloContract.PVP)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(ArticuloContract.IVA)),
                    cursor.getString(cursor.getColumnIndexOrThrow(ArticuloContract.PROVEEDOR)),
                )
            )
        }

            var intent = Intent(this, Detalles::class.java)
            intent.putExtra("Articulo", articulos.get(0))
            startActivity(intent)
            onResume()


        }


}