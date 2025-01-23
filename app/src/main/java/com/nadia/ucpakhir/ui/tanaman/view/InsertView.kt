package com.nadia.ucpakhir.ui.tanaman.view

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
import com.nadia.ucpakhir.ui.tanaman.viewmodel.FormErrorState
import com.nadia.ucpakhir.ui.tanaman.viewmodel.InsertTanamanUiEvent
import com.nadia.ucpakhir.ui.tanaman.viewmodel.InsertTanamanUiState
import com.nadia.ucpakhir.ui.tanaman.viewmodel.InsertTanamanViewModel
import kotlinx.coroutines.launch

object DestinasiEntryTanaman : DestinasiNavigasi {
    override val route = "entry_tanaman"
    override val titleRes = "Tambah Tanaman"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryTnmnScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InsertTanamanViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CostumeTopAppBar(
                title = DestinasiEntryTanaman.titleRes,
                canNavigateBack = true,
                scrollBehavior = scrollBehavior,
                navigateUp = navigateBack
            )
        }
    ) { innerPadding ->
        EntryBodyTanaman(
            insertTanamanUiState = viewModel.uiState,
            onTanamanValueChange = viewModel::updateInsertTanamanState,
            onSaveClick = {
                coroutineScope.launch {
                    if (viewModel.validateFields()){
                        viewModel.insertTnmn()
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
fun EntryBodyTanaman(
    insertTanamanUiState: InsertTanamanUiState,
    onTanamanValueChange: (InsertTanamanUiEvent) -> Unit,
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
        FormInputTanaman(
            insertTanamanUiEvent = insertTanamanUiState.insertTanamanUiEvent,
            onValueChange = onTanamanValueChange,
            errorState = insertTanamanUiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
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
fun FormInputTanaman(
    insertTanamanUiEvent: InsertTanamanUiEvent,
    errorState: FormErrorState = FormErrorState(),
    modifier: Modifier = Modifier,
    onValueChange: (InsertTanamanUiEvent) -> Unit = {},
    enabled: Boolean = true
){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ){
        OutlinedTextField(
            value = insertTanamanUiEvent.namaTanaman,
            onValueChange = {onValueChange(insertTanamanUiEvent.copy(namaTanaman = it))},
            label = { Text("Nama Tanaman") },
            isError = errorState.namaTanaman != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.namaTanaman ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            value = insertTanamanUiEvent.periodeTanam,
            onValueChange = {onValueChange(insertTanamanUiEvent.copy(periodeTanam = it))},
            label = { Text("Periode Tanam") },
            isError = errorState.periodeTanam != null,
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.periodeTanam ?: "",
            color = Color.Red
        )
        OutlinedTextField(
            value = insertTanamanUiEvent.deskripsiTanaman,
            onValueChange = {onValueChange(insertTanamanUiEvent.copy(deskripsiTanaman = it))},
            label = { Text("Deskripsi Tanaman") },
            isError = errorState.deskripsiTanaman != null,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFF388E3C),
                cursorColor = Color(0xFF388E3C)
            )
        )
        Text(
            text = errorState.deskripsiTanaman ?: "",
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