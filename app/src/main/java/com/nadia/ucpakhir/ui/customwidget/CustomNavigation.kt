package com.nadia.ucpakhir.ui.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nadia.ucpakhir.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigation(
    modifier: Modifier = Modifier,
    oClickPekerja: () -> Unit = {},
    oClickAktivitasPertanian: () -> Unit = {},
    oClickCatatanPanen: () -> Unit = {},
){
    Box (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp)
            .background(
                color = Color(0xFF0D4735)
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(onClick = oClickPekerja) {
                    Image(
                        painter = painterResource(R.drawable.gardener),
                        contentDescription = ""
                    )
                }
                Text("Pekerja",
                    color = Color.White)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(onClick = oClickPekerja) {
                    Image(
                        painter = painterResource(R.drawable.agriculture),
                        contentDescription = ""
                    )
                }
                Text("Aktivitas",
                    color = Color.White)
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(onClick = oClickPekerja) {
                    Image(
                        painter = painterResource(R.drawable.notes),
                        contentDescription = ""
                    )
                }
                Text("Catatan",
                    color = Color.White)
            }
        }
    }
}