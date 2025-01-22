package com.nadia.ucpakhir.ui.tanaman.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.TanamanRepository
import kotlinx.coroutines.launch

class InsertTanamanViewModel(private val tnmn:TanamanRepository): ViewModel(){
    var uiState by mutableStateOf(InsertTanamanUiState())
        private set

    fun updateInsertTanamanState(insertTanamanUiEvent: InsertTanamanUiEvent){
        uiState = InsertTanamanUiState(insertTanamanUiEvent = insertTanamanUiEvent)
    }

    suspend fun insertTnmn(){
        viewModelScope.launch {
            try {
                tnmn.insertTanaman(uiState.insertTanamanUiEvent.toTanaman())
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }
}

data class InsertTanamanUiState(
    val insertTanamanUiEvent: InsertTanamanUiEvent = InsertTanamanUiEvent()
)

data class InsertTanamanUiEvent(
    val idTanaman: Int = 0,
    val namaTanaman: String = "",
    val periodeTanam: String = "",
    val deskripsiTanaman: String = "",
)

fun InsertTanamanUiEvent.toTanaman(): Tanaman = Tanaman(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)

fun Tanaman.toUiStateTnmn(): InsertTanamanUiState = InsertTanamanUiState(
    insertTanamanUiEvent = toInsertTanamanUiEvent()
)

fun Tanaman.toInsertTanamanUiEvent(): InsertTanamanUiEvent = InsertTanamanUiEvent(
    idTanaman = idTanaman,
    namaTanaman = namaTanaman,
    periodeTanam = periodeTanam,
    deskripsiTanaman = deskripsiTanaman
)