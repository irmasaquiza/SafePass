package com.example.myapplication.ui.states

sealed class RegistroState {
    object Idle : RegistroState()
    data class  Succes(val mensaje:String):RegistroState()
    data class Error(val mensaje:String):RegistroState()
}