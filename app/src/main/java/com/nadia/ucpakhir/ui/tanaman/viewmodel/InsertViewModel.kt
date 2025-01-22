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

    fun validateFields(): Boolean {
        val event = uiState.insertTanamanUiEvent
        val errorState = FormErrorState(
            namaTanaman = if (event.namaTanaman.isNotEmpty()) null else "Nama Tanaman tidak boleh kosong",
            periodeTanam = if (event.periodeTanam.isNotEmpty()) null else "Periode Tanam tidak boleh kosong",
            deskripsiTanaman = if (event.deskripsiTanaman.isNotEmpty()) null else "Deskripsi Tanaman tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertTnmn(){
        val currentEvent = uiState.insertTanamanUiEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    tnmn.insertTanaman(currentEvent.toTanaman())
                    uiState = uiState.copy(
                        insertTanamanUiEvent = InsertTanamanUiEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}

data class InsertTanamanUiState(
    val insertTanamanUiEvent: InsertTanamanUiEvent = InsertTanamanUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
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

data class FormErrorState(
    val namaTanaman: String? = null,
    val periodeTanam: String? = null,
    val deskripsiTanaman: String? = null
){
    fun isValid(): Boolean {
        return namaTanaman == null
                && periodeTanam == null
                && deskripsiTanaman == null
    }
}