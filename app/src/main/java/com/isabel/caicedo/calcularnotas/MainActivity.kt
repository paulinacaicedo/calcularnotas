package com.isabel.caicedo.calcularnotas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    // declaremos la variable

    private lateinit var guardar: Button
    private lateinit var ingresarNombre: TextView
    private lateinit var ingresarNota: EditText
    private lateinit var ingresarPorcentaje: EditText
    private lateinit var progreso : ProgressBar

    //Variable global para validar el porcentaje
    private var porcentajeAcumulado = 0

    val listaNota: MutableList<Double> = mutableListOf()
    val listaporcentaje : MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos la variable

        guardar = findViewById(R.id.guardar)
        ingresarNombre = findViewById(R.id.ingresarNombre)
        ingresarNota = findViewById(R.id.ingresarNota)
        ingresarPorcentaje = findViewById(R.id.ingrsarPorcentaje)
        progreso = findViewById(R.id.progreso)


        guardar.setOnClickListener {


            val nota = (ingresarNota.text.toString())

            val porcentaje = (ingresarPorcentaje.text.toString())

            val nombre = (ingresarNombre.text.toString())

            if (nota.isNullOrEmpty() || porcentaje.isNullOrEmpty() || nombre.isNullOrEmpty()) {
                Toast.makeText(
                    this, "Los datos no son validos",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener

            }

            if (validarNota(nota.toDouble()) && validarPorcentaje(porcentaje.toInt()) && validarNombre(
                    nombre)) {
                listaNota.add(nota.toDouble())

                listaporcentaje.add(porcentaje.toInt())
                porcentajeAcumulado += porcentaje.toInt()

                ingresarNombre.isEnabled = false
                ingresarNota.text.clear()
                ingresarPorcentaje.text.clear()


            } else {


                Toast.makeText(
                    this, "La nota ingresada es correta",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }



    fun validarNota(nota : Double) : Boolean{
        return nota  >= 0 && nota <= 5
    }

    fun validarPorcentaje (porcentaje: Int) : Boolean{
        return  porcentajeAcumulado + porcentaje <=100
    }

    fun validarNombre (nombre: String) : Boolean{
        return  !nombre.matches(Regex(".*\\d.*"))
    }
}