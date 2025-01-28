package com.nadia.ucpakhir.ui.pekerja.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.ui.pekerja.view.DestinasiPekerjaUpdate
import kotlinx.coroutines.launch

class UpdatePekerjaViewModel (
    savedStateHandle: SavedStateHandle,
    private val pekerjaRepository: PekerjaRepository
): ViewModel(){
    var updatePekerjaUiState by mutableStateOf(InsertPekerjaUiState())
        private set
    private val _idpkrj: Int = checkNotNull(savedStateHandle[DestinasiPekerjaUpdate.IDpkrj])
    init {
        viewModelScope.launch {
            updatePekerjaUiState = pekerjaRepository.getPekerjaID(_idpkrj)
                .toUiStatePekerja()
        }
    }
    fun updateInsertPekerjaState(insertPekerjaUiEvent: InsertPekerjaUiEvent){
        updatePekerjaUiState = InsertPekerjaUiState(insertPekerjaUiEvent = insertPekerjaUiEvent)
    }
    fun validateFields(): Boolean {
        val event = updatePekerjaUiState.insertPekerjaUiEvent
        val errorState = FormErrorState(
            namaPekerja = if (event.namaPekerja.isNotEmpty()) null else "Nama Pekerja tidak boleh kosong",
            jabatan = if (event.jabatan.isNotEmpty()) null else "Jabatan tidak boleh kosong",
            kontakPekerja = if (event.kontakPekerja.isNotEmpty()) null else "Kontak Pekerja tidak boleh kosong"
        )

        updatePekerjaUiState = updatePekerjaUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    suspend fun updatePekerja(){
        val currentEvent = updatePekerjaUiState.insertPekerjaUiEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    pekerjaRepository.updatePekerja(_idpkrj, currentEvent.toPekerja())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}