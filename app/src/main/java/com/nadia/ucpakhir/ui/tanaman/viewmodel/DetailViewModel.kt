package com.nadia.ucpakhir.ui.tanaman.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.TanamanRepository
import com.nadia.ucpakhir.ui.tanaman.view.DestinasiDetailTanaman
import kotlinx.coroutines.launch
import okio.IOException

sealed class DetailTanamanUiState {
    data class Success(val tanaman: Tanaman) : DetailTanamanUiState()
    object Error : DetailTanamanUiState()
    object Loading : DetailTanamanUiState()
}

class DetailTanamanViewModel (
    savedStateHandle: SavedStateHandle,
    private val tanamanRepository: TanamanRepository
) : ViewModel(){
    var tanamanDetailUiState: DetailTanamanUiState by mutableStateOf(DetailTanamanUiState.Loading)
        private set

    private val _idtnmn: Int = checkNotNull(savedStateHandle[DestinasiDetailTanaman.IDtnmn])

    init {
        getTanamanID()
    }

    fun getTanamanID() {
        viewModelScope.launch {
            tanamanDetailUiState = DetailTanamanUiState.Loading
            tanamanDetailUiState = try {
                val tanaman = tanamanRepository.getTanamanID(_idtnmn)
                DetailTanamanUiState.Success(tanaman)
            } catch (e: IOException) {
                DetailTanamanUiState.Error
            } catch (e: HttpException) {
                DetailTanamanUiState.Error
            }
        }
    }
}