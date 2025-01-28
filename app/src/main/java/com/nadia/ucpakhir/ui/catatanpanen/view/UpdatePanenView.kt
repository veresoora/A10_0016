package com.nadia.ucpakhir.ui.catatanpanen.view

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
import com.nadia.ucpakhir.ui.aktivitaspertanian.view.DestinasiAktivitasUpdate
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.UpdatePanenViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

object DestinasiPanenUpdate : DestinasiNavigasi {
    override val route = "update_panen"
    override val titleRes = "Update Catatan Panen"
    const val IDpnn = "idPanen"
    val routeWithArg = "$route/{$IDpnn}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdateScreenPanen(
    onBack: () -> Unit,
    onNavigate:()-> Unit,
    modifier: Modifier = Modifier,
    viewModel: UpdatePanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
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
        EntryBodyPanen(
            modifier = Modifier.padding(padding),
            onPanenValueChange = viewModel::updateInsertPanenState,
            insertPanenUiState = viewModel.updatePanenUiState,
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.updatePanen()
                    delay(600)
                    withContext(Dispatchers.Main){
                        onNavigate()
                    }
                }
            },
            tanamanList = viewModel.listTanaman
        )
    }
}