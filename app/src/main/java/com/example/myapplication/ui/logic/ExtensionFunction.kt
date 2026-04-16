package com.example.myapplication.ui.logic
import com.example.myapplication.ui.model.*

fun Int.validarEdad():Boolean{

    if (this < 18){
        return false
    }
    return true

    }

fun String.validarNombre():Boolean {
    return this.isNotBlank() && this.none {it.isDigit()}
}

fun validarPrioridad(asistente: Asistente, campo: (Asistente) -> String):String{
    return campo(asistente)
}

fun String.validarTipoEntrada(): Boolean {
    val tipo = this.trim().uppercase()
    return tipo == "VIP" || tipo == "GENERAL"
}