package com.roddstkd.admin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roddstkd.admin.model.PersonRecord
import com.roddstkd.admin.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeltTestingScreen(navController: NavHostController) {
    val repo = remember { AdminRepository() }
    val items = remember { mutableStateListOf<PersonRecord>() }
    val status = remember { mutableStateOf("Press the button to load the belt testing list") }

    suspend fun loadList() {
        try {
            val result = withContext(Dispatchers.IO) { repo.getBeltTestList() }
            items.clear()
            items.addAll(result.items)
            status.value = result.message.ifBlank { "Loaded ${result.items.size} students" }
        } catch (e: Exception) {
            status.value = "Error: ${e.message}"
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Belt Testing") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = { kotlinx.coroutines.GlobalScope.launchMain { loadList() } }, modifier = Modifier.fillMaxWidth()) {
                Text("List Who Is On List")
            }

            Text(status.value)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items) { item ->
                    BeltCard(item, repo) { status.value = it }
                }
            }
        }
    }
}

@Composable
private fun BeltCard(
    item: PersonRecord,
    repo: AdminRepository,
    onStatus: (String) -> Unit
) {
    val currentBelt = remember { mutableStateOf(item.currentBelt) }
    val nextBelt = remember { mutableStateOf(item.nextBelt) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(item.name)
            OutlinedTextField(
                value = currentBelt.value,
                onValueChange = { currentBelt.value = it },
                label = { Text("Current Belt") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nextBelt.value,
                onValueChange = { nextBelt.value = it },
                label = { Text("Next Belt") },
                modifier = Modifier.fillMaxWidth()
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    kotlinx.coroutines.GlobalScope.launchMain {
                        try {
                            val response = withContext(Dispatchers.IO) {
                                repo.updateBelts(item.id, currentBelt.value, nextBelt.value)
                            }
                            onStatus(response.message)
                        } catch (e: Exception) {
                            onStatus("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("Save Belts")
                }
                Button(onClick = {
                    kotlinx.coroutines.GlobalScope.launchMain {
                        try {
                            val response = withContext(Dispatchers.IO) { repo.sendBeltInvite(item.id) }
                            onStatus(response.message)
                        } catch (e: Exception) {
                            onStatus("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("Send Invite")
                }
            }
        }
    }
}
