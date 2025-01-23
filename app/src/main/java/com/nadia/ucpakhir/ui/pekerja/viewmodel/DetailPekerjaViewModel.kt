package com.nadia.ucpakhir.ui.pekerja.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.repository.PekerjaRepository
import com.nadia.ucpakhir.ui.pekerja.view.DestinasiDetailPekerja
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailPekerjaUiState {
    data class Success(val pekerja: Pekerja) : DetailPekerjaUiState()
    object Error : DetailPekerjaUiState()
    object Loading : DetailPekerjaUiState()
}

class DetailPekerjaViewModel (
    savedStateHandle: SavedStateHandle,
    private val pekerjaRepository: PekerjaRepository
) : ViewModel(){
    var pekerjaDetailUiState: DetailPekerjaUiState by mutableStateOf(DetailPekerjaUiState.Loading)
        private set

    private val _idpkrj: Int = checkNotNull(savedStateHandle[DestinasiDetailPekerja.IDpkrj])

    init {
        getPekerjaID()
    }

    fun getPekerjaID() {
        viewModelScope.launch {
            pekerjaDetailUiState = DetailPekerjaUiState.Loading
            pekerjaDetailUiState = try {
                val pekerja = pekerjaRepository.getPekerjaID(_idpkrj)
                DetailPekerjaUiState.Success(pekerja)
            } catch (e: IOException) {
                DetailPekerjaUiState.Error
            } catch (e: HttpException) {
                DetailPekerjaUiState.Error
            }
        }
    }
}