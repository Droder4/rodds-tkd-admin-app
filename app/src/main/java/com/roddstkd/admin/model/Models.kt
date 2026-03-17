package com.roddstkd.admin.model

data class GenericRequest(
    val action: String,
    val club: String? = null,
    val month: String? = null,
    val rowNumber: Int? = null,
    val assignedClass: String? = null,
    val email: String? = null,
    val studentName: String? = null,
    val paymentStatus: String? = null,
    val amountPaid: String? = null,
    val currentBelt: String? = null,
    val testingFor: String? = null,
    val beltTestDate: String? = null,
    val beltInviteStatus: String? = null,
    val studentNotes: String? = null
)

data class ApiActionResponse(
    val success: Boolean = false,
    val message: String = ""
)

data class PaymentItem(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val location: String = "",
    val className: String = "",
    val status: String = "",
    val currentBelt: String = "",
    val nextBelt: String = "",
    val amountDue: String = ""
)

data class PaymentListResponse(
    val success: Boolean = false,
    val message: String = "",
    val items: List<PaymentItem> = emptyList()
)

data class RegistrationItem(
    val rowNumber: Int = 0,
    val timestamp: String = "",
    val studentType: String = "",
    val location: String = "",
    val email: String = "",
    val parentName: String = "",
    val contactNumber: String = "",
    val studentName: String = "",
    val studentAge: String = "",
    val birthdate: String = "",
    val grade: String = "",
    val assignedClass: String = "",
    val emailStatus: String = "",
    val notes: String = "",
    val registrationStatus: String = "",
    val completeEmailSent: String = "",
    val incompleteReminderSent: String = "",
    val beltTestInvitationSent: String = "",
    val classReminderSent: String = "",
    val paymentStatus: String = "",
    val amountPaid: String = "",
    val currentBelt: String = "",
    val testingFor: String = "",
    val beltTestDate: String = "",
    val beltInviteStatus: String = "",
    val studentNotes: String = ""
)

data class RegistrationListResponse(
    val success: Boolean = false,
    val registrations: List<RegistrationItem> = emptyList(),
    val message: String = ""
)

data class RosterStudent(
    val rowNumber: Int = 0,
    val timestamp: String = "",
    val studentName: String = "",
    val studentAge: String = "",
    val birthdate: String = "",
    val grade: String = "",
    val assignedClass: String = "",
    val parentName: String = "",
    val email: String = "",
    val contactNumber: String = "",
    val studentType: String = "",
    val emailStatus: String = "",
    val notes: String = ""
)

data class RosterResponse(
    val success: Boolean = false,
    val headers: List<String> = emptyList(),
    val students: List<RosterStudent> = emptyList(),
    val message: String = ""
)

data class BeltTestingStudent(
    val rowNumber: Int = 0,
    val studentName: String = "",
    val parentName: String = "",
    val email: String = "",
    val location: String = "",
    val assignedClass: String = "",
    val currentBelt: String = "",
    val testingFor: String = "",
    val fee: String = "",
    val beltTestDate: String = "",
    val beltInviteStatus: String = "",
    val studentNotes: String = "",
    val inviteMessage: String = ""
)

data class BeltTestingResponse(
    val success: Boolean = false,
    val students: List<BeltTestingStudent> = emptyList(),
    val message: String = ""
)

data class DashboardStats(
    val totalStudents: Int = 0,
    val cornwallStudents: Int = 0,
    val montagueStudents: Int = 0,
    val pendingWelcomeEmails: Int = 0,
    val unpaidStudents: Int = 0,
    val beltInviteReady: Int = 0
)

data class AdminInitResponse(
    val success: Boolean = false,
    val stats: DashboardStats = DashboardStats(),
    val locations: List<String> = emptyList(),
    val classes: List<String> = emptyList(),
    val pendingRegistrations: List<RegistrationItem> = emptyList()
)

data class DisplayItem(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val status: String = "",
    val club: String = "",
    val className: String = "",
    val currentBelt: String = "",
    val nextBelt: String = "",
    val amountDue: String = "",
    val notes: String = ""
)
