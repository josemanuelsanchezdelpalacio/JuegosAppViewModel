package com.dam2jms.juegosappviewmodel.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ViewModelSiete : ViewModel() {

    private val _puntuacionJugador = MutableLiveData(0.0)
    val puntuacionJugador: LiveData<Double> = _puntuacionJugador

    private val _puntuacionPC = MutableLiveData(0.0)
    val puntuacionPC: LiveData<Double> = _puntuacionPC

    private val _dineroGanadoJugador = MutableLiveData(0)
    val dineroGanadoJugador: LiveData<Int> = _dineroGanadoJugador

    private val _dineroGanadoPC = MutableLiveData(0)
    val dineroGanadoPC: LiveData<Int> = _dineroGanadoPC

    private val _resultado = MutableLiveData<String>()
    val resultado: LiveData<String> = _resultado

    private val _apuesta = MutableLiveData(apuestaRandom())
    val apuesta: LiveData<Int> = _apuesta

    private val _cartas = MutableLiveData(listOf("As", "2", "3", "4", "5", "6", "7", "Sota", "Caballo", "Rey"))
    val cartas: LiveData<List<String>> = _cartas

    fun onJuego() {
        // Tanto el jugador como el PC obtienen una carta random y se les da el valor de esa carta
        val cartaJugador = cartas()
        val valorCartaJugador = valorCarta(cartaJugador)
        val cartaPC = cartas()
        val valorCartaPC = valorCarta(cartaPC)

        // Se van sumando cada vez que piden una carta
        val nuevaPuntuacionJugador = puntuacionJugador.value!! + valorCartaJugador
        _puntuacionJugador.value = nuevaPuntuacionJugador
        val nuevaPuntuacionPC = puntuacionPC.value!! + valorCartaPC
        _puntuacionPC.value = nuevaPuntuacionPC

        // Si uno de los dos llega a 7.5 ganan el doble de la apuesta
        if (nuevaPuntuacionPC == 7.5) {
            _resultado.value = "Ganador PC. Gana el doble de la apuesta"
            _dineroGanadoJugador.value = apuesta.value!! * 2
        } else if (nuevaPuntuacionJugador == 7.5) {
            _resultado.value = "Ganaste. Ganas el doble de la apuesta"
            _dineroGanadoPC.value = apuesta.value!! * 2
        }

        // Si se pasan, pagan a la banca el importe de la apuesta
        if (nuevaPuntuacionPC > 7.5) {
            _resultado.value = "PC se pasó de 7.5. Paga"
            _dineroGanadoJugador.value = -apuesta.value!!
            _dineroGanadoPC.value = apuesta.value!! * 2
            _apuesta.value = 0
        } else if (nuevaPuntuacionJugador > 7.5) {
            _resultado.value = "Te pasaste de 7.5. Pagas"
            _dineroGanadoJugador.value = apuesta.value!! * 2
            _dineroGanadoPC.value = -apuesta.value!!
            _apuesta.value = 0
        }

        // Si uno de los jugadores llega a 7.5 o se pasa, reiniciar el juego
        if (nuevaPuntuacionJugador >= 7.5 || nuevaPuntuacionPC >= 7.5) {
            onReset()
        }
    }

    fun onReset() {
        _puntuacionJugador.value = 0.0
        _puntuacionPC.value = 0.0
        _resultado.value = ""
        _apuesta.value = apuestaRandom()
        _dineroGanadoJugador.value = 0
        _dineroGanadoPC.value = 0
    }

    private fun apuestaRandom(): Int {
        return (100..500).random()
    }

    private fun cartas(): String {
        return cartas.value!!.random()
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
