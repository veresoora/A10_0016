package com.nadia.ucpakhir.repository

import com.nadia.ucpakhir.model.AllTanamanResponse
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.service_api.TanamanService
import okio.IOException

interface TanamanRepository {

    suspend fun insertTanaman(tanaman: Tanaman)

    suspend fun getTanaman(): AllTanamanResponse

    suspend fun updateTanaman(idTanaman: Int, tanaman: Tanaman)

    suspend fun deleteTanaman(idTanaman: Int)

    suspend fun getTanamanID(idTanaman: Int): Tanaman
}

class NetworkTanamanRepository (
    private val tanamanService: TanamanService
) : TanamanRepository{

    override suspend fun insertTanaman(tanaman: Tanaman) {
        tanamanService.insertTanaman(tanaman)
    }

    override suspend fun updateTanaman(idTanaman: Int, tanaman: Tanaman) {
        tanamanService.updateTanaman(idTanaman, tanaman)
    }

    override suspend fun deleteTanaman(idTanaman: Int) {
        try {
            val response = tanamanService.deleteTanaman(idTanaman)
            if (response.isSuccessful) {
                throw IOException("Gagal untuk menghapus tanaman. HTTP Status code: " +
                "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw  e
        }
    }

    override suspend fun getTanaman(): AllTanamanResponse =
        tanamanService.getAllTanaman()

    override suspend fun getTanamanID(idTanaman: Int): Tanaman {
        return tanamanService.getTanamanID(idTanaman).data
    }
}