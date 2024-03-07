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


    private lateinit var calcularnotas: TextView
    private lateinit var guardar: Button
    private lateinit var ingresarNombre: TextView
    private lateinit var ingresarNota: EditText
    private lateinit var ingresarPorcentaje: EditText
    private lateinit var progreso: ProgressBar
    private var EstudianteActual: Estudiante = Estudiante()
    private lateinit var SiguienteEstudiante: Button
    private lateinit var promedio: TextView
    private lateinit var NotaFinal: TextView


    //Variable global para validar el porcentaje
    private var porcentajeAcumulado = 0

    val listaNota: MutableList<Double> = mutableListOf()
    val listaporcentaje: MutableList<Int> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos la variable

        guardar = findViewById(R.id.guardar)
        ingresarNombre = findViewById(R.id.ingresarNombre)
        ingresarNota = findViewById(R.id.ingresarNota)
        ingresarPorcentaje = findViewById(R.id.ingresarPorcentaje)
        progreso = findViewById(R.id.progreso)
        calcularnotas = findViewById(R.id.calcularnotas)
        SiguienteEstudiante = findViewById(R.id.SiguienteEstudiante)
        promedio = findViewById(R.id.promedio)
        NotaFinal = findViewById(R.id.NotaFinal)

        //---AND---
        guardar.setOnClickListener {


            val nota = (ingresarNota.text.toString())

            val porcentaje = (ingresarPorcentaje.text.toString())

            val nombre = (ingresarNombre.text.toString())

            EstudianteActual.nombre = nombre
            EstudianteActual.porcentajes = listaporcentaje
            EstudianteActual.notas = listaNota


            if (nota.isNullOrEmpty() || porcentaje.isNullOrEmpty() || nombre.isNullOrEmpty()) {
                Toast.makeText(
                    this, "Los datos no son validos",
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener

            }

            if (validarNota(nota.toDouble()) && validarPorcentaje(porcentaje.toInt()) && validarNombre(
                    nombre
                )
            ) {
                listaNota.add(nota.toDouble())

                listaporcentaje.add(porcentaje.toInt())
                porcentajeAcumulado += porcentaje.toInt()

                ingresarNombre.isEnabled = false
                ingresarNota.text.clear()
                ingresarPorcentaje.text.clear()

                actualizarProgreso(porcentajeAcumulado)

                Toast.makeText(this, "la nota ingresada es correcta", Toast.LENGTH_LONG).show()


            } else {


                Toast.makeText(
                    this, "Los datos ingresados no son validos",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }


    fun actualizarProgreso(porcentaje: Int) {
        progreso.progress = porcentaje


        if (porcentaje >= 100) {
            guardar.isEnabled = true
        }
    }

    fun validarNota(nota: Double): Boolean {
        return nota >= 0 && nota <= 5
    }

    fun validarPorcentaje(porcentaje: Int): Boolean {
        return porcentajeAcumulado + porcentaje <= 100
    }

    fun validarNombre(nombre: String): Boolean {
        return !nombre.matches(Regex(".*\\d.*"))
    }


    class Estudiante() {

        var nombre: String = " "
        var notas: List<Double> = listOf()
        var porcentajes: List<Int> = listOf()


        fun calcularPromedio(): Double {
            var sumaNotas = 0.0
            for (n in notas) {
                sumaNotas += n
            }
            return sumaNotas / notas.size

        }
    ///
        fun notaFinal(): Double {
            var notaFinal: Double = 0.0
            var contador = 0

            for (n in notas) {
                notaFinal += (n * porcentajes[contador]) / 100
                contador++
            }

            return notaFinal
        }
    }
}
