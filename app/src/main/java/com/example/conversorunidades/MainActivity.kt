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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.font.FontWeight


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
    val historico = remember { mutableStateListOf<String>() }

    Column (modifier = Modifier.padding(20.dp)) {
        Spacer(modifier = Modifier.height(30.dp))

        Text("Conversor de Unidades", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.SemiBold))

        Spacer(modifier = Modifier.height(35.dp))

        Seletor("Categoria", categorias, categoriaSelecionada){
            categoriaSelecionada = it
        }

        Spacer(modifier = Modifier.height(75.dp))

        when (categoriaSelecionada) {
            "Temperatura" -> ConversorTemperatura(historico = historico)
            "Comprimento" -> ConversorComprimento(historico = historico)
            "Tempo" -> ConversorTempo(historico = historico)
            "Peso" -> ConversorPeso(historico = historico)
            "Ângulo" -> ConversorAngulo(historico = historico)
            "Área" -> ConversorArea(historico = historico)
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text("Histórico:", style = MaterialTheme.typography.titleMedium)
        LazyColumn {
            items(historico.reversed()) { linha ->
                Text("• $linha", modifier = Modifier.padding(4.dp))
            }
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
fun ConversorTemperatura(historico: SnapshotStateList<String>){
    val unidades = listOf("Celsius", "Fahrenheit", "Kelvin")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Row {
            Seletor("De", unidades, deUnidade) { deUnidade = it }
            Spacer(modifier = Modifier.width(30.dp))
            Seletor("Para", unidades, paraUnidade) { paraUnidade = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null){
                converterTemperatura(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
            historico.add("$valorInserido $deUnidade → $resultado $paraUnidade")
        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium)

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
fun ConversorComprimento(historico: SnapshotStateList<String>){
    val unidades = listOf("Centímetros", "Metros", "Quilómetros", "Milhas")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Row {
            Seletor("De", unidades, deUnidade) { deUnidade = it }
            Spacer(modifier = Modifier.width(30.dp))
            Seletor("Para", unidades, paraUnidade) { paraUnidade = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterComprimento(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
            historico.add("$valorInserido $deUnidade → $resultado $paraUnidade")
        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium)
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
fun ConversorTempo(historico: SnapshotStateList<String>) {
    val unidades = listOf("Horas", "Minutos", "Segundos")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Row {
            Seletor("De", unidades, deUnidade) { deUnidade = it }
            Spacer(modifier = Modifier.width(30.dp))
            Seletor("Para", unidades, paraUnidade) { paraUnidade = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterTempo(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
            historico.add("$valorInserido $deUnidade → $resultado $paraUnidade")
        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium)
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
fun ConversorPeso(historico: SnapshotStateList<String>) {
    val unidades = listOf("Gramas", "Quilogramas", "Toneladas", "Libras")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Row {
            Seletor("De", unidades, deUnidade) { deUnidade = it }
            Spacer(modifier = Modifier.width(30.dp))
            Seletor("Para", unidades, paraUnidade) { paraUnidade = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterPeso(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
            historico.add("$valorInserido $deUnidade → $resultado $paraUnidade")
        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium)
    }
}

fun converterPeso(valor: Double, de: String, para: String): Double {
    val gramas = when (de) {
        "Gramas" -> valor
        "Quilogramas" -> valor * 1000
        "Toneladas" -> valor * 1000000
        "Libras" -> valor * 453.592
        else -> valor
    }
    return when (para) {
        "Gramas" -> gramas
        "Quilogramas" -> gramas / 1000
        "Toneladas" -> gramas / 1000000
        "Libras" -> gramas / 453.592
        else -> gramas
    }
}

@Composable
fun ConversorAngulo(historico: SnapshotStateList<String>) {
    val unidades = listOf("Graus", "Radianos")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Row {
            Seletor("De", unidades, deUnidade) { deUnidade = it }
            Spacer(modifier = Modifier.width(30.dp))
            Seletor("Para", unidades, paraUnidade) { paraUnidade = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterAngulo(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
            historico.add("$valorInserido $deUnidade → $resultado $paraUnidade")
        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium)
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


@Composable
fun ConversorArea(historico: SnapshotStateList<String>) {
    val unidades = listOf("cm²", "m²", "km²", "hectares")
    var deUnidade by remember { mutableStateOf(unidades[0]) }
    var paraUnidade by remember { mutableStateOf(unidades[1]) }
    var valorInserido by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Column {
        Row {
            Seletor("De", unidades, deUnidade) { deUnidade = it }
            Spacer(modifier = Modifier.width(30.dp))
            Seletor("Para", unidades, paraUnidade) { paraUnidade = it }
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = valorInserido,
            onValueChange = { valorInserido = it },
            label = { Text("Valor") }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val valor = valorInserido.toDoubleOrNull()
            resultado = if (valor != null) {
                converterArea(valor, deUnidade, paraUnidade).toString()
            } else "Valor inválido"
            historico.add("$valorInserido $deUnidade → $resultado $paraUnidade")
        }) {
            Text("Converter")
        }

        Spacer(modifier = Modifier.height(35.dp))
        Text("Resultado: $resultado", style = MaterialTheme.typography.titleMedium)
    }
}

fun converterArea(valor: Double, de: String, para: String): Double {
    val m2 = when (de) {
        "m²" -> valor
        "cm²" -> valor / 10000
        "km²" -> valor * 1000000
        "hectares" -> valor * 10000
        else -> valor
    }
    return when (para) {
        "m²" -> m2
        "cm²" -> m2 * 10000
        "km²" -> m2 / 1000000
        "hectares" -> m2 / 10000
        else -> m2
    }
}

@Preview(showBackground = true)
@Composable
fun AppPreview() {
    ConversorUnidadesTheme {
        ConversorUnidades()
    }
}