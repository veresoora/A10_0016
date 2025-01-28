package com.nadia.ucpakhir.ui.catatanpanen.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadia.ucpakhir.R
import com.nadia.ucpakhir.model.CatatanPanen
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.HomeCatatanPanenViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.HomePanenUiState
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

object DestinasiHomePanen : DestinasiNavigasi {
    override val route = "home_catatanpanen"
    override val titleRes = "Daftar Catatan Panen"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePanenScreen(
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    oBack: () -> Unit = {},
    viewModel: HomeCatatanPanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePanen.titleRes,
                canNavigateBack = true,
                navigateUp = oBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPanen()
                }
            )
        }
    ){ innerPadding ->
        HomePanenStatus (
            homePanenUiState = viewModel.panenUiState,
            retryAction = { viewModel.getPanen() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePanen(it.idPanen)
                viewModel.getPanen()
            },
            onEditClick = onEditClick,
            listTanaman = viewModel.listTanaman
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomePanenStatus(
    homePanenUiState: HomePanenUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (CatatanPanen) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    listTanaman: List<Tanaman>
){
    when (homePanenUiState){
        is HomePanenUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePanenUiState.Success ->
            if (homePanenUiState.panen.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Catatan Panen")
                }
            }else {
                PanenLayout (
                    catatanPanen = homePanenUiState.panen,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPanen)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it.idPanen)
                    },
                    listTanaman = listTanaman
                )
            }
        is HomePanenUiState.Error -> OnErrorPanen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun OnLoading(modifier: Modifier = Modifier){
    Image(
        modifier = modifier.size(200.dp),
        painter = painterResource(R.drawable.iconsloading),
        contentDescription = "loading"
    )
}

@Composable
fun OnErrorPanen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(
            painter = painterResource(id = R.drawable.iconserror),
            contentDescription = ""
        )
        Text(
            text = "Loading Failed",
            modifier = Modifier.padding(16.dp)
        )
        Button (
            onClick = retryAction
        ){
            Text("Retry")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PanenLayout(
    catatanPanen: List<CatatanPanen>,
    modifier: Modifier = Modifier,
    onDetailClick: (CatatanPanen) -> Unit,
    onDeleteClick: (CatatanPanen) -> Unit = {},
    onEditClick: (CatatanPanen) -> Unit,
    listTanaman: List<Tanaman>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(catatanPanen) { pnn ->
            PanenCard(
                catatanPanen = pnn,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pnn) },
                onDeleteClick = {
                    onDeleteClick(pnn)
                },
                onEditClick = {
                    onEditClick(pnn)
                },
                listTanaman = listTanaman
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PanenCard(
    catatanPanen: CatatanPanen,
    modifier: Modifier = Modifier,
    onDeleteClick: (CatatanPanen) -> Unit = {},
    onEditClick: (CatatanPanen) -> Unit = {},
    listTanaman: List<Tanaman>,
) {
    var deleteConfirmation by rememberSaveable { mutableStateOf(false) }

    val formattedDate = try {
        val zonedDateTime = ZonedDateTime.parse(catatanPanen.tanggal_panen, DateTimeFormatter.ISO_DATE_TIME)
        zonedDateTime.withZoneSameInstant(TimeZone.getDefault().toZoneId())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } catch (e: Exception) {
        "Invalid Date"
    }

    val tanamanMap = listTanaman.associate { it.idTanaman to it.namaTanaman }
    val namaTanaman = tanamanMap[catatanPanen.idTanaman] ?: "Tanaman Tidak Diketahui"

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .background(
                    Color(0xFF00796B)
                )
                .padding(16.dp)
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.plant),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = namaTanaman,
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.weight(1f))
                    IconButton(onClick = { deleteConfirmation = true }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete",
                            tint = Color(0xFFF44336)
                        )
                    }
                    IconButton(onClick = { onEditClick(catatanPanen) }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color.White.copy(alpha = 0.7f),
                    thickness = 1.dp
                )

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.weekend),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.weighing),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = catatanPanen.jumlah_panen,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(R.drawable.notes),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = catatanPanen.keterangan,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    if (deleteConfirmation) {
        KonfirmasiDeletePanen(
            onDeleteConfirm = {
                deleteConfirmation = false
                onDeleteClick(catatanPanen)
            },
            onDeleteCancel = {
                deleteConfirmation = false
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Composable
private fun KonfirmasiDeletePanen (
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(onDismissRequest = {},
        title = { Text("Hapus Data") },
        text = { Text("Apakah Anda benar-benar ingin menghapus data ini?") },
        modifier = modifier,
        dismissButton = {
            TextButton (onClick = onDeleteCancel) {
                Text(text = "Batal")
            }
        },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text(text = "Ya")
            }
        }
    )
}

