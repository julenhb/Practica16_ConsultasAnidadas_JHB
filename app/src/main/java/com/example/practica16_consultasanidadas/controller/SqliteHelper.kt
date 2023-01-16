package com.example.practica16_consultasanidadas.controller

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.lang.UProperty.NAME
import android.os.Build
import com.example.practica16_consultasanidadas.model.*

class SqliteHelper(context: Context?):
    SQLiteOpenHelper(context, NAME, null, VERSION){

    companion object{
        private const val NAME = "Tienda.db"
        private const val VERSION = 1
    }


    override fun onCreate(sqLiteDatabase: SQLiteDatabase?) {
        sqLiteDatabase?.execSQL(
            "CREATE TABLE " +
                    ArticuloContract.TABLE_NAME + " ( "
                    + ArticuloContract.CODIGOARTICULO + " TEXT PRIMARY KEY, "
                    + ArticuloContract.NOMBREARTICULO + " TEXT NOT NULL, "
                    + ArticuloContract.PVP + " DOUBLE NOT NULL, "
                    + ArticuloContract.IVA + " INT NOT NULL, "
                    + ArticuloContract.PROVEEDOR + " TEXT NOT NULL, "
                    + "FOREIGN KEY ("+ ArticuloContract.PROVEEDOR + ") " +
                    " REFERENCES " + ProveedorContract.TABLE_NAME + "(" + ProveedorContract.NOMBREPROVEEDOR
                    +"));"

        )

        sqLiteDatabase?.execSQL(
            "CREATE TABLE " +
                    ProveedorContract.TABLE_NAME + " ( "
                    + ProveedorContract.CODIGOPROVEEDOR + " TEXT PRIMARY KEY, "
                    + ProveedorContract.NOMBREPROVEEDOR + " TEXT NOT NULL, "
                    + ProveedorContract.DIRECCION + " TEXT NOT NULL, "
                    + ProveedorContract.TELEFONO + " INT NOT NULL, "
                    + ProveedorContract.PROVINCIA + " INT NOT NULL,"
                    + "FOREIGN KEY ("+ ProveedorContract.PROVINCIA + ") " +
                    " REFERENCES " + ProvinciaContract.TABLE_NAME + "(" + ProvinciaContract.CODIGOPROVINCIA
                    +"));"
        )

        sqLiteDatabase?.execSQL(
            "CREATE TABLE " +
                    ProvinciaContract.TABLE_NAME + " ( "
                    + ProvinciaContract.CODIGOPROVINCIA + " INT PRIMARY KEY, "
                    + ProvinciaContract.NOMBREPROVINCIA + " TEXT NOT NULL "
                    +");"
        )

        sqLiteDatabase?.execSQL(
            "INSERT INTO " + ProvinciaContract.TABLE_NAME + " (codigoProvincia, nombreProvincia) VALUES"+
                    "(1, 'Valladolid')," +
                    "(2, 'Madrid')," +
                    "(3, 'Barcelona')"
        )

        sqLiteDatabase?.execSQL(
            "INSERT INTO " + ProveedorContract.TABLE_NAME + " (codigoProveedor, nombreProveedor, direccion, telefono, provincia) VALUES"+
                    "(1, 'Julen', 'Portillo de Balboa 23', 684215133, 1)," +
                    "(2, 'Abraham', 'Calle Belzunegi 15', 63653633, 2)," +
                    "(3, 'Daniel', 'Yoqse 56', 6532652356, 3)"
        )
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertar(articulo : Articulo): Long {
        val db = writableDatabase
        val values = ContentValues()
        values.put(ArticuloContract.CODIGOARTICULO, articulo.codigoArticulo)
        values.put(ArticuloContract.NOMBREARTICULO, articulo.nombreArticulo)
        values.put(ArticuloContract.PVP, articulo.pvp)
        values.put(ArticuloContract.IVA, articulo.iva)
        values.put(ArticuloContract.PROVEEDOR, articulo.proveedor)
        return db.insert(ArticuloContract.TABLE_NAME, null, values)
    }

    fun NombreProveedores (): android.database.Cursor {
        val db = writableDatabase
        var ArrayProveedores : android.database.Cursor =
            db.rawQuery("SELECT nombreProveedor FROM " + ProveedorContract.TABLE_NAME + "", null)
        return ArrayProveedores
    }

    fun Proveedores (): android.database.Cursor {
        val db = writableDatabase
        var ArrayProveedores : android.database.Cursor =
            db.rawQuery("SELECT * FROM " + ProveedorContract.TABLE_NAME + "", null)
        return ArrayProveedores
    }

    fun leerArticulos (): android.database.Cursor {
        val db = writableDatabase
        var nombresArticulos : android.database.Cursor =
            db.rawQuery("SELECT nombreArticulo FROM " + ArticuloContract.TABLE_NAME + "", null)
        return nombresArticulos
    }

    fun encontrarArticulo (nombre : String): android.database.Cursor {
        val db = writableDatabase
        var resultado : android.database.Cursor =
            db.rawQuery("SELECT * FROM " + ArticuloContract.TABLE_NAME + " WHERE nombreArticulo= '" + nombre + "'", null)
        return resultado
    }

    fun consultaMultiple (nombre: String): android.database.Cursor {
        val db = writableDatabase
        var consulta : android.database.Cursor =
            db.rawQuery("SELECT * FROM " + ArticuloContract.TABLE_NAME + " INNER JOIN " + ProveedorContract.TABLE_NAME + " ON " + ArticuloContract.PROVEEDOR + " = "+
                ProveedorContract.NOMBREPROVEEDOR + " INNER JOIN " + ProvinciaContract.TABLE_NAME + " ON " + ProveedorContract.PROVINCIA + " = " +
                ProvinciaContract.CODIGOPROVINCIA + " WHERE nombreArticulo = '" + nombre + "'",  null)
        return consulta
    }

}