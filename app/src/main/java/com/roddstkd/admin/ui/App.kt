package com.roddstkd.admin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.roddstkd.admin.model.DisplayItem
import com.roddstkd.admin.viewmodel.AdminViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminApp(vm: AdminViewModel = viewModel()) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Payment", "Registration", "Belt Testing", "Communication")

    Scaffold(
        topBar = { TopAppBar(title = { Text("Rodd's TKD Admin") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            TabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> PaymentScreen(vm)
                1 -> RegistrationScreen(vm)
                2 -> BeltTestingScreen(vm)
                3 -> CommunicationScreen(vm)
            }
        }
    }
}

@Composable
fun PaymentScreen(vm: AdminViewModel) {
    var club by remember { mutableStateOf("cornwall") }
    var month by remember { mutableStateOf("September") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Payment", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = club,
            onValueChange = { club = it },
            label = { Text("Club: cornwall or montague") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = month,
            onValueChange = { month = it },
            label = { Text("Month") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.loadPaymentDueSoon(club, month) }) { Text("Payment Due Soon") }
            Button(onClick = { vm.loadWhoOwes(club, month) }) { Text("Who Owes") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.loadReminderNoticeList(club, month) }) { Text("Reminder Notice List") }
            Button(onClick = { vm.loadOverdueNoticeList(club, month) }) { Text("Overdue Notice List") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.sendReminderEmail(club, month) }) { Text("Send Reminder") }
            Button(onClick = { vm.sendDueSoonEmail(club, month) }) { Text("Send Due Soon") }
            Button(onClick = { vm.sendOverdueEmail(club, month) }) { Text("Send Overdue") }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(vm.message)
        Spacer(modifier = Modifier.height(8.dp))
        ResultList(vm.items)
    }
}

