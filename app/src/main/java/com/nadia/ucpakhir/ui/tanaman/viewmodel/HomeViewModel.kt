package com.nadia.ucpakhir.ui.tanaman.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.TanamanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomeTanamanUiState{
    data class Success(val tanaman: List<Tanaman>): HomeTanamanUiState()
    object Error: HomeTanamanUiState()
    object Loading: HomeTanamanUiState()
}

class HomeTanamanViewModel(private val tnmn: TanamanRepository): ViewModel() {
    var tanamanUiState: HomeTanamanUiState by mutableStateOf(HomeTanamanUiState.Loading)
        private set

    init {
        getTanaman()
    }

    fun getTanaman() {
        viewModelScope.launch {
            tanamanUiState = HomeTanamanUiState.Loading
            tanamanUiState = try {
                HomeTanamanUiState.Success(tnmn.getTanaman().data)
            } catch (e: IOException) {
                HomeTanamanUiState.Error
            } catch (e: HttpException) {
                HomeTanamanUiState.Error
            }
        }
    }

    fun deleteTanaman(idTanaman : Int) {
        viewModelScope.launch {
            try {
                tnmn.deleteTanaman(idTanaman)
            } catch (e: IOException){
                HomeTanamanUiState.Error
            } catch (e: HttpException){
                HomeTanamanUiState.Error
            }
        }
    }
}