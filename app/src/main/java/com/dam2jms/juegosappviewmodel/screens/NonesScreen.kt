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
import com.dam2jms.juegosappviewmodel.ui.ViewModelNones

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun nonesScreen(navController: NavController, mvvm: ViewModelNones) {

    val puntuacion: Int by mvvm.puntuacion.observeAsState(initial = 0)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "PUNTUACION: ${puntuacion}") },
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
        nonesBodyContent(modifier = Modifier.padding(paddingValues), mvvm)
    }
}

@Composable
fun nonesBodyContent(modifier: Modifier, mvvm: ViewModelNones) {
    val seleccionJugador: String by mvvm.seleccionJugador.observeAsState(initial = "")
    val numeroJugador: Int by mvvm.numeroJugador.observeAsState(initial = 0)

    var mostrarAlertDialog by rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))
        OutlinedTextField(value = seleccionJugador,
            onValueChange = { mvvm.onSeleccionJugador(seleccionJugadorUi = it, numeroJugadorUi = numeroJugador) },
            label = { Text(text = "Elije entre pares o nones") },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )
        OutlinedTextField(
            value = numeroJugador.toString(),
            onValueChange = { mvvm.onSeleccionJugador(seleccionJugadorUi = seleccionJugador, numeroJugadorUi = if (it.equals("")) 0 else it.toInt()) },
            label = { Text(text = "Elije la tirada entre 1 y 5") },
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
            }, modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "JUGAR")
        }
    }
}


//lo mismo pero hecho sin viewmodel
/*@Composable
fun nonesScreen(navController: NavController) {

    var nonesJugador by rememberSaveable { mutableStateOf("") }
    var numeroJugador by rememberSaveable { mutableStateOf("") }

    var nonesPC by rememberSaveable { mutableStateOf("") }
    var numeroPC by rememberSaveable { mutableIntStateOf(0) }

    var suma by rememberSaveable { mutableIntStateOf(0) }
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
            value = nonesJugador,
            onValueChange = { nonesJugador = it },
            label = { Text(text = "Elije entre pares o nones") },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        OutlinedTextField(
            value = numeroJugador,
            onValueChange = { numeroJugador = it },
            label = { Text(text = "Elije la tirada entre 1 y 5") },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (mostrarAlertDialog) {
            AlertDialog(
                text = { Text(text = "Yo he sacado un $numeroPC, suman $suma. Ganan $ganador") },
                onDismissRequest = { mostrarAlertDialog = false },
                confirmButton = {
                    TextButton(onClick = {
                        mostrarAlertDialog = false
                    }) { Text(text = "OK") }
                })
        }

        Button(
            onClick = {

                //genero de forma aleatoria que el PC escoja entre pares o nones
                val seleccionPC: String = if (seleccionJugador == "nones") "pares" else "nones"
                //genero un numero aleatorio entre 1 y 5 para la PC
                numeroPC = (1..5).random()
                suma = numeroJugador.toInt() + numeroPC

                // si el numero del jugador es mayor o igual a 1 y es menor o igual a 5.
                // si no, saca mensaje de que el numero tiene que ser entre 1 y 5
                if (numeroJugador in 1..5) {
                    // si es nones y es impar la suma o si es pares y es par la suma
                    if ((seleccionJugador == "nones" && suma % 2 != 0) || (seleccionJugador == "pares" && suma % 2 == 0)) {
                        mostrarAlertDialog = true
                        ganador = "pares"
                    } else {
                        mostrarAlertDialog = true
                        ganador = "nones"
                    }
                }else{
                    Toast.makeText(context, "El numero debe ser entre 1 y 5", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)

        ) {
            Text(text = "JUGAR")
        }
    }
*/