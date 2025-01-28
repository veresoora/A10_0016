package com.nadia.ucpakhir.ui.catatanpanen.view

import android.annotation.SuppressLint
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
import com.nadia.ucpakhir.model.Tanaman
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.FormErrorState
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.InsertPanenUiEvent
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.InsertPanenUiState
import com.nadia.ucpakhir.ui.catatanpanen.viewmodel.InsertPanenViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import kotlinx.coroutines.launch

object DestinasiEntryPanen : DestinasiNavigasi {
    override val route = "entry_catatanpanen"
    override val titleRes = "Tambah Catatan Panen"
}

@SuppressLint("NewApi")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPnnScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPanenViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPanen.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPanen(
            insertPanenUiState = viewModel.uiState,
            onPanenValueChange = viewModel::updateInsertPanenState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()){
                        viewModel.insertPnn()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            tanamanList = viewModel.listTanaman
        )
    }
}

@Composable
fun EntryBodyPanen(
    insertPanenUiState: InsertPanenUiState,
    onPanenValueChange: (InsertPanenUiEvent) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier,
    tanamanList: List<Tanaman>
){
    if (tanamanList.isEmpty()) {
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
        FormInputPanen(
            insertPanenUiEvent = insertPanenUiState.insertPanenUiEvent,
            onValueChange = onPanenValueChange,
            errorState = insertPanenUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth(),
            tanamanList = tanamanList
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
fun FormInputPanen(
    insertPanenUiEvent: InsertPanenUiEvent,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    onValueChange: (InsertPanenUiEvent) -> Unit = {},
    enabled: Boolean = true,
    tanamanList: List<Tanaman>
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (tanamanList.isNotEmpty()) {
            TanamanDropdown(
                tanamanList = tanamanList,
                selectedtanamanId = insertPanenUiEvent.idTanaman,
                ontanamanSelected = { tanaman ->
                    onValueChange(insertPanenUiEvent.copy(idTanaman = tanaman))
                }
            )
            Text(
                text = errorState.idTanaman ?: "",
                color = Color.Red
            )
        }

        OutlinedTextField(
            value = insertPanenUiEvent.tanggalPanen,
            onValueChange = { onValueChange(insertPanenUiEvent.copy(tanggalPanen = it)) },
            label = { Text("Tanggal Panen") },
            isError = errorState.tanggalPanen != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.tanggalPanen ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            value = insertPanenUiEvent.jumlahPanen,
            onValueChange = { onValueChange(insertPanenUiEvent.copy(jumlahPanen = it)) },
            label = { Text("Jumlah Panen") },
            isError = errorState.jumlahPanen != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.jumlahPanen ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            value = insertPanenUiEvent.keterangan,
            onValueChange = {onValueChange(insertPanenUiEvent.copy(keterangan = it))},
            label = { Text("Keterangan") },
            isError = errorState.keterangan != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.keterangan ?: "",
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
