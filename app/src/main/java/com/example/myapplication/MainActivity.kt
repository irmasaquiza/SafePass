package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.unit.dp

import com.example.myapplication.ui.model.*
import com.example.myapplication.ui.logic.*
import com.example.myapplication.ui.states.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .padding(16.dp)
                    ) {

                        SafePass()
                    }
                }
            }
        }
    }
}

@Composable
fun SafePass(){
    var nombreInput by remember { mutableStateOf("") }
    var edadInput by remember { mutableStateOf("") }
    var tipoEntradaInput by remember { mutableStateOf("") }
    var estado by remember { mutableStateOf<RegistroState>(RegistroState.Idle) }

    Column {
        Text("Aplicacion SafePass")

        TextField(
            value = nombreInput,
            onValueChange = { nombreInput = it },
            label = { Text("Nombre") }
        )
        TextField(
            value = edadInput,
            onValueChange = { edadInput = it },
            label = { Text("Edad") }
        )
        TextField(
            value = tipoEntradaInput,
            onValueChange = { tipoEntradaInput = it },
            label = { Text("Tipo de entrada") }
        )

        Button(onClick = {
            val edad = edadInput.toIntOrNull()

            if (nombreInput.isBlank()) {
                estado = RegistroState.Error("Nombre es obligatorio")
            } else {
                edad?.let { edadValida ->

                    if (!edadValida.validarEdad()) {
                        estado = RegistroState.Error("Usted es menor de edad")
                    } else {
                        val asistente = Asistente(nombreInput, edadValida, tipoEntradaInput.trim().uppercase())

                        val resultado = validarPrioridad(asistente) {
                            if (it.TipoEntrada == "VIP") {
                                "Nombre: ${it.nombre}, Edad: ${it.edad}, Tipo de pase: ${it.TipoEntrada}"
                            } else {
                                "Nombre: ${it.nombre}, Edad: ${it.edad}, Tipo de pase: ${it.TipoEntrada}"
                            }
                        }

                        estado = RegistroState.Succes(resultado)
                    }

                } ?: run {
                    estado = RegistroState.Error("Edad es incorrecta")
                }
            }
        }) {
            Text("Registrar")
        }

        when(estado){

            is RegistroState.Idle ->{
                Text("Ingrese los datos")
            }
            is RegistroState.Succes -> {
                Text((estado as RegistroState.Succes).mensaje)
            }

            is RegistroState.Error -> {
                Text((estado as RegistroState.Error).mensaje)
            }
        }



    }





}