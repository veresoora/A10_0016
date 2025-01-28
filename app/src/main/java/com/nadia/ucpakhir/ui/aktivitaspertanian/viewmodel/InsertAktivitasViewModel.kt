package com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.AktivitasPertanianRepository
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

class InsertAktivitasViewModel(
    private val aktvts: AktivitasPertanianRepository,
    private val tnmn: TanamanRepository,
    private val pkrj: PekerjaRepository
): ViewModel(){
    var uiState by mutableStateOf(InserAktivitasUiState())
        private set

    init {
        viewModelScope.launch {
            tanaman()
            pekerja()
        }
    }

    var listTanaman by mutableStateOf<List<Tanaman>> (listOf())
        private set

    var listPekerja by mutableStateOf<List<Pekerja>>(listOf())
        private set

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
        uiState = InserAktivitasUiState(insertAktivitasUiEvent = insertAktivitasUiEvent)
    }

    fun validateFields(): Boolean {
        val event = uiState.insertAktivitasUiEvent
        val errorState = FormErrorState(
            idTanaman = if (event.idTanaman > 0) null else "Nama Tanaman tidak boleh kosong",
            idPekerja = if (event.idPekerja > 0) null else "Nama Pekerja tidak boleh kosong",
            tanggalAktivitas = if (event.tanggalAktivitas.isNotEmpty()) null else "Tanggal Aktivitas tidak boleh kosong",
            deskripsiAktivitas = if (event.deskripsiAktivitas.isNotEmpty()) null else "Deskripsi Aktivitas tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insertAktvts(){
        val currentEvent = uiState.insertAktivitasUiEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    val zonedDateTime = ZonedDateTime.now(TimeZone.getDefault().toZoneId())
                    val formattedDate = zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME)

                    val updatedEvent = currentEvent.copy(tanggalAktivitas = formattedDate)
                    aktvts.insertAktivitas(updatedEvent.toAktivitas())
                    uiState = uiState.copy(
                        insertAktivitasUiEvent = InsertAktivitasUiEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}

data class InserAktivitasUiState(
    val insertAktivitasUiEvent: InsertAktivitasUiEvent = InsertAktivitasUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

data class InsertAktivitasUiEvent(
    val idAktivitas: Int = 0,
    val idTanaman: Int = 0,
    val idPekerja: Int = 0,
    val tanggalAktivitas: String = "",
    val deskripsiAktivitas: String = "",
)

fun InsertAktivitasUiEvent.toAktivitas(): Aktivitas = Aktivitas(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas
)

fun Aktivitas.toUiStateAktivitas(): InserAktivitasUiState = InserAktivitasUiState(
    insertAktivitasUiEvent = toInsertAktivitasUiEvent()
)

fun Aktivitas.toInsertAktivitasUiEvent():InsertAktivitasUiEvent = InsertAktivitasUiEvent(
    idAktivitas = idAktivitas,
    idTanaman = idTanaman,
    idPekerja = idPekerja,
    tanggalAktivitas = tanggalAktivitas,
    deskripsiAktivitas = deskripsiAktivitas

)

data class FormErrorState(
    val idTanaman: String? = null,
    val idPekerja: String? = null,
    val tanggalAktivitas: String? = null,
    val deskripsiAktivitas: String? = null
){
    fun isValid(): Boolean {
        return idTanaman == null
                && idPekerja == null
                && tanggalAktivitas == null
                && deskripsiAktivitas == null
    }
}