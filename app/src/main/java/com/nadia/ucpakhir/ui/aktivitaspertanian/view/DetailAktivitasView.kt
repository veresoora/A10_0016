package com.nadia.ucpakhir.ui.aktivitaspertanian.view

import android.os.Build
import androidx.annotation.RequiresApi
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
import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.DetailAktivitasUiState
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.DetailAktivitasViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DestinasiDetailAktivitas : DestinasiNavigasi {
    override val route = "detail_aktivitas"
    override val titleRes = "Detail Aktivitas"
    const val IDaktvts = "idAktivitas"
    val routeWithArg = "$route/{$IDaktvts}"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenAktivitas(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailAktivitas.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getAktivitasID()
                }
            )
        }
    ) { innerPadding ->
        DetailStatusAktivitas(
            modifier = Modifier.padding(innerPadding),
            detailAktivitasUiState = viewModel.aktivitasDetailUiState,
            retryAction = { viewModel.getAktivitasID() },
            listTanaman = viewModel.listTanaman,
            listPekerja = viewModel.listPekerja
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailStatusAktivitas(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailAktivitasUiState: DetailAktivitasUiState,
    listTanaman: List<Tanaman>,
    listPekerja: List<Pekerja>
) {
    when (detailAktivitasUiState) {
        is DetailAktivitasUiState.Loading -> com.nadia.ucpakhir.ui.aktivitaspertanian.view.OnLoading(modifier = modifier.fillMaxSize())

        is DetailAktivitasUiState.Success -> {
            if (detailAktivitasUiState.aktivitas.idAktivitas.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailAktvts(
                    aktivitas = detailAktivitasUiState.aktivitas,
                    modifier = modifier.fillMaxWidth(),
                    listTanaman = listTanaman,
                    listPekerja = listPekerja
                )
            }
        }

        is DetailAktivitasUiState.Error -> OnErrorAktivitas(retryAction, modifier = modifier.fillMaxSize())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDetailAktvts(
    modifier: Modifier = Modifier,
    aktivitas: Aktivitas,
    listTanaman: List<Tanaman>,
    listPekerja: List<Pekerja>
) {
    val tanamanMap = listTanaman.associate { it.idTanaman to it.namaTanaman }
    val namaTanaman = tanamanMap[aktivitas.idTanaman] ?: "Tanaman Tidak Diketahui"

    val pekerjaMap = listPekerja.associate { it.idPekerja to it.namaPekerja }
    val namaPekerja = pekerjaMap[aktivitas.idPekerja] ?:"Pekerja Tidak Diketahui"

    val formattedDate = try {
        // Parsing string ISO 8601 menjadi LocalDateTime
        val dateTime = LocalDateTime.parse(aktivitas.tanggalAktivitas, DateTimeFormatter.ISO_DATE_TIME)
        // Format menjadi hanya "yyyy-MM-dd"
        dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } catch (e: Exception) {
        "Invalid Date" // Tampilkan error jika parsing gagal
    }
    Card (
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailAktvts(judul = "ID Aktivitas", isinya = aktivitas.idAktivitas.toString())
            ComponentDetailAktvts(judul = "ID Tanaman", isinya = aktivitas.idTanaman.toString())
            ComponentDetailAktvts(judul = "Nama Tanaman", isinya = namaTanaman)
            ComponentDetailAktvts(judul = "ID Pekerja", isinya = aktivitas.idPekerja.toString())
            ComponentDetailAktvts(judul = "Nama Pekerja", isinya = namaPekerja)
            ComponentDetailAktvts(judul = "Tanggal Aktivitas", isinya = formattedDate)
            ComponentDetailAktvts(judul = "Deskripsi Aktivitas", isinya = aktivitas.deskripsiAktivitas)
        }
    }
}

@Composable
fun ComponentDetailAktvts(
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