package com.example.navegacao1.model.dados

import retrofit2.http.GET
import retrofit2.http.Path


interface UsuarioServiceIF {

    @GET("usuarios")
    suspend fun listar(): List<Usuario>

    @GET("{cep}/json")
    suspend fun getEndereco(@Path("cep") cep: String): Endereco

}