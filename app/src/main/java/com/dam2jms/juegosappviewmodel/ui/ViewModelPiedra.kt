package com.dam2jms.juegosappviewmodel.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelPiedra : ViewModel() {

    private val _puntuacion = MutableLiveData<Int>(0)
    val puntuacion: LiveData<Int> = _puntuacion

    private val _seleccionJugador = MutableLiveData<String>()
    val seleccionJugador: LiveData<String> = _seleccionJugador

    private val _resultado = MutableLiveData<String>()
    val resultado: LiveData<String> = _resultado

    fun onSeleccionJugador(seleccionJugadorUi: String) {
        _seleccionJugador.value = seleccionJugadorUi
    }

    fun onJuego(context: Context) {
        //llamo al metodo eleccionAleatoriaPC y lo guardo en su variable
        val seleccionPC = eleccionAleatoriaPC()

        //compruebo que la seleccion del jugador solo pueda ser pidra, papel o tijeras
        if (_seleccionJugador.value != "piedra" || _seleccionJugador.value != "papel" || _seleccionJugador.value != "tijeras") {

            //comparo la seleccion del jugador y la del PC y si son iguales el resultado es "nadie"
            if (_seleccionJugador.value.equals(seleccionPC)) {
                _resultado.value = "nadie"
            } else if (
            //comparaciones para comprobar si gana el jugador
                (_seleccionJugador.value.equals("piedra") && seleccionPC.equals("tijeras")) ||
                (_seleccionJugador.value.equals("papel") && seleccionPC.equals("piedra")) ||
                (_seleccionJugador.value.equals("tijeras") && seleccionPC.equals("papel"))
            ) {
                _resultado.value = "Jugador"
                _puntuacion.postValue(puntuacion.value?.inc())

            } else {
                //si no es ninguna de las comparaciones anteriores gana el PC
                _resultado.value = "PC"
            }

            //muestro el resultado
            _resultado.value = "Jugador: ${_seleccionJugador.value}\n PC: ${seleccionPC}\n Resultado: ${_resultado.value}"
        } else {
            Toast.makeText(context, "Solo puedes elejir entre piedra, papel o tijeras", Toast.LENGTH_SHORT).show()
        }
    }
}

//metodo con una lista con las opciones de piedra, papel, tijeras que la randomiza
fun eleccionAleatoriaPC(): String {
    val opciones = listOf("piedra", "papel", "tijeras")
    return opciones.random()
}