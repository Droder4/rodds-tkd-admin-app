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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
fun PaymentScreen(navController: NavHostController) {
    val repo = remember { AdminRepository() }
    val items = remember { mutableStateListOf<PersonRecord>() }
    val message = remember { mutableStateOf("Choose an option") }

    suspend fun load(action: String) {
        try {
            val result = withContext(Dispatchers.IO) {
                when (action) {
                    "dueSoon" -> repo.getPaymentDueSoon()
                    "whoOwes" -> repo.getWhoOwes()
                    "reminderList" -> repo.getReminderNoticeList()
                    else -> repo.getOverdueNoticeList()
                }
            }
            items.clear()
            items.addAll(result.items)
            message.value = result.message.ifBlank { "Loaded ${result.items.size} records" }
        } catch (e: Exception) {
            message.value = "Error: ${e.message}"
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Payment Reminders") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(onClick = { kotlinx.coroutines.GlobalScope.launchMain { load("dueSoon") } }, modifier = Modifier.fillMaxWidth()) {
                Text("Payment Due Soon")
            }
            Button(onClick = { kotlinx.coroutines.GlobalScope.launchMain { load("whoOwes") } }, modifier = Modifier.fillMaxWidth()) {
                Text("Who Owes")
            }
            Button(onClick = { kotlinx.coroutines.GlobalScope.launchMain { load("reminderList") } }, modifier = Modifier.fillMaxWidth()) {
                Text("Reminder Notice To Be Sent")
            }
            Button(onClick = { kotlinx.coroutines.GlobalScope.launchMain { load("overdueList") } }, modifier = Modifier.fillMaxWidth()) {
                Text("Overdue Notice To Be Sent")
            }

            Text(message.value)

            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items) { item ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(item.name)
                            Text("Email: ${item.email}")
                            Text("Class: ${item.className}")
                            Text("Location: ${item.location}")
                            Text("Amount Due: ${item.amountDue}")
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                Button(onClick = {
                                    kotlinx.coroutines.GlobalScope.launchMain {
                                        try {
                                            val response = withContext(Dispatchers.IO) { repo.sendReminder(item.id) }
                                            message.value = response.message
                                        } catch (e: Exception) {
                                            message.value = "Error: ${e.message}"
                                        }
                                    }
                                }) {
                                    Text("Send Reminder")
                                }
                                Button(onClick = {
                                    kotlinx.coroutines.GlobalScope.launchMain {
                                        try {
                                            val response = withContext(Dispatchers.IO) { repo.sendOverdue(item.id) }
                                            message.value = response.message
                                        } catch (e: Exception) {
                                            message.value = "Error: ${e.message}"
                                        }
                                    }
                                }) {
                                    Text("Send Overdue")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}