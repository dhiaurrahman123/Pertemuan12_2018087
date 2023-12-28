package com.example.pertemuan12.ui.theme.kontak.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.pertemuan12.KontakAPP.TopAppBarKontak
import com.example.pertemuan12.navigation.DestinasiNavigasi
import com.example.pertemuan12.ui.theme.PenyediaViewModel
import com.example.pertemuan12.ui.theme.kontak.viewmodel.InsertUIEvent
import com.example.pertemuan12.ui.theme.kontak.viewmodel.InsertUIState
import com.example.pertemuan12.ui.theme.kontak.viewmodel.InsertViewModel
import kotlinx.coroutines.launch



@Composable
fun EntryKontakBody(
    insertUiState:InsertUIState,
    onSiswaValueChange: (InsertUIEvent) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(18.dp),
        modifier = modifier.padding(12.dp)
    ){
        FormInputSiswa(
            insertUIEvent = insertUiState.insertUIEvent,
            onValueChange = onSiswaValueChange,
            modifier=Modifier.fillMaxWidth()
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "Simpan")
        }
    }
}

object DestinasiEntry : DestinasiNavigasi {
    override val route = "item entry"
    override val titleRes =  "Entry Siswa"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryKontakScreen(
    navigateBack:()->Unit,
    modifier:Modifier= Modifier,
    viewModel:InsertViewModel=viewModel(factory= PenyediaViewModel.Factory)
){
    val coroutineScope = rememberCoroutineScope()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold(
        modifier = modifier.nestedScroll (scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBarKontak(
                title = DestinasiEntry.titleRes,
                canNavigateBack = true,
                scrollBehavior= scrollBehavior,
                navigateUp = navigateBack
        }){ innerPadding ->
        EntryKontakBody(
            insertuistate = viewModel.insertKontakState,
            onSiswaValueChange = viewModel::updateInsertKontakState, onSaveClick = {
                coroutineScope.launch {
                    viewModel.insertKontak()
                    navigateBack()
                }
            },
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxWidth()

    }
}