@Composable
fun RegistrationScreen(vm: AdminViewModel) {
    var rowNumber by remember { mutableStateOf("") }
    var assignedClass by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var studentName by remember { mutableStateOf("") }
    var paymentStatus by remember { mutableStateOf("") }
    var amountPaid by remember { mutableStateOf("") }
    var currentBelt by remember { mutableStateOf("") }
    var testingFor by remember { mutableStateOf("") }
    var beltTestDate by remember { mutableStateOf("") }
    var beltInviteStatus by remember { mutableStateOf("") }
    var studentNotes by remember { mutableStateOf("") }
    var rosterClub by remember { mutableStateOf("Cornwall") }
    var rosterClass by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Registration", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { vm.loadRegistrations() }, modifier = Modifier.fillMaxWidth()) {
            Text("Load Registrations")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = rowNumber, onValueChange = { rowNumber = it }, label = { Text("Row Number") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = assignedClass, onValueChange = { assignedClass = it }, label = { Text("Assigned Class") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                rowNumber.toIntOrNull()?.let { vm.acceptRegistration(it, assignedClass) }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Accept Registration / Assign Class")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = studentName, onValueChange = { studentName = it }, label = { Text("Student Name") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = paymentStatus, onValueChange = { paymentStatus = it }, label = { Text("Payment Status") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = amountPaid, onValueChange = { amountPaid = it }, label = { Text("Amount Paid") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = currentBelt, onValueChange = { currentBelt = it }, label = { Text("Current Belt") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = testingFor, onValueChange = { testingFor = it }, label = { Text("Testing For") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = beltTestDate, onValueChange = { beltTestDate = it }, label = { Text("Belt Test Date") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = beltInviteStatus, onValueChange = { beltInviteStatus = it }, label = { Text("Belt Invite Status") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = studentNotes, onValueChange = { studentNotes = it }, label = { Text("Student Notes") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                vm.updateStudentManagement(
                    email = email,
                    studentName = studentName,
                    assignedClass = assignedClass,
                    paymentStatus = paymentStatus,
                    amountPaid = amountPaid,
                    currentBelt = currentBelt,
                    testingFor = testingFor,
                    beltTestDate = beltTestDate,
                    beltInviteStatus = beltInviteStatus,
                    studentNotes = studentNotes
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Edit Registration / Student Management")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = rosterClub, onValueChange = { rosterClub = it }, label = { Text("Roster Club") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = rosterClass, onValueChange = { rosterClass = it }, label = { Text("Roster Class") }, modifier = Modifier.fillMaxWidth())
        Button(onClick = { vm.loadRoster(rosterClub, rosterClass) }, modifier = Modifier.fillMaxWidth()) {
            Text("Show Roster")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(vm.message)
        Spacer(modifier = Modifier.height(8.dp))
        ResultList(vm.items)
    }
}

@Composable
fun BeltTestingScreen(vm: AdminViewModel) {
    var club by remember { mutableStateOf("") }
    var assignedClass by remember { mutableStateOf("") }
    var rowNumber by remember { mutableStateOf("") }
    var currentBelt by remember { mutableStateOf("") }
    var testingFor by remember { mutableStateOf("") }
    var beltTestDate by remember { mutableStateOf("") }
    var beltInviteStatus by remember { mutableStateOf("") }
    var studentNotes by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Belt Testing", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = club, onValueChange = { club = it }, label = { Text("Club (optional)") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = assignedClass, onValueChange = { assignedClass = it }, label = { Text("Assigned Class (optional)") }, modifier = Modifier.fillMaxWidth())

        Button(onClick = { vm.loadBeltTesting(club, assignedClass) }, modifier = Modifier.fillMaxWidth()) {
            Text("Load Belt Testing List")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(value = rowNumber, onValueChange = { rowNumber = it }, label = { Text("Row Number") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = currentBelt, onValueChange = { currentBelt = it }, label = { Text("Current Belt") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = testingFor, onValueChange = { testingFor = it }, label = { Text("Testing For") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = beltTestDate, onValueChange = { beltTestDate = it }, label = { Text("Belt Test Date") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = beltInviteStatus, onValueChange = { beltInviteStatus = it }, label = { Text("Belt Invite Status") }, modifier = Modifier.fillMaxWidth())
        OutlinedTextField(value = studentNotes, onValueChange = { studentNotes = it }, label = { Text("Student Notes") }, modifier = Modifier.fillMaxWidth())

        Button(
            onClick = {
                rowNumber.toIntOrNull()?.let {
                    vm.saveBeltTesting(it, currentBelt, testingFor, beltTestDate, beltInviteStatus, studentNotes)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Belt Testing")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { vm.sendBeltTestInvitations() }, modifier = Modifier.fillMaxWidth()) {
            Text("Send Belt Test Invitations")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(vm.message)
        Spacer(modifier = Modifier.height(8.dp))
        ResultList(vm.items)
    }
}

@Composable
fun CommunicationScreen(vm: AdminViewModel) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Communication", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { vm.sendPendingEmails() }, modifier = Modifier.fillMaxWidth()) {
            Text("Send Pending Welcome Emails")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = { vm.sendBeltTestInvitations() }, modifier = Modifier.fillMaxWidth()) {
            Text("Send Belt Test Invitations")
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(vm.message)
    }
}

@Composable
fun ResultList(items: List<DisplayItem>) {
    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        items(items) { item ->
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(item.name.ifBlank { "No name" }, style = MaterialTheme.typography.titleMedium)
                    if (item.email.isNotBlank()) Text("Email: ${item.email}")
                    if (item.status.isNotBlank()) Text("Status: ${item.status}")
                    if (item.club.isNotBlank()) Text("Club: ${item.club}")
                    if (item.className.isNotBlank()) Text("Class: ${item.className}")
                    if (item.currentBelt.isNotBlank()) Text("Current Belt: ${item.currentBelt}")
                    if (item.nextBelt.isNotBlank()) Text("Next Belt: ${item.nextBelt}")
                    if (item.amountDue.isNotBlank()) Text("Amount/Fee: ${item.amountDue}")
                    if (item.notes.isNotBlank()) Text("Notes: ${item.notes}")
                    if (item.id.isNotBlank()) Text("ID/Row: ${item.id}")
                }
            }
        }
    }
}
