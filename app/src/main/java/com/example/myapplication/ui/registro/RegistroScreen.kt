package com.example.myapplication.ui.registro
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.material3.*
import com.example.myapplication.ui.model.*
import com.example.myapplication.ui.logic.*
import com.example.myapplication.ui.states.*


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
            val edad = edadInput.trim().toIntOrNull()

            if (!nombreInput.validarNombre()) {
                estado = RegistroState.Error("El Nombre no es correcto")
            }else if (!tipoEntradaInput.validarTipoEntrada()){
                estado = RegistroState.Error("Tipo de entrada inválido")
            }
            else {
                edad?.let { edadValida ->
                    if (edadValida.edadPositiva()) {
                        estado = RegistroState.Error("Edad es incorrecta")
                    }
                    else if (!edadValida.validarEdad()) {
                        estado = RegistroState.Error("Usted es menor de edad")
                    }else {
                        val asistente = Asistente(nombreInput, edadValida, tipoEntradaInput.trim().uppercase()).apply {
                            println("Asistente: $nombre")
                        }

                        val resultado = validarPrioridad(asistente) {
                            if (it.TipoEntrada == "VIP") {
                                "PRIORIDAD VIP\n Nombre: ${it.nombre}, Edad: ${it.edad}, Tipo: ${it.TipoEntrada} \nTiene un 20% de descuento en su entrada"
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
