package com.nadia.ucpakhir.ui.catatanpanen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.CatatanPanen
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.AktivitasPertanianRepository
import com.nadia.ucpakhir.repository.CatatanPanenRepository
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiDetailAktivitas
import com.nadia.ucpakhir.ui.catatanpanen.view.DestinasiDetailPanen
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPanenUiState {
    data class Success(val catatanPanen: CatatanPanen) : DetailPanenUiState()
    object Error : DetailPanenUiState()
    object Loading : DetailPanenUiState()
}

class DetailPanenViewModel (
    savedStateHandle: SavedStateHandle,
    private val catatanPanenRepository: CatatanPanenRepository,
    private val tnmn: TanamanRepository
) : ViewModel(){
    var panenDetailUiState: DetailPanenUiState by mutableStateOf(DetailPanenUiState.Loading)
        private set

    private val _idpnn: Int = checkNotNull(savedStateHandle[DestinasiDetailPanen.IDpnn])

    init {
        getPanenID()
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

    fun getPanenID() {
        viewModelScope.launch {
            panenDetailUiState = DetailPanenUiState.Loading
            panenDetailUiState = try {
                val catatanpanen = catatanPanenRepository.getPanensID(_idpnn)
                DetailPanenUiState.Success(catatanpanen)
            } catch (e: IOException) {
                DetailPanenUiState.Error
            } catch (e: HttpException) {
                DetailPanenUiState.Error
            }
        }
    }
}