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
import androidx.compose.material3.OutlinedTextField
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

        when (categoriaSelecionada) {
            "Temperatura" -> ConversorTemperatura()
        }
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

@Composable
fun ConversorTemperatura(){
    val unidades = listOf("Celsius", "Fahrenheit", "Kelvin")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Seletor("De", unidades, deUnidade) { deUnidade = it }
        Spacer(modifier = Modifier.height(8.dp))
        Seletor("Para", unidades, paraUnidade) { paraUnidade = it }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null){
                converterTemperatura(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"

        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Resultado: $resultado")

    }
}

fun converterTemperatura(valor: Double, de: String, para: String): Double {
    val celsius = when (de) {
        "Celsius" -> valor
        "Fahrenheit" -> (valor - 32) * 5 / 9
        "Kelvin" -> valor - 273.15
        else -> valor
    }
    return when (para) {
        "Celsius" -> celsius
        "Fahrenheit" -> celsius * 9 / 5 + 32
        "Kelvin" -> celsius + 273.15
        else -> celsius
    }
}


@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ConversorUnidadesTheme {
        ConversorUnidades()
    }
}