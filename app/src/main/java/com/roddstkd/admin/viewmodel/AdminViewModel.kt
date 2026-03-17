package com.roddstkd.admin.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.roddstkd.admin.model.DisplayItem
import com.roddstkd.admin.repo.AdminRepository
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {
    private val repo = AdminRepository()

    var loading by mutableStateOf(false)
        private set

    var message by mutableStateOf("Ready")
        private set

    var items by mutableStateOf(listOf<DisplayItem>())
        private set

    fun loadPaymentDueSoon(club: String, month: String) {
        viewModelScope.launch {
            loading = true
            try {
                val response = repo.getPaymentDueSoon(club, month)
                items = response.items.map {
                    DisplayItem(
                        id = it.id,
                        name = it.name,
                        email = it.email,
                        status = it.status,
                        club = it.location,
                        className = it.className,
                        currentBelt = it.currentBelt,
                        nextBelt = it.nextBelt,
                        amountDue = it.amountDue
                    )
                }
                message = response.message
            } catch (e: Exception) {
                items = emptyList()
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun loadWhoOwes(club: String, month: String) {
        viewModelScope.launch {
            loading = true
            try {
                val response = repo.getWhoOwes(club, month)
                items = response.items.map {
                    DisplayItem(
                        id = it.id,
                        name = it.name,
                        email = it.email,
                        status = it.status,
                        club = it.location,
                        className = it.className,
                        currentBelt = it.currentBelt,
                        nextBelt = it.nextBelt,
                        amountDue = it.amountDue
                    )
                }
                message = response.message
            } catch (e: Exception) {
                items = emptyList()
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun loadReminderNoticeList(club: String, month: String) {
        viewModelScope.launch {
            loading = true
            try {
                val response = repo.getReminderNoticeList(club, month)
                items = response.items.map {
                    DisplayItem(
                        id = it.id,
                        name = it.name,
                        email = it.email,
                        status = it.status,
                        club = it.location,
                        className = it.className,
                        currentBelt = it.currentBelt,
                        nextBelt = it.nextBelt,
                        amountDue = it.amountDue
                    )
                }
                message = response.message
            } catch (e: Exception) {
                items = emptyList()
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun loadOverdueNoticeList(club: String, month: String) {
        viewModelScope.launch {
            loading = true
            try {
                val response = repo.getOverdueNoticeList(club, month)
                items = response.items.map {
                    DisplayItem(
                        id = it.id,
                        name = it.name,
                        email = it.email,
                        status = it.status,
                        club = it.location,
                        className = it.className,
                        currentBelt = it.currentBelt,
                        nextBelt = it.nextBelt,
                        amountDue = it.amountDue
                    )
                }
                message = response.message
            } catch (e: Exception) {
                items = emptyList()
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun sendReminderEmail(club: String, month: String) {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.sendReminderEmail(club, month).message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun sendDueSoonEmail(club: String, month: String) {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.sendDueSoonEmail(club, month).message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun sendOverdueEmail(club: String, month: String) {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.sendOverdueEmail(club, month).message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun loadRegistrations() {
        viewModelScope.launch {
            loading = true
            try {
                val response = repo.getRegistrations()
                items = response.registrations.map {
                    DisplayItem(
                        id = it.rowNumber.toString(),
                        name = it.studentName,
                        email = it.email,
                        status = it.registrationStatus,
                        club = it.location,
                        className = it.assignedClass,
                        currentBelt = it.currentBelt,
                        nextBelt = it.testingFor,
                        amountDue = it.amountPaid,
                        notes = it.studentNotes
                    )
                }
                message = if (response.success) "Registrations loaded" else (response.message.ifBlank { "Failed" })
            } catch (e: Exception) {
                items = emptyList()
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun acceptRegistration(rowNumber: Int, assignedClass: String) {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.acceptRegistration(rowNumber, assignedClass).message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun updateStudentManagement(
        email: String,
        studentName: String,
        assignedClass: String,
        paymentStatus: String,
        amountPaid: String,
        currentBelt: String,
        testingFor: String,
        beltTestDate: String,
        beltInviteStatus: String,
        studentNotes: String
    ) {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.updateStudentManagement(
                    email,
                    studentName,
                    assignedClass,
                    paymentStatus,
                    amountPaid,
                    currentBelt,
                    testingFor,
                    beltTestDate,
                    beltInviteStatus,
                    studentNotes
                ).message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun loadRoster(club: String, assignedClass: String) {
        viewModelScope.launch {
            loading = true
            try {
                val response = repo.getRoster(club, assignedClass)
                items = response.students.map {
                    DisplayItem(
                        id = it.rowNumber.toString(),
                        name = it.studentName,
                        email = it.email,
                        status = it.emailStatus,
                        club = club,
                        className = it.assignedClass,
                        notes = it.notes
                    )
                }
                message = if (response.success) "Roster loaded" else (response.message.ifBlank { "Failed" })
            } catch (e: Exception) {
                items = emptyList()
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun loadBeltTesting(club: String, assignedClass: String) {
        viewModelScope.launch {
            loading = true
            try {
                val response = repo.getBeltTesting(club, assignedClass)
                items = response.students.map {
                    DisplayItem(
                        id = it.rowNumber.toString(),
                        name = it.studentName,
                        email = it.email,
                        status = it.beltInviteStatus,
                        club = it.location,
                        className = it.assignedClass,
                        currentBelt = it.currentBelt,
                        nextBelt = it.testingFor,
                        amountDue = it.fee,
                        notes = it.studentNotes
                    )
                }
                message = if (response.success) "Belt testing loaded" else (response.message.ifBlank { "Failed" })
            } catch (e: Exception) {
                items = emptyList()
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun saveBeltTesting(
        rowNumber: Int,
        currentBelt: String,
        testingFor: String,
        beltTestDate: String,
        beltInviteStatus: String,
        studentNotes: String
    ) {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.saveBeltTesting(
                    rowNumber,
                    currentBelt,
                    testingFor,
                    beltTestDate,
                    beltInviteStatus,
                    studentNotes
                ).message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun sendBeltTestInvitations() {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.sendBeltTestInvitations().message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }

    fun sendPendingEmails() {
        viewModelScope.launch {
            loading = true
            try {
                message = repo.sendPendingEmails().message
            } catch (e: Exception) {
                message = e.message ?: "Error"
            }
            loading = false
        }
    }
}
