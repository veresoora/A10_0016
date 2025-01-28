package com.nadia.ucpakhir.service_api

import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.AktivitasDetailResponse
import com.nadia.ucpakhir.model.AllPanenResponse
import com.nadia.ucpakhir.model.CatatanPanen
import com.nadia.ucpakhir.model.PanenDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CatatanPanenService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("add")
    suspend fun insertPanen(@Body catatanPanen: CatatanPanen)

    @GET(".")
    suspend fun getAllPanen(): AllPanenResponse

    @GET("{id_panen}")
    suspend fun getPanenID(@Path("id_panen") idPanen: Int): PanenDetailResponse

    @PUT("{id_panen}")
    suspend fun updatePanen(@Path("id_panen")idPanen: Int, @Body catatanPanen: CatatanPanen)

    @DELETE("{id_panen}")
    suspend fun  deletePanen(@Path("id_panen") idPanen: Int): Response<Void>

}