package com.nadia.ucpakhir.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nadia.ucpakhir.repository.AktivitasPertanianRepository
import com.nadia.ucpakhir.repository.NetworkAktivitasPertanianRepository
import com.nadia.ucpakhir.service_api.AktivitasPertanianService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AktivitasPertanianAppContainer{
    val aktivitasPertanianRepository: AktivitasPertanianRepository
}

class AktivitasPertanianContainer : AktivitasPertanianAppContainer {

    private val baseUrl = "http://10.0.2.2:3000/aktivitas_pertanian/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val aktivitasPertanianService : AktivitasPertanianService by lazy {
        retrofit.create(AktivitasPertanianService::class.java)
    }

    override val aktivitasPertanianRepository : AktivitasPertanianRepository by lazy {
        NetworkAktivitasPertanianRepository(aktivitasPertanianService)
    }
}