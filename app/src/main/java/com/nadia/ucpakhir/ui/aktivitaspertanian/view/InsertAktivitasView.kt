package com.nadia.ucpakhir.ui.aktivitaspertanian.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadia.ucpakhir.model.Pekerja
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.InsertAktivitasUiEvent
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.FormErrorState
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.InserAktivitasUiState
import com.nadia.ucpakhir.ui.aktivitaspertanian.viewmodel.InsertAktivitasViewModel
import kotlinx.coroutines.launch


object DestinasiEntryAktivitas : DestinasiNavigasi {
    override val route = "entry_aktivitas"
    override val titleRes = "Tambah Aktivitas"
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryAktvtsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertAktivitasViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryAktivitas.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyAktivitas(
            inserAktivitasUiState = viewModel.uiState,
            onAktivitasValueChange = viewModel::updateInsertAktivitasState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()){
                        viewModel.insertAktvts()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            tanamanList = viewModel.listTanaman,
            pekerjaList = viewModel.listPekerja
        )
    }
}

@Composable
fun EntryBodyAktivitas(
    inserAktivitasUiState: InserAktivitasUiState,
    onAktivitasValueChange: (InsertAktivitasUiEvent) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier,
    tanamanList: List<Tanaman>,
    pekerjaList: List<Pekerja>
){
    if (tanamanList.isEmpty() || pekerjaList.isEmpty()) {
        CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        return
    }
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .padding(12.dp)
            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ){
        FormInputAktivitas(
            insertAktivitasUiEvent = inserAktivitasUiState.insertAktivitasUiEvent,
            onValueChange = onAktivitasValueChange,
            errorState = inserAktivitasUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            tanamanList = tanamanList,
            pekerjaList = pekerjaList
        )
        Button (
            onClick = onSaveClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF004D40)),
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan",
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormInputAktivitas(
    insertAktivitasUiEvent: InsertAktivitasUiEvent,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    onValueChange: (InsertAktivitasUiEvent) -> Unit = {},
    enabled: Boolean = true,
    tanamanList: List<Tanaman>,
    pekerjaList: List<Pekerja>
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        if (tanamanList.isNotEmpty()){
            TanamanDropdown(
                tanamanList = tanamanList,
                selectedtanamanId = insertAktivitasUiEvent.idTanaman,
                ontanamanSelected = {tanaman ->
                    onValueChange(insertAktivitasUiEvent.copy(idTanaman = tanaman))
                }
            )
            Text(
                text = errorState.idTanaman ?: "",
                color = Color.Red
            )
        }

        if (pekerjaList.isNotEmpty()){
            PekerjaDropdown (
                pekerjaList = pekerjaList,
                selectedpekerjaId = insertAktivitasUiEvent.idPekerja,
                onpekerjaSelected = {pekerja ->
                    onValueChange(insertAktivitasUiEvent.copy(idPekerja = pekerja))
                }
            )
            Text(
                text = errorState.idPekerja ?: "",
                color = Color.Red
            )
        }

        OutlinedTextField(
            value = insertAktivitasUiEvent.tanggalAktivitas,
            onValueChange = {onValueChange(insertAktivitasUiEvent.copy(tanggalAktivitas = it))},
            label = { Text("Tanggal Aktivitas") },
            isError = errorState.tanggalAktivitas != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.tanggalAktivitas ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            value = insertAktivitasUiEvent.deskripsiAktivitas,
            onValueChange = {onValueChange(insertAktivitasUiEvent.copy(deskripsiAktivitas = it))},
            label = { Text("Deskripsi Aktivitas") },
            isError = errorState.deskripsiAktivitas != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.deskripsiAktivitas ?: "",
            color = Color.Red
        )
        Text(
            text = "Isi Data Secara Terperinci!",
            modifier = Modifier.padding(12.dp)
        )
        Divider(
            thickness = 8.dp,
            modifier = Modifier.padding(12.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TanamanDropdown(
    tanamanList: List<Tanaman>,
    selectedtanamanId: Int?,
    ontanamanSelected: (Int) -> Unit
) {
    val tanamanMap = tanamanList.associate { it.namaTanaman to it.idTanaman }
    val options = tanamanMap.keys.toList()

    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember(selectedtanamanId, tanamanList) {
        mutableStateOf(
            tanamanList.find { it.idTanaman == selectedtanamanId }?.namaTanaman ?: ""
        )
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Nama tanaman") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        ontanamanSelected(tanamanMap[selectionOption]!!)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PekerjaDropdown(
    pekerjaList: List<Pekerja>,
    selectedpekerjaId: Int?,
    onpekerjaSelected: (Int) -> Unit
) {
    val pekerjaMap = pekerjaList.associate { it.namaPekerja to it.idPekerja }
    val options = pekerjaMap.keys.toList()

    val expanded = remember { mutableStateOf(false) }
    val currentSelection = remember(selectedpekerjaId, pekerjaList) {
        mutableStateOf(
            pekerjaList.find { it.idPekerja == selectedpekerjaId }?.namaPekerja ?: ""
        )
    }

    ExposedDropdownMenuBox(
        expanded = expanded.value,
        onExpandedChange = { expanded.value = !expanded.value }
    ) {
        OutlinedTextField(
            value = currentSelection.value,
            onValueChange = {},
            readOnly = true,
            label = { Text("Nama Pekerja") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        currentSelection.value = selectionOption
                        expanded.value = false
                        onpekerjaSelected(pekerjaMap[selectionOption]!!)
                    }
                )
            }
        }
    }
}