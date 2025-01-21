package com.nadia.ucpakhir.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nadia.ucpakhir.repository.NetworkTanamanRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import com.nadia.ucpakhir.service_api.TanamanService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface TanamanAppContainer{
    val tanamanRepository: TanamanRepository
}

class TanamanContainer : TanamanAppContainer {

    private val baseUrl = "http://10.0.2.2:3000/tanaman/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val tanamanService: TanamanService by lazy {
        retrofit.create(TanamanService::class.java)
    }

    override val tanamanRepository: TanamanRepository by lazy {
        NetworkTanamanRepository(tanamanService)
    }
}