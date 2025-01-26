package com.nadia.ucpakhir.repository

import com.nadia.ucpakhir.model.AllPekerjaResponse
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.service_api.PekerjaService
import okio.IOException

interface PekerjaRepository {

    suspend fun insertPekerja(pekerja: Pekerja)

    suspend fun getPekerja(): AllPekerjaResponse

    suspend fun updatePekerja(idPekerja: Int, pekerja: Pekerja)

    suspend fun deletePekerja(idPekerja: Int)

    suspend fun getPekerjaID(idPekerja: Int): Pekerja

    suspend fun getPekerjaAll(): List<Pekerja>
}

class NetworkPekerjaRepository (
    private val pekerjaService: PekerjaService
) : PekerjaRepository{

    override suspend fun insertPekerja(pekerja: Pekerja) {
        pekerjaService.insertPekerja(pekerja)
    }

    override suspend fun updatePekerja(idPekerja: Int, pekerja: Pekerja) {
        pekerjaService.updatePekerja(idPekerja, pekerja)
    }

    override suspend fun deletePekerja(idPekerja: Int) {
        try {
            val response = pekerjaService.deletePekerja(idPekerja)
            if (response.isSuccessful) {
                throw IOException("Gagal untuk menghapus pekerja. HTTP Status code: " +
                        "${response.code()}")
            }else {
                response.message()
                println(response.message())
            }
        } catch (e:Exception) {
            throw  e
        }
    }

    override suspend fun getPekerja(): AllPekerjaResponse =
        pekerjaService.getAllPekerja()

    override suspend fun getPekerjaID(idPekerja: Int): Pekerja {
        return pekerjaService.getPekerjaID(idPekerja).data
    }

    override suspend fun getPekerjaAll(): List<Pekerja> {
        return pekerjaService.getAllPekerja().data
    }
}