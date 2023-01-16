package com.example.practica16_consultasanidadas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Button

class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var btnInsertar : Button
    private lateinit var btnMostrar : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnInsertar = findViewById(R.id.btn1)
        btnMostrar = findViewById(R.id.btn2)

        btnInsertar.setOnClickListener(this)
        btnMostrar.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn1 -> {
                intent = Intent(this, InsertarActivity::class.java)
                startActivity(intent)
            }

            R.id.btn2 -> {
                intent = Intent(this, MostrarActivity::class.java)
                startActivity(intent)
            }
        }
    }
}