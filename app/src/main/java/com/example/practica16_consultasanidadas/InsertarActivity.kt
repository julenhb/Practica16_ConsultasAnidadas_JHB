package com.example.practica16_consultasanidadas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.practica16_consultasanidadas.controller.SqliteHelper
import com.example.practica16_consultasanidadas.model.Articulo
import com.example.practica16_consultasanidadas.model.Proveedor
import com.example.practica16_consultasanidadas.model.ProveedorContract

class InsertarActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var codProducto : EditText
    private lateinit var nombreProducto : EditText
    private lateinit var spinner: Spinner
    private lateinit var PVP : EditText
    private lateinit var radioGroup: RadioGroup
    private lateinit var radio1 : RadioButton
    private lateinit var radio2 : RadioButton
    private lateinit var radio3 : RadioButton
    private lateinit var insertar : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertar)

        codProducto = findViewById(R.id.codProducto)
        nombreProducto = findViewById(R.id.nombreProducto)
        spinner = findViewById(R.id.proveedores)
        PVP = findViewById(R.id.pvp)
        radioGroup = findViewById(R.id.radiogroup)
        radio1 = findViewById(R.id.b4)
        radio2 = findViewById(R.id.b10)
        radio3 = findViewById(R.id.b21)
        insertar = findViewById(R.id.insert)

        insertar.setOnClickListener(this)
        radioGroup.setOnClickListener(this)
        radio1.setOnClickListener(this)
        radio2.setOnClickListener(this)
        radio3.setOnClickListener(this)
        spinner.onItemSelectedListener

        val arrayNombres = ArrayList<String>()
        val helper = SqliteHelper(this)
        val cursorNombres = helper.NombreProveedores()
        while (cursorNombres.moveToNext()){
            arrayNombres.add(
                cursorNombres.getString(cursorNombres.getColumnIndexOrThrow(ProveedorContract.NOMBREPROVEEDOR))
            )
        }

        val arrayProveedor = ArrayList<Proveedor>()
        val cursorProveedor = helper.Proveedores()
        while (cursorProveedor.moveToNext()){
            arrayProveedor.add(
                Proveedor(
                    cursorProveedor.getString(cursorProveedor.getColumnIndexOrThrow(ProveedorContract.CODIGOPROVEEDOR)),
                    cursorProveedor.getString(cursorProveedor.getColumnIndexOrThrow(ProveedorContract.NOMBREPROVEEDOR)),
                    cursorProveedor.getString(cursorProveedor.getColumnIndexOrThrow(ProveedorContract.DIRECCION)),
                    cursorProveedor.getInt(cursorProveedor.getColumnIndexOrThrow(ProveedorContract.TELEFONO)),
                    cursorProveedor.getInt(cursorProveedor.getColumnIndexOrThrow(ProveedorContract.PROVINCIA))
                )
            )
        }

        val adaptadorProveedor = ArrayAdapter(this,
            android.R.layout.simple_list_item_1, arrayNombres)

        spinner.adapter = adaptadorProveedor

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            R.id.insert -> {
                val articulo = Articulo(codProducto.text.toString(), nombreProducto.text.toString(), PVP.text.toString().toDouble(), radioGroup.checkedRadioButtonId.toString().toInt(), spinner.selectedItem.toString())
                val helper = SqliteHelper(this)
                if(helper.insertar(articulo)!= (-1).toLong()) {
                    Toast.makeText(this, "Insertado", Toast.LENGTH_SHORT).show()
                    finish()
                }

            }
        }
    }
}