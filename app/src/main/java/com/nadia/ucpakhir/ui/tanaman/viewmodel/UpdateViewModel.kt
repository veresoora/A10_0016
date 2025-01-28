package com.nadia.ucpakhir.ui.tanaman.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.repository.TanamanRepository
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiTanamanUpdate
import kotlinx.coroutines.launch

class UpdateTanamanViewModel (
    savedStateHandle: SavedStateHandle,
    private val tanamanRepository: TanamanRepository
): ViewModel(){
    var updateTanamanUiState by mutableStateOf(InsertTanamanUiState())
        private set
    private val _idtnmn: Int = checkNotNull(savedStateHandle[DestinasiTanamanUpdate.IDtnmn])
    init {
        viewModelScope.launch {
            updateTanamanUiState = tanamanRepository.getTanamanID(_idtnmn)
                .toUiStateTnmn()
        }
    }
    fun updateInsertTanamanState(insertTanamanUiEvent: InsertTanamanUiEvent){
        updateTanamanUiState = InsertTanamanUiState(insertTanamanUiEvent = insertTanamanUiEvent)
    }
    fun validateFields(): Boolean {
        val event = updateTanamanUiState.insertTanamanUiEvent
        val errorState = FormErrorState(
            namaTanaman = if (event.namaTanaman.isNotEmpty()) null else "Nama Tanaman tidak boleh kosong",
            periodeTanam = if (event.periodeTanam.isNotEmpty()) null else "Periode Tanam tidak boleh kosong",
            deskripsiTanaman = if (event.deskripsiTanaman.isNotEmpty()) null else "Deskripsi Tanaman tidak boleh kosong"
        )

        updateTanamanUiState = updateTanamanUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    suspend fun updateTanaman(){
        val currentEvent = updateTanamanUiState.insertTanamanUiEvent
        if (validateFields()){
            viewModelScope.launch {
                try {
                    tanamanRepository.updateTanaman(_idtnmn, currentEvent.toTanaman())
                }catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}