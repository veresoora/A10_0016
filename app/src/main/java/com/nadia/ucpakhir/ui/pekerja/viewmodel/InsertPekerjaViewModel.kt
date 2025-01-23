package com.nadia.ucpakhir.ui.pekerja.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.repository.PekerjaRepository
import kotlinx.coroutines.launch

class InsertPekerjaViewModel(private val pkrj: PekerjaRepository): ViewModel(){
    var uiState by mutableStateOf(InsertPekerjaUiState())
        private set

    fun updateInsertPekerjaState(insertPekerjaUiEvent: InsertPekerjaUiEvent){
        uiState = InsertPekerjaUiState(insertPekerjaUiEvent = insertPekerjaUiEvent)
    }

    fun validateFields(): Boolean {
        val event = uiState.insertPekerjaUiEvent
        val errorState = FormErrorState(
            namaPekerja = if (event.namaPekerja.isNotEmpty()) null else "Nama Pekerja tidak boleh kosong",
            jabatan = if (event.jabatan.isNotEmpty()) null else "Jabatan tidak boleh kosong",
            kontakPekerja = if (event.kontakPekerja.isNotEmpty()) null else "Kontak Pekerja tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    suspend fun insertPkrj(){
        val currentEvent = uiState.insertPekerjaUiEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    pkrj.insertPekerja(currentEvent.toPekerja())
                    uiState = uiState.copy(
                        insertPekerjaUiEvent = InsertPekerjaUiEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}

data class InsertPekerjaUiState(
    val insertPekerjaUiEvent: InsertPekerjaUiEvent = InsertPekerjaUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

data class InsertPekerjaUiEvent(
    val idPekerja: Int = 0,
    val namaPekerja: String = "",
    val jabatan: String = "",
    val kontakPekerja: String = "",
)

fun InsertPekerjaUiEvent.toPekerja(): Pekerja = Pekerja(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)

fun Pekerja.toUiStatePekerja(): InsertPekerjaUiState = InsertPekerjaUiState(
    insertPekerjaUiEvent = toInsertPekerjaUiEvent()
)

fun Pekerja.toInsertPekerjaUiEvent():InsertPekerjaUiEvent = InsertPekerjaUiEvent(
    idPekerja = idPekerja,
    namaPekerja = namaPekerja,
    jabatan = jabatan,
    kontakPekerja = kontakPekerja
)

data class FormErrorState(
    val namaPekerja: String? = null,
    val jabatan: String? = null,
    val kontakPekerja: String? = null
){
    fun isValid(): Boolean {
        return namaPekerja == null
                && jabatan == null
                && kontakPekerja == null
    }
}