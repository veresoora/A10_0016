package com.nadia.ucpakhir.ui.catatanpanen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.CatatanPanenRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import com.nadia.ucpakhir.ui.catatanpanen.view.DestinasiPanenUpdate
import kotlinx.coroutines.launch

class UpdatePanenViewModel(
    savedStateHandle: SavedStateHandle,
    private val pnn: CatatanPanenRepository,
    private val tnmn: TanamanRepository,
) : ViewModel() {
    var updatePanenUiState by mutableStateOf(InsertPanenUiState())
        private set
    var listTanaman by mutableStateOf<List<Tanaman>> (listOf())
        private set

    private val _idpnn: Int = checkNotNull(savedStateHandle[DestinasiPanenUpdate.IDpnn])
    init {
        viewModelScope.launch {
            val panen = pnn.getPanensID(_idpnn)
            updatePanenUiState = panen.toUiStatePanen()
            tanaman()

            updatePanenUiState = updatePanenUiState.copy(
                insertPanenUiEvent = updatePanenUiState.insertPanenUiEvent.copy(
                    idTanaman = listTanaman.find { it.idTanaman == panen.idTanaman }?.idTanaman ?: 0,
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

    fun updateInsertPanenState(insertPanenUiEvent: InsertPanenUiEvent){
        updatePanenUiState = InsertPanenUiState(insertPanenUiEvent = insertPanenUiEvent)
    }
    suspend fun updatePanen(){
        viewModelScope.launch {
            try {
                pnn.updatePanen(_idpnn, updatePanenUiState.insertPanenUiEvent.toPanen())
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}