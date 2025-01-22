package com.nadia.ucpakhir.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nadia.ucpakhir.PertanianApp
import com.nadia.ucpakhir.ui.tanaman.viewmodel.HomeTanamanViewModel
import com.nadia.ucpakhir.ui.tanaman.viewmodel.InsertTanamanViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeTanamanViewModel(pertanianApp().container.tanamanRepository)
        }
        initializer {
            InsertTanamanViewModel(pertanianApp().container.tanamanRepository)
        }
    }
}
fun CreationExtras.pertanianApp(): PertanianApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApp)