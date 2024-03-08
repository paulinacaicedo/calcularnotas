package com.isabel.caicedo.calcularnotas


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {


    // declaremos la variable


    private lateinit var calcularnotas: TextView
    private lateinit var finalizar: Button
    private lateinit var ingresarNombre: EditText
    private lateinit var ingresarNota: EditText
    private lateinit var ingresarPorcentaje: EditText
    private lateinit var progreso: ProgressBar
    private var EstudianteActual: Estudiante = Estudiante()
    private lateinit var SiguienteEstudiante: Button
    private lateinit var promedio: TextView
    private lateinit var NotaFinal: TextView
    private lateinit var guardar : Button


    //Variable global para validar el porcentaje
    private var porcentajeAcumulado = 0

    val listaNota: MutableList<Double> = mutableListOf()
    val listaporcentaje: MutableList<Int> = mutableListOf()
    val listaEstudiante : MutableList<Estudiante> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos la variable

        finalizar = findViewById(R.id.finalizar)
        ingresarNombre = findViewById(R.id.ingresarNombre)
        ingresarNota = findViewById(R.id.ingresarNota)
        ingresarPorcentaje = findViewById(R.id.ingrearPorcentaje)
        progreso = findViewById(R.id.progreso)
        calcularnotas = findViewById(R.id.calcularnotas)
        SiguienteEstudiante = findViewById(R.id.SiguienteEstudiante)
        promedio = findViewById(R.id.promedio)
        NotaFinal = findViewById(R.id.NotaFinal)
        guardar = findViewById(R.id.guardar)


        SiguienteEstudiante.setOnClickListener(){
            nuevoEstudiante()
        }


        finalizar.setOnClickListener(){


            NotaFinal.text = "Nota Final : " + EstudianteActual.notaFinal()

            promedio.text = "promedio" + EstudianteActual.calcularPromedio()

            SiguienteEstudiante.isEnabled = true
        }
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

            if (validarNota(nota.toDouble()) && validarPorcentaje(porcentaje.toInt()) && validarNombre(nombre)) {
                listaNota.add(nota.toDouble())

                listaporcentaje.add(porcentaje.toInt())
                porcentajeAcumulado += porcentaje.toInt()

                ingresarNombre.isEnabled = false
                ingresarNota.text.clear()
                ingresarPorcentaje.text.clear()

                actualizarProgreso(porcentajeAcumulado)

                Toast.makeText(this, "se ingreso la nota correctamente", Toast.LENGTH_LONG).show()

            } else {


                Toast.makeText(
                    this, "Los datos ingresados no son validos",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }


    fun nuevoEstudiante (){
        ingresarNombre.text.clear()
        progreso.progress = 0
        porcentajeAcumulado = 0
        ingresarNota.text.clear()
        ingresarPorcentaje.text.clear()
        promedio.text = ""
        NotaFinal.text = ""
        listaNota.clear()
        listaporcentaje.clear()

        ingresarNombre.isEnabled = true
        finalizar.isEnabled = false
        SiguienteEstudiante.isEnabled = false
    }

    fun actualizarProgreso(porcentaje: Int) {
        progreso.progress = porcentaje


        if (porcentaje >= 100) {
            guardar.isEnabled = false
            finalizar.isEnabled = true
            SiguienteEstudiante.isEnabled = true

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
        var Estudiante : List<Estudiante> = listOf()


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
