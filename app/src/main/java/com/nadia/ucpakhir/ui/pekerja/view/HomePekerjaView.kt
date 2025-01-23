package com.nadia.ucpakhir.ui.pekerja.view

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
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import com.nadia.ucpakhir.ui.pekerja.viewmodel.HomePekerjaUiState
import com.nadia.ucpakhir.ui.pekerja.viewmodel.HomePekerjaViewModel

object DestinasiHomePekerja : DestinasiNavigasi {
    override val route = "home_pekerja"
    override val titleRes = "Daftar Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePekerjaScreen(
    navigateToltemEntry: () -> Unit,
    modifier: Modifier = Modifier,
    onDetailClick: (Int) -> Unit = {},
    onEditClick: (Int) -> Unit = {},
    oBack: () -> Unit = {},
    viewModel: HomePekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiHomePekerja.titleRes,
                canNavigateBack = true,
                navigateUp = oBack,
                scrollBehavior = scrollBehavior,
                onRefresh = {
                    viewModel.getPekerja()
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton (
                onClick = navigateToltemEntry,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(18.dp)
            ){
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Pekerja")
            }
        },
    ){ innerPadding ->
        HomePekerjaStatus(
            homePekerjaUiState = viewModel.pekerjaUiState,
            retryAction = { viewModel.getPekerja() },
            modifier = Modifier.padding(innerPadding),
            onDetailClick = onDetailClick,
            onDeleteClick = {
                viewModel.deletePekerja(it.idPekerja)
                viewModel.getPekerja()
            },
            onEditClick = onEditClick
        )
    }
}

@Composable
fun HomePekerjaStatus(
    homePekerjaUiState: HomePekerjaUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
    onDetailClick: (Int) -> Unit,
    onEditClick: (Int) -> Unit
){
    when (homePekerjaUiState){
        is HomePekerjaUiState.Loading -> OnLoading(modifier = modifier.fillMaxSize())
        is HomePekerjaUiState.Success ->
            if (homePekerjaUiState.pekerja.isEmpty()) {
                return Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "Tidak ada data Tanaman")
                }
            }else {
                PekerjaLayout(
                    pekerja = homePekerjaUiState.pekerja,
                    modifier = modifier.fillMaxWidth(),
                    onDetailClick = {
                        onDetailClick(it.idPekerja)
                    },
                    onDeleteClick = {
                        onDeleteClick(it)
                    },
                    onEditClick = {
                        onEditClick(it.idPekerja)
                    }
                )
            }
        is HomePekerjaUiState.Error -> OnErrorPekerja(retryAction, modifier = modifier.fillMaxSize())
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
fun OnErrorPekerja(
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


@Composable
fun PekerjaLayout(
    pekerja: List<Pekerja>,
    modifier: Modifier = Modifier,
    onDetailClick: (Pekerja) -> Unit,
    onDeleteClick: (Pekerja) -> Unit = {},
    onEditClick: (Pekerja) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(pekerja) { pkrj ->
            PekerjaCard(
                pekerja = pkrj,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onDetailClick(pkrj) },
                onDeleteClick = {
                    onDeleteClick(pkrj)
                },
                onEditClick = {
                    onEditClick(pkrj)
                }
            )
        }
    }
}

@Composable
fun PekerjaCard(
    pekerja: Pekerja,
    modifier: Modifier = Modifier,
    onDeleteClick: (Pekerja) -> Unit = {},
    onEditClick: (Pekerja) -> Unit = {}
) {
    var deleteConfirmation by rememberSaveable { mutableStateOf(false) }

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
                    Color(0xFF3E2723)
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
                        painter = painterResource(R.drawable.user),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = pekerja.namaPekerja,
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
                    IconButton(onClick = { onEditClick(pekerja) }) {
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
                        painter = painterResource(R.drawable.farmer),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = pekerja.jabatan,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    if (deleteConfirmation) {
        KonfirmasiDeletePekerja(
            onDeleteConfirm = {
                deleteConfirmation = false
                onDeleteClick(pekerja)
            },
            onDeleteCancel = {
                deleteConfirmation = false
            },
            modifier = Modifier.padding(8.dp)
        )
    }
}


@Composable
private fun KonfirmasiDeletePekerja (
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