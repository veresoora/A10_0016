package com.nadia.ucpakhir.service_api

import com.nadia.ucpakhir.model.AllTanamanResponse
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.model.TanamanDetailResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TanamanService {

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json",
    )

    @POST("add")
    suspend fun insertTanaman(@Body tanaman: Tanaman)

    @GET(".")
    suspend fun getAllTanaman(): AllTanamanResponse

    @GET("{id_tanaman}")
    suspend fun getTanamanID(@Path("id_tanaman") idTanaman: Int): TanamanDetailResponse

    @PUT("{id_tanaman}")
    suspend fun updateTanaman(@Path("id_tanaman")idTanaman: Int, @Body tanaman: Tanaman)

    @DELETE("{id_tanaman}")
    suspend fun  deleteTanaman(@Path("id_tanaman") idTanaman: Int):Response<Void>

}
