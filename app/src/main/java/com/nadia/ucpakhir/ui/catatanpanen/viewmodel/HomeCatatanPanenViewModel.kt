package com.nadia.ucpakhir.ui.catatanpanen.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.nadia.ucpakhir.model.CatatanPanen
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.repository.CatatanPanenRepository
import com.nadia.ucpakhir.repository.TanamanRepository
import kotlinx.coroutines.launch
import okio.IOException

sealed class HomePanenUiState{
    data class Success(val panen: List<CatatanPanen>): HomePanenUiState()
    object Error: HomePanenUiState()
    object Loading: HomePanenUiState()
}

class HomeCatatanPanenViewModel(
    private val pnn: CatatanPanenRepository,
    private val tnmn: TanamanRepository
): ViewModel() {
    var panenUiState: HomePanenUiState by mutableStateOf(HomePanenUiState.Loading)
        private set

    init {
        getPanen()
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

    fun getPanen() {
        viewModelScope.launch {
            panenUiState = HomePanenUiState.Loading
            panenUiState = try {
                HomePanenUiState.Success(pnn.getPanen().data)
            } catch (e: IOException) {
                HomePanenUiState.Error
            } catch (e: HttpException) {
                HomePanenUiState.Error
            }
        }
    }

    fun deletePanen(idPanen : Int) {
        viewModelScope.launch {
            try {
                pnn.deletePanen(idPanen)
            } catch (e: IOException){
                HomePanenUiState.Error
            } catch (e: HttpException){
                HomePanenUiState.Error
            }
        }
    }
}