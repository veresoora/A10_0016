package com.nadia.ucpakhir

import android.app.Application
import com.nadia.ucpakhir.di.AktivitasPertanianAppContainer
import com.nadia.ucpakhir.di.AktivitasPertanianContainer
import com.nadia.ucpakhir.di.CatatanPanenAppContainer
import com.nadia.ucpakhir.di.CatatanPanenContainer
import com.nadia.ucpakhir.di.PekerjaAppContainer
import com.nadia.ucpakhir.di.PekerjaContainer
import com.nadia.ucpakhir.di.TanamanAppContainer
import com.nadia.ucpakhir.di.TanamanContainer

class PertanianApp: Application() {
    lateinit var container: TanamanAppContainer
    lateinit var containerPekerja: PekerjaAppContainer
    lateinit var containerAktivitasPertanian: AktivitasPertanianAppContainer
    lateinit var containerCatatanPanen: CatatanPanenAppContainer
    override fun onCreate() {
        super.onCreate()
        container=TanamanContainer()
        containerPekerja=PekerjaContainer()
        containerAktivitasPertanian=AktivitasPertanianContainer()
        containerCatatanPanen=CatatanPanenContainer()
    }
}