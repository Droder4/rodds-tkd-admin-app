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
fun RegistrationScreen(navController: NavHostController) {
    val repo = remember { AdminRepository() }
    val items = remember { mutableStateListOf<PersonRecord>() }
    val status = remember { mutableStateOf("Choose an option") }
    val rosterLocation = remember { mutableStateOf("") }
    val rosterClass = remember { mutableStateOf("") }

    suspend fun loadPending() {
        try {
            val result = withContext(Dispatchers.IO) { repo.getPendingRegistrations() }
            items.clear()
            items.addAll(result.items)
            status.value = result.message.ifBlank { "Loaded ${result.items.size} registrations" }
        } catch (e: Exception) {
            status.value = "Error: ${e.message}"
        }
    }

    suspend fun loadRoster() {
        try {
            val result = withContext(Dispatchers.IO) {
                repo.getRoster(rosterLocation.value, rosterClass.value)
            }
            items.clear()
            items.addAll(result.items)
            status.value = result.message.ifBlank { "Loaded ${result.items.size} roster records" }
        } catch (e: Exception) {
            status.value = "Error: ${e.message}"
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registrations") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = { kotlinx.coroutines.GlobalScope.launchMain { loadPending() } }, modifier = Modifier.fillMaxWidth()) {
                Text("Accept Registrations")
            }

            OutlinedTextField(
                value = rosterLocation.value,
                onValueChange = { rosterLocation.value = it },
                label = { Text("Roster Location") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rosterClass.value,
                onValueChange = { rosterClass.value = it },
                label = { Text("Roster Class") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = { kotlinx.coroutines.GlobalScope.launchMain { loadRoster() } }, modifier = Modifier.fillMaxWidth()) {
                Text("Show Roster By Club And Class")
            }

            Text(status.value)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items) { item ->
                    RegistrationCard(item, repo) { status.value = it }
                }
            }
        }
    }
}

@Composable
private fun RegistrationCard(
    item: PersonRecord,
    repo: AdminRepository,
    onStatus: (String) -> Unit
) {
    val classInput = remember { mutableStateOf(item.className) }
    val nameInput = remember { mutableStateOf(item.name) }
    val emailInput = remember { mutableStateOf(item.email) }
    val locationInput = remember { mutableStateOf(item.location) }

    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("${item.name} (${item.status})")
            OutlinedTextField(value = nameInput.value, onValueChange = { nameInput.value = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = emailInput.value, onValueChange = { emailInput.value = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = locationInput.value, onValueChange = { locationInput.value = it }, label = { Text("Location") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = classInput.value, onValueChange = { classInput.value = it }, label = { Text("Class") }, modifier = Modifier.fillMaxWidth())

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    kotlinx.coroutines.GlobalScope.launchMain {
                        try {
                            val response = withContext(Dispatchers.IO) {
                                repo.approveRegistration(item.id, classInput.value)
                            }
                            onStatus(response.message)
                        } catch (e: Exception) {
                            onStatus("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("Assign / Accept")
                }

                Button(onClick = {
                    kotlinx.coroutines.GlobalScope.launchMain {
                        try {
                            val response = withContext(Dispatchers.IO) {
                                repo.updateRegistration(
                                    item.id,
                                    nameInput.value,
                                    emailInput.value,
                                    locationInput.value,
                                    classInput.value
                                )
                            }
                            onStatus(response.message)
                        } catch (e: Exception) {
                            onStatus("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("Edit")
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    kotlinx.coroutines.GlobalScope.launchMain {
                        try {
                            onStatus(withContext(Dispatchers.IO) { repo.sendCompleteEmail(item.id) }.message)
                        } catch (e: Exception) {
                            onStatus("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("Complete Email")
                }
                Button(onClick = {
                    kotlinx.coroutines.GlobalScope.launchMain {
                        try {
                            onStatus(withContext(Dispatchers.IO) { repo.sendOneMoreStepEmail(item.id) }.message)
                        } catch (e: Exception) {
                            onStatus("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("One More Step")
                }
                Button(onClick = {
                    kotlinx.coroutines.GlobalScope.launchMain {
                        try {
                            onStatus(withContext(Dispatchers.IO) { repo.sendNotCompleteEmail(item.id) }.message)
                        } catch (e: Exception) {
                            onStatus("Error: ${e.message}")
                        }
                    }
                }) {
                    Text("Not Complete")
                }
            }
        }
    }
}
