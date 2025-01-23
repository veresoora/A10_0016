package com.nadia.ucpakhir

import android.app.Application
import com.nadia.ucpakhir.di.PekerjaAppContainer
import com.nadia.ucpakhir.di.PekerjaContainer
import com.nadia.ucpakhir.di.TanamanAppContainer
import com.nadia.ucpakhir.di.TanamanContainer

class PertanianApp: Application() {
    lateinit var container: TanamanAppContainer
    lateinit var containerPekerja: PekerjaAppContainer
    override fun onCreate() {
        super.onCreate()
        container=TanamanContainer()
        containerPekerja=PekerjaContainer()
    }
}