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
    suspend fun updatePekerja(){
        viewModelScope.launch {
            try {
                pekerjaRepository.updatePekerja(_idpkrj, updatePekerjaUiState.insertPekerjaUiEvent.toPekerja())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}