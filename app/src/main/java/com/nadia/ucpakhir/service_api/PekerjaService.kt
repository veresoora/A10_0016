package com.nadia.ucpakhir.service_api

import com.nadia.ucpakhir.model.AllPekerjaResponse
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.PekerjaDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface PekerjaService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("add")
    suspend fun insertPekerja(@Body pekerja: Pekerja)

    @GET(".")
    suspend fun getAllPekerja(): AllPekerjaResponse

    @GET("{id_pekerja}")
    suspend fun getPekerjaID(@Path("id_pekerja") idPekerja: Int): PekerjaDetailResponse

    @PUT("{id_pekerja}")
    suspend fun updatePekerja(@Path("id_pekerja")idPekerja: Int, @Body pekerja: Pekerja)

    @DELETE("{id_pekerja}")
    suspend fun  deletePekerja(@Path("id_pekerja") idPekerja: Int): Response<Void>

}