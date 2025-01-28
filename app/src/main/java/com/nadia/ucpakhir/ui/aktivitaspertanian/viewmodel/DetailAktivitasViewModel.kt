package com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.AktivitasPertanianRepository
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiDetailAktivitas
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailAktivitasUiState {
    data class Success(val aktivitas: Aktivitas) : DetailAktivitasUiState()
    object Error : DetailAktivitasUiState()
    object Loading : DetailAktivitasUiState()
}

class DetailAktivitasViewModel (
    savedStateHandle: SavedStateHandle,
    private val aktivitasPertanianRepository: AktivitasPertanianRepository,
    private val tnmn: TanamanRepository,
    private val pkrj: PekerjaRepository
) : ViewModel(){
    var aktivitasDetailUiState: DetailAktivitasUiState by mutableStateOf(DetailAktivitasUiState.Loading)
        private set

    private val _idaktvts: Int = checkNotNull(savedStateHandle[DestinasiDetailAktivitas.IDaktvts])

    init {
        getAktivitasID()
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

    fun getAktivitasID() {
        viewModelScope.launch {
            aktivitasDetailUiState = DetailAktivitasUiState.Loading
            aktivitasDetailUiState = try {
                val aktivitas = aktivitasPertanianRepository.getAktivitasID(_idaktvts)
                DetailAktivitasUiState.Success(aktivitas)
            } catch (e: IOException) {
                DetailAktivitasUiState.Error
            } catch (e: HttpException) {
                DetailAktivitasUiState.Error
            }
        }
    }
}