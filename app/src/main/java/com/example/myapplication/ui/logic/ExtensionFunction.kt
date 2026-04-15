package com.example.myapplication.ui.logic
import com.example.myapplication.ui.model.*

fun Int.validarEdad():Boolean{

    if (this < 18){
        return false
    }
    return true

    }

fun validarPrioridad(asistente: Asistente, campo: (Asistente) -> String):String{
    return campo(asistente)
}