package com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.AktivitasPertanianRepository
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeAktivitasUiState{
    data class Success(val aktivitas: List<Aktivitas>): HomeAktivitasUiState()
    object Error: HomeAktivitasUiState()
    object Loading: HomeAktivitasUiState()
}

class HomeAktivitasViewModel(
    private val aktvts: AktivitasPertanianRepository,
    private val tnmn: TanamanRepository,
    private val pkrj: PekerjaRepository
): ViewModel() {
    var aktivitasUiState: HomeAktivitasUiState by mutableStateOf(HomeAktivitasUiState.Loading)
        private set

    init {
        getAktivitas()
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

    fun getAktivitas() {
        viewModelScope.launch {
            aktivitasUiState = HomeAktivitasUiState.Loading
            aktivitasUiState = try {
                HomeAktivitasUiState.Success(aktvts.getAktivitas().data)
            } catch (e: IOException) {
                HomeAktivitasUiState.Error
            } catch (e: HttpException) {
                HomeAktivitasUiState.Error
            }
        }
    }

    fun deleteAktivitas(idAktivitas : Int) {
        viewModelScope.launch {
            try {
                aktvts.deleteAktivitas(idAktivitas)
            } catch (e: IOException){
                HomeAktivitasUiState.Error
            } catch (e: HttpException){
                HomeAktivitasUiState.Error
            }
        }
    }
}