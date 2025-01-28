package com.nadia.ucpakhir.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.nadia.ucpakhir.repository.AktivitasPertanianRepository
import com.nadia.ucpakhir.repository.CatatanPanenRepository
import com.nadia.ucpakhir.repository.NetworkAktivitasPertanianRepository
import com.nadia.ucpakhir.repository.NetworkCatatanPanenRepository
import com.nadia.ucpakhir.service_api.AktivitasPertanianService
import com.nadia.ucpakhir.service_api.CatatanPanenService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface CatatanPanenAppContainer{
    val catatanPanenRepository : CatatanPanenRepository
}

class CatatanPanenContainer : CatatanPanenAppContainer {

    private val baseUrl = "http://10.0.2.2:3000/catatan_panen/"
    private val json = Json { ignoreUnknownKeys = true }
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl).build()

    private val catatanPanenService: CatatanPanenService by lazy {
        retrofit.create(CatatanPanenService::class.java)
    }

    override val catatanPanenRepository: CatatanPanenRepository by lazy {
        NetworkCatatanPanenRepository(catatanPanenService)
    }
}