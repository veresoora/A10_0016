package com.nadia.ucpakhir.ui.tanaman.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadia.ucpakhir.R
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import com.nadia.ucpakhir.ui.tanaman.viewmodel.DetailTanamanUiState
import com.nadia.ucpakhir.ui.tanaman.viewmodel.DetailTanamanViewModel

object DestinasiDetailTanaman : DestinasiNavigasi {
    override val route = "detail"
    override val titleRes = "Detail Tanaman"
    const val IDtnmn = "idTanaman"
    val routeWithArg = "$route/{$IDtnmn}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenTanaman(
    navigateBack: () -> Unit,
    navigateToEntryCatatanPanen: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: DetailTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    Scaffold(
        topBar = {
            CostumeTopAppBar(
                title = DestinasiDetailTanaman.titleRes,
                canNavigateBack = true,
                navigateUp = navigateBack,
                onRefresh = {
                    viewModel.getTanamanID()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateToEntryCatatanPanen,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(10.dp).padding(start = 30.dp),
                containerColor = Color(0xFF004D40)
            ){
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Image(
                        painter = painterResource(R.drawable.addnote),
                        contentDescription = "add",
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Tambah Catatan Panen",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }
        }
    ) { innerPadding ->
        DetailStatusTanaman(
            modifier = Modifier.padding(innerPadding),
            detailTanamanUiState = viewModel.tanamanDetailUiState,
            retryAction = { viewModel.getTanamanID() }
        )
    }
}

@Composable
fun DetailStatusTanaman(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    detailTanamanUiState: DetailTanamanUiState
) {
    when (detailTanamanUiState) {
        is DetailTanamanUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())

        is DetailTanamanUiState.Success -> {
            if (detailTanamanUiState.tanaman.idTanaman.toString().isEmpty()) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Data tidak ditemukan.")
                }
            } else {
                ItemDetailTnmn(
                    tanaman = detailTanamanUiState.tanaman,
                    modifier = modifier.fillMaxWidth()
                )
            }
        }

        is DetailTanamanUiState.Error -> OnError(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun ItemDetailTnmn(
    modifier: Modifier = Modifier,
    tanaman: Tanaman
) {
    Card (
        modifier = modifier.padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            ComponentDetailTnmn(judul = "ID Tanaman", isinya = tanaman.idTanaman.toString())
            ComponentDetailTnmn(judul = "Nama Tanaman", isinya = tanaman.namaTanaman)
            ComponentDetailTnmn(judul = "Periode Tanaman", isinya = tanaman.periodeTanam)
            ComponentDetailTnmn(judul = "Deskripsi Tanaman", isinya = tanaman.deskripsiTanaman)
        }
    }
}

@Composable
fun ComponentDetailTnmn(
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