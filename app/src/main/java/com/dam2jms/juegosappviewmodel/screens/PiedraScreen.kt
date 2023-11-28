package com.dam2jms.juegosappviewmodel.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2jms.juegosappviewmodel.ui.ViewModelPiedra

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun piedraScreen(navController: NavController, mvvm: ViewModelPiedra) {

    val puntuacion: Int by mvvm.puntuacion.observeAsState(initial = 0)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "VECES GANADAS: $puntuacion") },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Atras")
                    }
                }
            )
        }
    ) { paddingValues ->
        piedraBodyContent(modifier = Modifier.padding(paddingValues), mvvm)
    }
}

@Composable
fun piedraBodyContent(modifier: Modifier, mvvm: ViewModelPiedra) {

    val seleccionJugador: String by mvvm.seleccionJugador.observeAsState(initial = "")

    var mostrarAlertDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(
            value = seleccionJugador,
            onValueChange = { mvvm.onSeleccionJugador(seleccionJugadorUi = it) },
            label = { Text(text = "Elije entre piedra, papel o tijeras") },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (mostrarAlertDialog) {
            AlertDialog(text = {
                mvvm.resultado.value?.let { Text(text = it) }
            }, onDismissRequest = { mostrarAlertDialog = false }, confirmButton = {
                TextButton(onClick = { mostrarAlertDialog = false }) { Text(text = "OK") }
            })
        }

        Button(
            onClick = {
                //llamo al metodo del viewmodel con la logica
                mvvm.onJuego(context = context)
                mostrarAlertDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Text(text = "JUGAR")
        }
    }
}

//lo mismo pero sin usar viewmodel
/*@Composable
fun piedraScreen(navController: NavController) {

    var eleccionJugador by rememberSaveable { mutableStateOf("") }
    var eleccionPC by rememberSaveable { mutableStateOf("") }
    var ganador by rememberSaveable { mutableStateOf("") }
    var mostrarAlertDialog by rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = eleccionJugador,
            onValueChange = { eleccionJugador = it },
            label = { Text(text = "Elije entre piedra, papel o tijeras") },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (mostrarAlertDialog) {
            AlertDialog(
                text = { Text(text = "Yo he sacado $eleccionPC. Gana $ganador") },
                onDismissRequest = { mostrarAlertDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        mostrarAlertDialog = false
                    }) { Text(text = "OK") }
                })
        }

        Button(
            onClick = {

                eleccionPC = eleccionAleatoriaPC()

                //si es nones y es impar la suma o si es pares y es par la suma muestra el alertdialog con el ganador segun la suma de los numeros
                if (eleccionJugador.equals(eleccionPC)) {
                    mostrarAlertDialog = true
                    ganador = "nadie"
                } else if (
                    (eleccionJugador.equals("piedra") && eleccionPC.equals("tijeras")) ||
                    (eleccionJugador.equals("papel") && eleccionPC.equals("piedra")) ||
                    (eleccionJugador.equals("tijeras") && eleccionPC.equals("papel"))
                ) {
                    mostrarAlertDialog = true
                    ganador = "Jugador"
                } else {
                    mostrarAlertDialog = true
                    ganador = "PC"
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Text(text = "JUGAR")
        }
    }
}*/