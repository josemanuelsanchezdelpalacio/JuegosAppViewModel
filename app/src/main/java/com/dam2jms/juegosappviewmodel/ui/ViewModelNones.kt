package com.dam2jms.juegosappviewmodel.ui

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelNones : ViewModel() {

    private val _puntuacion = MutableLiveData(0)
    val puntuacion: LiveData<Int> = _puntuacion

    private val _seleccionJugador = MutableLiveData<String>()
    val seleccionJugador: LiveData<String> = _seleccionJugador

    private val _numeroJugador = MutableLiveData<Int>()
    val numeroJugador: LiveData<Int> = _numeroJugador

    private val _resultado = MutableLiveData<String>()
    val resultado: LiveData<String> = _resultado


    fun onSeleccionJugador(seleccionJugadorUi: String, numeroJugadorUi: Int) {
        _seleccionJugador.value = seleccionJugadorUi
        _numeroJugador.value = numeroJugadorUi
    }

    fun onJuego(context: Context) {
        // genero de forma aleatoria que el PC escoja entre pares o nones
        val seleccionPC: String = if (_seleccionJugador.value == "nones") "pares" else "nones"

        // genero un numero aleatorio entre 1 y 5 para la PC
        val numeroPC = (1..5).random()
        val suma = _numeroJugador.value!! + numeroPC!!

        //compruebo que la seleccion del jugador solo pueda ser pares o nones
        if (_seleccionJugador.value != "pares" || _seleccionJugador.value != "nones") {

            // compruebo que el numero del jugador sea entre 1 y 5
            if (_numeroJugador.value in 1..5) {

                // si es nones y es impar la suma o si es pares y es par la suma
                if ((_seleccionJugador.value == "nones" && suma % 2 != 0) || (_seleccionJugador.value == "pares" && suma % 2 == 0)) {
                    _resultado.value = "ganas"
                    _puntuacion.postValue(puntuacion.value?.plus(10))
                } else {
                    _resultado.value = "pierdes"
                    _puntuacion.postValue(puntuacion.value?.minus(5))

                }

                //muestro el resultado
                _resultado.value = "Jugador: ${_seleccionJugador.value} ${_numeroJugador.value}\n PC: $seleccionPC. ${numeroPC}\n Resultado: ${_resultado.value}"

            } else {
                Toast.makeText(context, "El numero debe ser entre 1 y 5", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, "Solo puedes elejir entre pares o nones", Toast.LENGTH_SHORT).show()
        }
    }

}