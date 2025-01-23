package com.nadia.ucpakhir.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nadia.ucpakhir.repository.NetworkPekerjaRepository
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.service_api.PekerjaService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface PekerjaAppContainer{
    val pekerjaRepository: PekerjaRepository
}

class PekerjaContainer : PekerjaAppContainer {

    private val baseUrl = "http://10.0.2.2:3000/pekerja/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val pekerjaService: PekerjaService by lazy {
        retrofit.create(PekerjaService::class.java)
    }

    override val pekerjaRepository: PekerjaRepository by lazy {
        NetworkPekerjaRepository(pekerjaService)
    }
}