package com.nadia.ucpakhir.ui.customwidget

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nadia.ucpakhir.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomNavigation(
    modifier: Modifier = Modifier,
    oClickPekerja: () -> Unit = {},
    oClickAktivitasPertanian: () -> Unit = {},
    oClickCatatanPanen: () -> Unit = {},
){
    Card (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 16.dp),
    ) {
        Row (modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "logo",
                modifier = Modifier.size(120.dp)
                    .clip(shape = RoundedCornerShape(10.dp))
            )
            Column {
                Text(
                    text = "Welcome to Agro!",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 20.sp
                )
                Text(
                    text = "Solusi pertanian yang lebih pintar untuk hasil yang lebih maksimal."
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            thickness = 3.dp,
            color = Color(0xFF14B7A5)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Card (
                colors = CardDefaults.cardColors(containerColor = Color(0xFF004D40)),
                onClick = oClickPekerja
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.gardener),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                    Text("Pekerja",
                        color = Color.White)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF004D40)),
                onClick = oClickAktivitasPertanian
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(R.drawable.agriculture),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                    Text("Aktivitas",
                        color = Color.White)
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF004D40)),
                onClick = oClickCatatanPanen
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(R.drawable.notes),
                        contentDescription = "",
                        modifier = Modifier.size(40.dp)
                    )
                    Text("Catatan",
                        color = Color.White)
                }
            }
        }
    }
}