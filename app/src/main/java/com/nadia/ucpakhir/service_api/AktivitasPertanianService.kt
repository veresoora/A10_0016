package com.nadia.ucpakhir.service_api

import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.AktivitasDetailResponse
import com.nadia.ucpakhir.model.AllAktivitasResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AktivitasPertanianService {
    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("add")
    suspend fun insertAktivitas(@Body aktivitas: Aktivitas)

    @GET(".")
    suspend fun getAllAktivitas(): AllAktivitasResponse

    @GET("{id_aktivitas}")
    suspend fun getAktivitasID(@Path("id_aktivitas") idAktivias: Int): AktivitasDetailResponse

    @PUT("{id_aktivitas}")
    suspend fun updateAktivitas(@Path("id_aktivitas")idAktivias: Int, @Body aktivitas: Aktivitas)

    @DELETE("{id_aktivitas}")
    suspend fun  deleteAktivitas(@Path("id_aktivitas") idAktivias: Int): Response<Void>

}