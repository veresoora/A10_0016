package com.nadia.ucpakhir.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.nadia.ucpakhir.PertanianApp
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.DetailAktivitasViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.HomeAktivitasViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.InsertAktivitasViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.UpdateAktivitasViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.DetailPanenViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.HomeCatatanPanenViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.InsertPanenViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.UpdatePanenViewModel
import com.nadia.ucpakhir.ui.pekerja.viewmodel.DetailPekerjaViewModel
import com.nadia.ucpakhir.ui.pekerja.viewmodel.HomePekerjaViewModel
import com.nadia.ucpakhir.ui.pekerja.viewmodel.InsertPekerjaViewModel
import com.nadia.ucpakhir.ui.pekerja.viewmodel.UpdatePekerjaViewModel
import com.nadia.ucpakhir.ui.tanaman.viewmodel.DetailTanamanViewModel
import com.nadia.ucpakhir.ui.tanaman.viewmodel.HomeTanamanViewModel
import com.nadia.ucpakhir.ui.tanaman.viewmodel.InsertTanamanViewModel
import com.nadia.ucpakhir.ui.tanaman.viewmodel.UpdateTanamanViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            HomeTanamanViewModel(pertanianApp().container.tanamanRepository)
        }
        initializer {
            InsertTanamanViewModel(pertanianApp().container.tanamanRepository)
        }
        initializer {
            DetailTanamanViewModel(
                createSavedStateHandle(),
                pertanianApp().container.tanamanRepository
            )
        }
        initializer {
            UpdateTanamanViewModel(
                createSavedStateHandle(),
                pertanianApp().container.tanamanRepository
            )
        }

        initializer {
            HomePekerjaViewModel(pertanianApp().containerPekerja.pekerjaRepository)
        }
        initializer {
            InsertPekerjaViewModel(pertanianApp().containerPekerja.pekerjaRepository)
        }
        initializer {
            DetailPekerjaViewModel(
                createSavedStateHandle(),
                pertanianApp().containerPekerja.pekerjaRepository
            )
        }
        initializer {
            UpdatePekerjaViewModel(
                createSavedStateHandle(),
                pertanianApp().containerPekerja.pekerjaRepository
            )
        }

        initializer {
            HomeAktivitasViewModel(
                pertanianApp().containerAktivitasPertanian.aktivitasPertanianRepository,
                pertanianApp().container.tanamanRepository,
                pertanianApp().containerPekerja.pekerjaRepository
            )
        }
        initializer {
            InsertAktivitasViewModel(
                pertanianApp().containerAktivitasPertanian.aktivitasPertanianRepository,
                pertanianApp().container.tanamanRepository,
                pertanianApp().containerPekerja.pekerjaRepository
            )
        }
        initializer {
            DetailAktivitasViewModel(
                createSavedStateHandle(),
                pertanianApp().containerAktivitasPertanian.aktivitasPertanianRepository,
                pertanianApp().container.tanamanRepository,
                pertanianApp().containerPekerja.pekerjaRepository
            )
        }
        initializer {
            UpdateAktivitasViewModel(
                createSavedStateHandle(),
                pertanianApp().containerAktivitasPertanian.aktivitasPertanianRepository,
                pertanianApp().container.tanamanRepository,
                pertanianApp().containerPekerja.pekerjaRepository
            )
        }

        initializer {
            HomeCatatanPanenViewModel(
                pertanianApp().containerCatatanPanen.catatanPanenRepository,
                pertanianApp().container.tanamanRepository
                )
        }
        initializer {
            InsertPanenViewModel(
                pertanianApp().containerCatatanPanen.catatanPanenRepository,
                pertanianApp().container.tanamanRepository
            )
        }

        initializer {
            DetailPanenViewModel(
                createSavedStateHandle(),
                pertanianApp().containerCatatanPanen.catatanPanenRepository,
                pertanianApp().container.tanamanRepository,
            )
        }

        initializer {
            UpdatePanenViewModel(
                createSavedStateHandle(),
                pertanianApp().containerCatatanPanen.catatanPanenRepository,
                pertanianApp().container.tanamanRepository,
            )
        }
    }
}
fun CreationExtras.pertanianApp(): PertanianApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as PertanianApp)