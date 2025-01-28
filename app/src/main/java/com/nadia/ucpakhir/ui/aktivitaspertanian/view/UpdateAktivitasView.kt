package com.nadia.ucpakhir.ui.aktivitaspertanian.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.UpdateAktivitasViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiAktivitasUpdate : DestinasiNavigasi {
    override val route = "update_aktivitas"
    override val titleRes = "Update Aktivitas"
    const val IDaktvts = "idAktivitas"
    val routeWithArg = "$route/{$IDaktvts}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenAktivitas(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdateAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiAktivitasUpdate.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = onBack,
            )
        }
    ){ padding ->
        EntryBodyAktivitas(
            modifier = Modifier.padding(padding),
            onAktivitasValueChange = viewModel::updateInsertAktivitasState,
            inserAktivitasUiState = viewModel.updateAktivitasUiState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()){
                        viewModel.updateAktivitas()
                        delay(600)
                        withContext(Dispatchers.Main){
                            onNavigate()
                        }
                    }
                }
            },
            tanamanList = viewModel.listTanaman,
            pekerjaList = viewModel.listPekerja
        )
    }
}