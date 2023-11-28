package com.dam2jms.juegosappviewmodel.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelSiete: ViewModel() {

    private val _puntuacionJugador = MutableLiveData(0.0)
    val puntuacionJugador: LiveData<Double> = _puntuacionJugador

    private val _puntuacionPC = MutableLiveData(0.0)
    val puntuacionPC: LiveData<Double> = _puntuacionPC

    private val _resultado = MutableLiveData<String>()
    val resultado: LiveData<String> = _resultado

    private val _cartas = MutableLiveData(listOf("As", "2", "3", "4", "5", "6", "7", "Sota", "Caballo", "Rey"))
    val cartas: LiveData<List<String>> = _cartas

    fun onJuego() {
        //tanto el jugador como el PC obtiene una carta random y se le da el valor de esa carta
        val cartaJugador = cartas.value!!.random()
        val valorCartaJugador = valorCarta(cartaJugador)
        val cartaPC = cartas.value!!.random()
        val valorCartaPC = valorCarta(cartaPC)

        //se van sumando cada vez que pida una carta
        val nuevaPuntuacionJugador = _puntuacionJugador.value!! + valorCartaJugador
        _puntuacionJugador.value = nuevaPuntuacionJugador
        val nuevaPuntuacionPC = _puntuacionPC.value!! + valorCartaPC
        _puntuacionPC.value = nuevaPuntuacionPC

        //si la puntuacion es igual a 7.5 gana el jugador
        if (nuevaPuntuacionJugador >= 7.5 && nuevaPuntuacionPC != 7.5) {
            _resultado.value = "Has ganado, has llegado a 7.5"
        } else if (nuevaPuntuacionPC >= 7.5 && nuevaPuntuacionJugador != 7.5) {
            _resultado.value = "Has perdido, el PC ha llegado a 7.5"
        }
    }

    private fun valorCarta(carta: String): Double {
        return when (carta) {
            "As" -> 1.0
            "2" -> 2.0
            "3" -> 3.0
            "4" -> 4.0
            "5" -> 5.0
            "6" -> 6.0
            "7" -> 7.0
            else -> 0.5
        }
    }
}
