package com.nadia.ucpakhir.ui.catatanpanen.view

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
import com.nadia.ucpakhir.model.CatatanPanen
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.OnErrorAktivitas
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.DetailAktivitasUiState
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.DetailAktivitasViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.DetailPanenUiState
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.DetailPanenViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DestinasiDetailPanen : DestinasiNavigasi {
    override val route = "detail_catatanpanen"
    override val titleRes = "Detail Catatan Panen"
    const val IDpnn = "idPanen"
    val routeWithArg = "$route/{$IDpnn}"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenPanen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailPanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailPanen.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getPanenID()
                }
            )
        }
    ) { innerPadding ->
        DetailStatusPanen(
            modifier = Modifier.padding(innerPadding),
            detailPanenUiState = viewModel.panenDetailUiState,
            retryAction = { viewModel.getPanenID() },
            listTanaman = viewModel.listTanaman
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailStatusPanen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailPanenUiState: DetailPanenUiState,
    listTanaman: List<Tanaman>
) {
    when (detailPanenUiState) {
        is DetailPanenUiState.Loading -> com.nadia.ucpakhir.ui.catatanpanen.view.OnLoading(modifier = modifier.fillMaxSize())

        is DetailPanenUiState.Success -> {
            if (detailPanenUiState.catatanPanen.idPanen.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailPnn(
                    catatanPanen = detailPanenUiState.catatanPanen,
                    modifier = modifier.fillMaxWidth(),
                    listTanaman = listTanaman
                )
            }
        }

        is DetailPanenUiState.Error -> OnErrorPanen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ItemDetailPnn(
    modifier: Modifier = Modifier,
    catatanPanen: CatatanPanen,
    listTanaman: List<Tanaman>
) {
    val tanamanMap = listTanaman.associate { it.idTanaman to it.namaTanaman }
    val namaTanaman = tanamanMap[catatanPanen.idTanaman] ?: "Tanaman Tidak Diketahui"

    val formattedDate = try {
        // Parsing string ISO 8601 menjadi LocalDateTime
        val dateTime = LocalDateTime.parse(catatanPanen.tanggal_panen, DateTimeFormatter.ISO_DATE_TIME)
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
            ComponentDetailPnn(judul = "ID Panen", isinya = catatanPanen.idPanen.toString())
            ComponentDetailPnn(judul = "ID Tanaman", isinya = catatanPanen.idTanaman.toString())
            ComponentDetailPnn(judul = "Nama Tanaman", isinya = namaTanaman)
            ComponentDetailPnn(judul = "Tanggal Panen", isinya = formattedDate)
            ComponentDetailPnn(judul = "Jumlah Panen", isinya = catatanPanen.jumlah_panen)
            ComponentDetailPnn(judul = "Keterangan",  isinya = catatanPanen.keterangan)
        }
    }
}

@Composable
fun ComponentDetailPnn(
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