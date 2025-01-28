package com.nadia.ucpakhir.ui.aktivitaspertanian.view

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadia.ucpakhir.R
import com.nadia.ucpakhir.model.Aktivitas
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.HomeAktivitasUiState
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.HomeAktivitasViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import com.nadia.ucpakhir.ui.pekerja.viewmodel.HomePekerjaUiState
import com.nadia.ucpakhir.ui.pekerja.viewmodel.HomePekerjaViewModel
import java.time.LocalDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.TimeZone

object DestinasiHomeAktivitas : DestinasiNavigasi {
    override val route = "home_aktivitas"
    override val titleRes = "Daftar Aktivitas"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAktivitasScreen(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    oBack: () -> Unit = {},
    viewModel: HomeAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomeAktivitas.titleRes,
                canNavigateBack = true,
                navigateUp = oBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getAktivitas()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateToltemEntry,
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
                        painter = painterResource(R.drawable.add),
                        contentDescription = "add",
                        modifier = Modifier.size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Tambah Aktivitas",
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                }
            }
        },
    ){ innerPadding ->
        HomeAktivitasStatus (
            homeAktivitasUiState = viewModel.aktivitasUiState,
            retryAction = { viewModel.getAktivitas() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deleteAktivitas(it.idAktivitas)
                viewModel.getAktivitas()
            },
            onEditClick = onEditClick,
            listTanaman = viewModel.listTanaman,
            listPekerja = viewModel.listPekerja
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeAktivitasStatus(
    homeAktivitasUiState: HomeAktivitasUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Aktivitas) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit,
    listTanaman: List<Tanaman>,
    listPekerja: List<Pekerja>
){
    when (homeAktivitasUiState){
        is HomeAktivitasUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomeAktivitasUiState.Success ->
            if (homeAktivitasUiState.aktivitas.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Aktivitas Pertanian")
                }
            }else {
                AktivitasLayout (
                    aktivitas = homeAktivitasUiState.aktivitas,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idAktivitas)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it.idAktivitas)
                    },
                    listTanaman = listTanaman,
                    listPekerja = listPekerja
                )
            }
        is HomeAktivitasUiState.Error -> OnErrorAktivitas(retryAction, modifier = modifier.fillMaxSize())
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
fun OnErrorAktivitas(
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
fun AktivitasLayout(
    aktivitas: List<Aktivitas>,
    modifier: Modifier = Modifier,
    onDetailClick: (Aktivitas) -> Unit,
    onDeleteClick: (Aktivitas) -> Unit = {},
    onEditClick: (Aktivitas) -> Unit,
    listTanaman: List<Tanaman>,
    listPekerja: List<Pekerja>
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(aktivitas) { aktvts ->
            AktivitasCard(
                aktivitas = aktvts,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(aktvts) },
                onDeleteClick = {
                    onDeleteClick(aktvts)
                },
                onEditClick = {
                    onEditClick(aktvts)
                },
                listTanaman = listTanaman,
                listPekerja = listPekerja
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AktivitasCard(
    aktivitas: Aktivitas,
    modifier: Modifier = Modifier,
    onDeleteClick: (Aktivitas) -> Unit = {},
    onEditClick: (Aktivitas) -> Unit = {},
    listTanaman: List<Tanaman>,
    listPekerja: List<Pekerja>
) {
    var deleteConfirmation by rememberSaveable { mutableStateOf(false) }

    val formattedDate = try {
        val zonedDateTime = ZonedDateTime.parse(aktivitas.tanggalAktivitas, DateTimeFormatter.ISO_DATE_TIME)
        zonedDateTime.withZoneSameInstant(TimeZone.getDefault().toZoneId())
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    } catch (e: Exception) {
        "Invalid Date"
    }

    val tanamanMap = listTanaman.associate { it.idTanaman to it.namaTanaman }
    val namaTanaman = tanamanMap[aktivitas.idTanaman] ?: "Tanaman Tidak Diketahui"

    val pekerjaMap = listPekerja.associate { it.idPekerja to it.namaPekerja }
    val namaPekerja = pekerjaMap[aktivitas.idPekerja] ?:"Pekerja Tidak Diketahui"



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
                    IconButton(onClick = { onEditClick(aktivitas) }) {
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
                        painter = painterResource(R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = namaPekerja,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

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
                        painter = painterResource(R.drawable.activity),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = aktivitas.deskripsiAktivitas,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    if (deleteConfirmation) {
        KonfirmasiDeleteAktivitas(
            onDeleteConfirm = {
                deleteConfirmation = false
                onDeleteClick(aktivitas)
            },
            onDeleteCancel = {
                deleteConfirmation = false
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Composable
private fun KonfirmasiDeleteAktivitas (
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

