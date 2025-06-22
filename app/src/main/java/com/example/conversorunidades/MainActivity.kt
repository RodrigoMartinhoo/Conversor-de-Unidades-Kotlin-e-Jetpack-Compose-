package com.example.conversorunidades

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
    val categorias = listOf("Temperatura", "Comprimento", "Tempo", "Peso", "Ângulo", "Área")
    var categoriaSelecionada by remember { mutableStateOf(categorias[0]) }

    Column (modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.height(16.dp))

        Text("Conversor de Unidades", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        Seletor("Categoria", categorias, categoriaSelecionada){
            categoriaSelecionada = it
        }

        Spacer(modifier = Modifier.height(50.dp))

        when (categoriaSelecionada) {
            "Temperatura" -> ConversorTemperatura()
            "Comprimento" -> ConversorComprimento()
            "Tempo" -> ConversorTempo()
            "Peso" -> ConversorPeso()
            "Ângulo" -> ConversorAngulo()
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

@Composable
fun ConversorComprimento(){
    val unidades = listOf("Centímetros", "Metros", "Quilómetros", "Milhas")
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
            resultado = if (valor != null) {
                converterComprimento(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text("Resultado: $resultado")
    }
}

fun converterComprimento(valor: Double, de: String, para: String): Double {
    val metros = when (de) {
        "Metros" -> valor
        "Quilómetros" -> valor * 1000
        "Centímetros" -> valor / 100
        "Milhas" -> valor * 1609.34
        else -> valor
    }
    return when (para) {
        "Metros" -> metros
        "Quilómetros" -> metros / 1000
        "Centímetros" -> metros * 100
        "Milhas" -> metros / 1609.34
        else -> metros
    }
}

@Composable
fun ConversorTempo() {
    val unidades = listOf("Horas", "Minutos", "Segundos")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Seletor("De", unidades, deUnidade) { deUnidade = it }
        Spacer(modifier = Modifier.width(8.dp))
        Seletor("Para", unidades, paraUnidade) { paraUnidade = it }

        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )
        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterTempo(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
        }) {
            Text("Converter")
        }
        Text("Resultado: $resultado")
    }
}

fun converterTempo(valor: Double, de: String, para: String): Double {
    val segundos = when (de) {
        "Horas" -> valor * 3600
        "Minutos" -> valor * 60
        "Segundos" -> valor
        else -> valor
    }
    return when (para) {
        "Horas" -> segundos / 3600
        "Minutos" -> segundos / 60
        "Segundos" -> segundos
        else -> segundos
    }
}


@Composable
fun ConversorPeso() {
    val unidades = listOf("Gramas", "Quilogramas", "Toneladas", "Libras")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Seletor("De", unidades, deUnidade) { deUnidade = it }
        Spacer(modifier = Modifier.width(8.dp))
        Seletor("Para", unidades, paraUnidade) { paraUnidade = it }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterPeso(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
        }) {
            Text("Converter")
        }

        Text("Resultado: $resultado")
    }
}

fun converterPeso(valor: Double, de: String, para: String): Double {
    val gramas = when (de) {
        "Gramas" -> valor
        "Quilogramas" -> valor * 1000
        "Toneladas" -> valor * 1_000_000
        "Libras" -> valor * 453.592
        else -> valor
    }
    return when (para) {
        "Gramas" -> gramas
        "Quilogramas" -> gramas / 1000
        "Toneladas" -> gramas / 1_000_000
        "Libras" -> gramas / 453.592
        else -> gramas
    }
}

@Composable
fun ConversorAngulo() {
    val unidades = listOf("Graus", "Radianos")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {

        Seletor("De", unidades, deUnidade) { deUnidade = it }
        Spacer(modifier = Modifier.width(35.dp))
        Seletor("Para", unidades, paraUnidade) { paraUnidade = it }


        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )
        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterAngulo(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
        }) {
            Text("Converter")
        }
        Text("Resultado: $resultado")
    }
}

fun converterAngulo(valor: Double, de: String, para: String): Double {
    val graus = when (de) {
        "Graus" -> valor
        "Radianos" -> Math.toDegrees(valor)
        else -> valor
    }
    return when (para) {
        "Graus" -> graus
        "Radianos" -> Math.toRadians(graus)
        else -> graus
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ConversorUnidadesTheme {
        ConversorUnidades()
    }
}