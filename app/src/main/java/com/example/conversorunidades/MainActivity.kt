package com.example.conversorunidades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.conversorunidades.ui.theme.ConversorUnidadesTheme
import androidx.compose.runtime.*


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConversorUnidadesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ConversorUnidades()
                }
            }
        }
    }
}


@Composable
fun ConversorUnidades(){
    val categorias = listOf("Temperatura", "Comprimento", "Hora", "Peso", "Ângulo", "Área")
    var categoriaSelecionada by remember { mutableStateOf(categorias[0]) }

    Column (modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        Text("Conversor de Unidades", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Seletor("Categoria", categorias, categoriaSelecionada){
            categoriaSelecionada = it
        }

        Spacer(modifier = Modifier.height(16.dp))

    }


}

@Composable
fun Seletor(label: String, opcoes: List<String>, selecionado: String, aoSelecionar: (String) -> Unit){
    var expandido by remember { mutableStateOf(false) }

    Column {
        Text(label)
        Box() {
            Button(onClick = { expandido = true}) {
                Text(selecionado)
            }
            DropdownMenu(expanded = expandido, onDismissRequest = { expandido = false}) {
                opcoes.forEach {
                    DropdownMenuItem(text = { Text(it) }, onClick = {
                        aoSelecionar(it)
                        expandido = false
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ConversorUnidadesTheme {
        ConversorUnidades()
    }
}