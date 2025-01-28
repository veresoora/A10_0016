package com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.AktivitasPertanianRepository
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiAktivitasUpdate
import kotlinx.coroutines.launch

class UpdateAktivitasViewModel (
    savedStateHandle: SavedStateHandle,
    private val aktivitasPertanianRepository: AktivitasPertanianRepository,
    private val tnmn: TanamanRepository,
    private val pkrj: PekerjaRepository

): ViewModel(){
    var updateAktivitasUiState by mutableStateOf(InserAktivitasUiState())
        private set
    var listTanaman by mutableStateOf<List<Tanaman>> (listOf())
        private set

    var listPekerja by mutableStateOf<List<Pekerja>>(listOf())
        private set

    private val _idaktvts: Int = checkNotNull(savedStateHandle[DestinasiAktivitasUpdate.IDaktvts])
    init {
        viewModelScope.launch {
            val aktivitas = aktivitasPertanianRepository.getAktivitasID(_idaktvts)
            updateAktivitasUiState = aktivitas.toUiStateAktivitas()
            tanaman()
            pekerja()

            updateAktivitasUiState = updateAktivitasUiState.copy(
                insertAktivitasUiEvent = updateAktivitasUiState.insertAktivitasUiEvent.copy(
                    idTanaman = listTanaman.find { it.idTanaman == aktivitas.idTanaman }?.idTanaman ?: 0,
                    idPekerja = listPekerja.find { it.idPekerja == aktivitas.idPekerja }?.idPekerja ?: 0
                )
            )
        }
    }
    suspend fun tanaman() {
        listTanaman = tnmn.getTanamanAll()
        listTanaman.forEach { id ->
            println("Tanaman: ${id.idTanaman}")
        }
    }

    suspend fun pekerja() {
        listPekerja = pkrj.getPekerjaAll()
        listPekerja.forEach { id ->
            println("Pekerja: ${id.idPekerja}")
        }
    }
    fun updateInsertAktivitasState(insertAktivitasUiEvent: InsertAktivitasUiEvent){
        updateAktivitasUiState = InserAktivitasUiState(insertAktivitasUiEvent = insertAktivitasUiEvent)
    }
    fun validateFields(): Boolean {
        val event = updateAktivitasUiState.insertAktivitasUiEvent
        val errorState = FormErrorState(
            idTanaman = if (event.idTanaman > 0) null else "Nama Tanaman tidak boleh kosong",
            idPekerja = if (event.idPekerja > 0) null else "Nama Pekerja tidak boleh kosong",
            tanggalAktivitas = if (event.tanggalAktivitas.isNotEmpty()) null else "Tanggal Aktivitas tidak boleh kosong",
            deskripsiAktivitas = if (event.deskripsiAktivitas.isNotEmpty()) null else "Deskripsi Aktivitas tidak boleh kosong"
        )

        updateAktivitasUiState = updateAktivitasUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    suspend fun updateAktivitas(){
        val currentEvent = updateAktivitasUiState.insertAktivitasUiEvent
        viewModelScope.launch {
            try {
                aktivitasPertanianRepository.updateAktivitas(_idaktvts, currentEvent.toAktivitas())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}