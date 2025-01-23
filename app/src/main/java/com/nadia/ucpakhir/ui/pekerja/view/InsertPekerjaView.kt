package com.nadia.ucpakhir.ui.pekerja.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nadia.ucpakhir.ui.PenyediaViewModel
import com.nadia.ucpakhir.ui.customwidget.CostumeTopAppBar
import com.nadia.ucpakhir.ui.navigation.DestinasiNavigasi
import com.nadia.ucpakhir.ui.pekerja.viewmodel.InsertPekerjaUiEvent
import com.nadia.ucpakhir.ui.pekerja.viewmodel.FormErrorState
import com.nadia.ucpakhir.ui.pekerja.viewmodel.InsertPekerjaUiState
import com.nadia.ucpakhir.ui.pekerja.viewmodel.InsertPekerjaViewModel
import kotlinx.coroutines.launch

object DestinasiEntryPekerja : DestinasiNavigasi {
    override val route = "entry_pekerja"
    override val titleRes = "Tambah Pekerja"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryPkrjScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertPekerjaViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryPekerja.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyPekerja(
            insertPekerjaUiState = viewModel.uiState,
            onPekerjaValueChange = viewModel::updateInsertPekerjaState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()){
                        viewModel.insertPkrj()
                        navigateBack()
                    }
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()
        )
    }
}

@Composable
fun EntryBodyPekerja(
    insertPekerjaUiState: InsertPekerjaUiState,
    onPekerjaValueChange: (InsertPekerjaUiEvent) -> Unit,
    onSaveClick:() -> Unit,
    modifier: Modifier = Modifier
){
    Column (
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier
            .padding(12.dp)
            .background(Color(0xFFE8F5E9), shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ){
        FormInputPekerja(
            insertPekerjaUiEvent = insertPekerjaUiState.insertPekerjaUiEvent,
            onValueChange = onPekerjaValueChange,
            errorState = insertPekerjaUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button (
            onClick = onSaveClick,
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8D6E63)),
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
fun FormInputPekerja(
    insertPekerjaUiEvent: InsertPekerjaUiEvent,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    onValueChange: (InsertPekerjaUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertPekerjaUiEvent.namaPekerja,
            onValueChange = {onValueChange(insertPekerjaUiEvent.copy(namaPekerja = it))},
            label = { Text("Nama Pekerja") },
            isError = errorState.namaPekerja != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF8D6E63),
                cursorColor = Color(0xFF8D6E63)
            )
        )
        Text(
            text = errorState.namaPekerja ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertPekerjaUiEvent.jabatan,
            onValueChange = {onValueChange(insertPekerjaUiEvent.copy(jabatan = it))},
            label = { Text("Jabatan") },
            isError = errorState.jabatan != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF8D6E63),
                cursorColor = Color(0xFF8D6E63)
            )
        )
        Text(
            text = errorState.jabatan ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            value = insertPekerjaUiEvent.kontakPekerja,
            onValueChange = {onValueChange(insertPekerjaUiEvent.copy(kontakPekerja = it))},
            label = { Text("Kontak Pekerja") },
            isError = errorState.kontakPekerja != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF8D6E63),
                cursorColor = Color(0xFF8D6E63)
            )
        )
        Text(
            text = errorState.kontakPekerja ?: "",
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