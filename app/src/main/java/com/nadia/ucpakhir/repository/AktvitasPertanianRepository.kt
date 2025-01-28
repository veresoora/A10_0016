package com.nadia.ucpakhir.repository

import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.AllAktivitasResponse
import com.nadia.ucpakhir.service_api.AktivitasPertanianService
import okio.IOException

interface AktivitasPertanianRepository {

    suspend fun insertAktivitas(aktivitas: Aktivitas)

    suspend fun getAktivitas(): AllAktivitasResponse

    suspend fun updateAktivitas(idAktivitas: Int, aktivitas: Aktivitas)

    suspend fun deleteAktivitas(idAktivitas: Int)

    suspend fun getAktivitasID(idAktivitas: Int): Aktivitas
}

class NetworkAktivitasPertanianRepository (
    private val aktivitasPertanianService: AktivitasPertanianService
) : AktivitasPertanianRepository{

    override suspend fun insertAktivitas(aktivitas: Aktivitas) {
        aktivitasPertanianService.insertAktivitas(aktivitas)
    }

    override suspend fun updateAktivitas(idAktivitas: Int, aktivitas: Aktivitas) {
        aktivitasPertanianService.updateAktivitas(idAktivitas, aktivitas)
    }

    override suspend fun deleteAktivitas(idAktivitas: Int) {
        try {
            val response = aktivitasPertanianService.deleteAktivitas(idAktivitas)
            if (response.isSuccessful) {
                throw IOException("Gagal untuk menghapus aktivitas pertanian. HTTP Status code: " +
                        "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw  e
        }
    }

    override suspend fun getAktivitas(): AllAktivitasResponse =
        aktivitasPertanianService.getAllAktivitas()

    override suspend fun getAktivitasID(idAktivitas: Int): Aktivitas {
        return aktivitasPertanianService.getAktivitasID(idAktivitas).data
    }
}