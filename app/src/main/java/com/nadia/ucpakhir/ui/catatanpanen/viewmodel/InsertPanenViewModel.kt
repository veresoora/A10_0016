package com.nadia.ucpakhir.ui.catatanpanen.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nadia.ucpakhir.model.CatatanPanen
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.CatatanPanenRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

class InsertPanenViewModel(
    private val pnn: CatatanPanenRepository,
    private val tnmn: TanamanRepository
): ViewModel(){
    var uiState by mutableStateOf(InsertPanenUiState())
        private set

    init {
        viewModelScope.launch {
            tanaman()
        }
    }

    var listTanaman by mutableStateOf<List<Tanaman>> (listOf())
        private set

    suspend fun tanaman() {
        listTanaman = tnmn.getTanamanAll()
        listTanaman.forEach { id ->
            println("Tanaman: ${id.idTanaman}")
        }
    }


    fun updateInsertPanenState(insertPanenUiEvent: InsertPanenUiEvent){
        uiState = InsertPanenUiState(insertPanenUiEvent = insertPanenUiEvent)
    }

    fun validateFields(): Boolean {
        val event = uiState.insertPanenUiEvent
        val errorState = FormErrorState(
            idTanaman = if (event.idTanaman > 0) null else "Nama Tanaman tidak boleh kosong",
            tanggalPanen = if (event.tanggalPanen.isNotEmpty()) null else "Tanggal Panen tidak boleh kosong",
            jumlahPanen = if (event.jumlahPanen.isNotEmpty()) null else "Jumlah Panen tidak boleh kosong",
            keterangan = if (event.keterangan.isNotEmpty()) null else "Keterangan tidak boleh kosong"
        )

        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insertPnn(){
        val currentEvent = uiState.insertPanenUiEvent

        if (validateFields()){
            viewModelScope.launch {
                try {
                    val zonedDateTime = ZonedDateTime.now(TimeZone.getDefault().toZoneId())
                    val formattedDate = zonedDateTime.format(DateTimeFormatter.ISO_DATE_TIME)

                    val updatedEvent = currentEvent.copy(tanggalPanen = formattedDate)

                    pnn.insertPanen(updatedEvent.toPanen())
                    uiState = uiState.copy(
                        insertPanenUiEvent = InsertPanenUiEvent(),
                        isEntryValid = FormErrorState()
                    )
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}

data class InsertPanenUiState(
    val insertPanenUiEvent: InsertPanenUiEvent = InsertPanenUiEvent(),
    val isEntryValid: FormErrorState = FormErrorState()
)

data class InsertPanenUiEvent(
    val idPanen: Int = 0,
    val idTanaman: Int = 0,
    val tanggalPanen: String = "",
    val jumlahPanen: String = "",
    val keterangan: String = "",
)

fun InsertPanenUiEvent.toPanen(): CatatanPanen = CatatanPanen(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggal_panen = tanggalPanen,
    jumlah_panen = jumlahPanen,
    keterangan = keterangan
)

fun CatatanPanen.toUiStatePanen(): InsertPanenUiState = InsertPanenUiState(
    insertPanenUiEvent = toInsertPanenUiEvent()
)

fun CatatanPanen.toInsertPanenUiEvent(): InsertPanenUiEvent = InsertPanenUiEvent(
    idPanen = idPanen,
    idTanaman = idTanaman,
    tanggalPanen = tanggal_panen,
    jumlahPanen = jumlah_panen,
    keterangan = keterangan

)

data class FormErrorState(
    val idPanen: String? = null,
    val idTanaman: String? = null,
    val tanggalPanen: String? = null,
    val jumlahPanen: String? = null,
    val keterangan: String? = null
){
    fun isValid(): Boolean {
        return idPanen == null
                && idTanaman == null
                && tanggalPanen == null
                && jumlahPanen == null
                && keterangan == null
    }
}