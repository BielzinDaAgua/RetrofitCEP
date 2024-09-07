package com.example.navegacao1.ui.telas

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.navegacao1.model.dados.Endereco
import com.example.navegacao1.model.dados.RetrofitClient
import com.example.navegacao1.model.dados.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun TelaPrincipal(modifier: Modifier = Modifier, onLogoffClick: () -> Unit) {
    val scope = rememberCoroutineScope()

    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "Tela Principal")

        //var usuarios by remember { mutableStateOf<List<Usuario>>(emptyList()) }
        var endereco by remember { mutableStateOf(Endereco()) }
        var cepInput by remember { mutableStateOf("") }  // Estado para o campo de entrada do CEP
        var enderecoCarregado by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            scope.launch {
                Log.d("principal", "Carregando usuários...")
                //usuarios = getUsuarios()
            }
        }

        OutlinedTextField(
            value = cepInput,
            onValueChange = { cepInput = it },
            label = { Text(text = "Digite o CEP") },
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                scope.launch {
                    Log.d("principal", "Buscando endereço para o CEP: $cepInput")
                    endereco = getEndereco(cepInput)
                    enderecoCarregado = true
                }
            },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        ) {
            Text("Buscar Endereço")
        }

        if (enderecoCarregado) {
            Text(text = "Logradouro: ${endereco.logradouro}")
            Text(text = "Bairro: ${endereco.bairro}")
            Text(text = "Cidade: ${endereco.localidade}")
            Text(text = "UF: ${endereco.uf}")
        }

        Button(onClick = { onLogoffClick() }, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Text("Sair")
        }

        //LazyColumn {
            //items(usuarios) { usuario ->
                //Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
                    //Column(modifier = Modifier.padding(8.dp)) {
                        //Text(text = "Nome: ${usuario.nome}")
                        //Text(text = "ID: ${usuario.id}")
                    //}
                //}
            //}
        //}
    }
}

//suspend fun getUsuarios(): List<Usuario> {
    //return withContext(Dispatchers.IO) {
        //RetrofitClient.usuarioService.listar()
    //}
//}

// Função para buscar o endereço de um CEP usando Retrofit
suspend fun getEndereco(cep: String): Endereco {
    return withContext(Dispatchers.IO) {
        RetrofitClient.usuarioService.getEndereco(cep)
    }
}
