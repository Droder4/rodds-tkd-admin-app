package com.roddstkd.admin.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.roddstkd.admin.repository.AdminRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunicationScreen(navController: NavHostController) {
    val repo = remember { AdminRepository() }
    val location = remember { mutableStateOf("") }
    val className = remember { mutableStateOf("") }
    val subject = remember { mutableStateOf("") }
    val message = remember { mutableStateOf("") }
    val status = remember { mutableStateOf("Send email blasts to a class or location") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Communication") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = location.value,
                onValueChange = { location.value = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = className.value,
                onValueChange = { className.value = it },
                label = { Text("Class") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = subject.value,
                onValueChange = { subject.value = it },
                label = { Text("Subject") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = message.value,
                onValueChange = { message.value = it },
                label = { Text("Message") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(onClick = {
                kotlinx.coroutines.GlobalScope.launchMain {
                    try {
                        val response = withContext(Dispatchers.IO) {
                            repo.sendBlast(location.value, className.value, subject.value, message.value)
                        }
                        status.value = response.message
                    } catch (e: Exception) {
                        status.value = "Error: ${e.message}"
                    }
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Send Email Blast")
            }

            Text(status.value)
        }
    }
}
