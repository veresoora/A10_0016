package com.nadia.ucpakhir.repository

import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.AllAktivitasResponse
import com.nadia.ucpakhir.model.AllPanenResponse
import com.nadia.ucpakhir.model.CatatanPanen
import com.nadia.ucpakhir.service_api.AktivitasPertanianService
import com.nadia.ucpakhir.service_api.CatatanPanenService
import okio.IOException

interface CatatanPanenRepository {

    suspend fun insertPanen(catatanPanen: CatatanPanen)

    suspend fun getPanen(): AllPanenResponse

    suspend fun updatePanen(idPanen: Int, catatanPanen: CatatanPanen)

    suspend fun deletePanen(idPanen: Int)

    suspend fun getPanensID(idPanen: Int): CatatanPanen
}

class NetworkCatatanPanenRepository (
    private val catatanPanenService: CatatanPanenService
) : CatatanPanenRepository{

    override suspend fun insertPanen(catatanPanen: CatatanPanen) {
        catatanPanenService.insertPanen(catatanPanen)
    }

    override suspend fun updatePanen(idPanen: Int, catatanPanen: CatatanPanen) {
        catatanPanenService.updatePanen(idPanen, catatanPanen)
    }

    override suspend fun deletePanen(idPanen: Int) {
        try {
            val response = catatanPanenService.deletePanen(idPanen)
            if (response.isSuccessful) {
                throw IOException("Gagal untuk menghapus Catatan Panen. HTTP Status code: " +
                        "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw  e
        }
    }

    override suspend fun getPanen(): AllPanenResponse =
        catatanPanenService.getAllPanen()

    override suspend fun getPanensID(idPanen: Int): CatatanPanen {
        return catatanPanenService.getPanenID(idPanen).data
    }
}