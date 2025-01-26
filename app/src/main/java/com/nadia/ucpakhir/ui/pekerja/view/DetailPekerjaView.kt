package com.nadia.ucpakhir.ui.pekerja.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import com.nadia.ucpakhir.ui.pekerja.viewmodel.DetailPekerjaUiState
import com.nadia.ucpakhir.ui.pekerja.viewmodel.DetailPekerjaViewModel

object DestinasiDetailPekerja : DestinasiNavigasi {
    override val route = "detail_pekerja"
    override val titleRes = "Detail Pekerja"
    const val IDpkrj = "idPekerja"
    val routeWithArg = "$route/{$IDpkrj}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenPekerja(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPekerja.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPekerjaID()
                }
            )
        }
    ) { innerPadding ->
        DetailStatusPekerja(
            modifier = Modifier.padding(innerPadding),
            detailPekerjaUiState = viewModel.pekerjaDetailUiState,
            retryAction = { viewModel.getPekerjaID() }
        )
    }
}

@Composable
fun DetailStatusPekerja(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPekerjaUiState: DetailPekerjaUiState
) {
    when (detailPekerjaUiState) {
        is DetailPekerjaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailPekerjaUiState.Success -> {
            if (detailPekerjaUiState.pekerja.idPekerja.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPkrj(
                    pekerja = detailPekerjaUiState.pekerja,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailPekerjaUiState.Error -> OnErrorPekerja(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailPkrj(
    modifier: Modifier = Modifier,
    pekerja: Pekerja
) {
    Card (
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailPkrj(judul = "ID Pekerja", isinya = pekerja.idPekerja.toString())
            ComponentDetailPkrj(judul = "Nama Pekerja", isinya = pekerja.namaPekerja)
            ComponentDetailPkrj(judul = "Jabatan", isinya = pekerja.jabatan)
            ComponentDetailPkrj(judul = "Kontak Pekerja", isinya = pekerja.kontakPekerja)
        }
    }
}

@Composable
fun ComponentDetailPkrj(
    modifier: Modifier = Modifier,
    judul: String,
    isinya: String,
){
    Column (
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "$judul : ",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004D40)
        )
        Text(
            text = isinya,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}