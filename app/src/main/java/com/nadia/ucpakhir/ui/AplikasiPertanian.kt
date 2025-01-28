package com.nadia.ucpakhir.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.nadia.ucpakhir.ui.navigation.PengelolaHalamanPertanian

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AplikasiPertanian(
    modifier: Modifier = Modifier
){
    Scaffold(){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            PengelolaHalamanPertanian()
        }
    }
}