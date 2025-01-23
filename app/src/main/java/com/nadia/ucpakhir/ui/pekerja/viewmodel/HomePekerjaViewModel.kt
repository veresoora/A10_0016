package com.nadia.ucpakhir.ui.pekerja.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.repository.PekerjaRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePekerjaUiState{
    data class Success(val pekerja: List<Pekerja>): HomePekerjaUiState()
    object Error: HomePekerjaUiState()
    object Loading: HomePekerjaUiState()
}

class HomePekerjaViewModel(private val pkrj: PekerjaRepository): ViewModel() {
    var pekerjaUiState: HomePekerjaUiState by mutableStateOf(HomePekerjaUiState.Loading)
        private set

    init {
        getPekerja()
    }

    fun getPekerja() {
        viewModelScope.launch {
            pekerjaUiState = HomePekerjaUiState.Loading
            pekerjaUiState = try {
                HomePekerjaUiState.Success(pkrj.getPekerja().data)
            } catch (e: IOException) {
                HomePekerjaUiState.Error
            } catch (e: HttpException) {
                HomePekerjaUiState.Error
            }
        }
    }

    fun deletePekerja(idPekerja : Int) {
        viewModelScope.launch {
            try {
                pkrj.deletePekerja(idPekerja)
            } catch (e: IOException){
                HomePekerjaUiState.Error
            } catch (e: HttpException){
                HomePekerjaUiState.Error
            }
        }
    }
}