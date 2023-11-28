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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dam2jms.juegosappviewmodel.ui.ViewModelSiete

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun sieteScreen(navController: NavController, mvvm: ViewModelSiete) {
    val puntuacionJugador: Double by mvvm.puntuacionJugador.observeAsState(initial = 0.0)
    val puntuacionPC: Double by mvvm.puntuacionPC.observeAsState(initial = 0.0)
    val resultado: String by mvvm.resultado.observeAsState("")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(text = "PUNTUACION JUGADOR: $puntuacionJugador\n PUNTUACION PC: $puntuacionPC") },
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
        sieteBodyContent(
            modifier = Modifier.padding(paddingValues),
            mvvm = mvvm,
            resultado = resultado
        )
    }
}

@Composable
fun sieteBodyContent(modifier: Modifier, mvvm: ViewModelSiete, resultado: String){

    var mostrarAlertDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        if (mostrarAlertDialog && resultado.isNotEmpty()) {
            AlertDialog(
                text = {
                    Text(text = resultado)
                },
                onDismissRequest = { mostrarAlertDialog = false },
                confirmButton = {
                    TextButton(onClick = { mostrarAlertDialog = false }) {
                        Text(text = "OK")
                    }
                }
            )
        }

        Button(
            onClick = {
                //llamo al metodo del viewmodel con la logica
                mvvm.onJuego()
                mostrarAlertDialog = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text = "Tomar Carta")
        }
    }
}
