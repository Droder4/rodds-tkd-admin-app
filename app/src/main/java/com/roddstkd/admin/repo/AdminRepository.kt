package com.roddstkd.admin.repo

import com.roddstkd.admin.BuildConfig
import com.roddstkd.admin.model.AdminInitResponse
import com.roddstkd.admin.model.ApiActionResponse
import com.roddstkd.admin.model.BeltTestingResponse
import com.roddstkd.admin.model.GenericRequest
import com.roddstkd.admin.model.PaymentListResponse
import com.roddstkd.admin.model.RegistrationListResponse
import com.roddstkd.admin.model.RosterResponse
import com.roddstkd.admin.network.NetworkModule

class AdminRepository {
    private val api = NetworkModule.apiService

    private val registrationUrl = BuildConfig.REGISTRATION_BASE_URL + "exec"
    private val paymentUrl = BuildConfig.PAYMENT_BASE_URL + "exec"

    suspend fun getAdminInit(): AdminInitResponse {
        return api.postAdminInit(
            registrationUrl,
            GenericRequest(action = "adminGetInitData")
        )
    }

    suspend fun getPaymentDueSoon(club: String, month: String): PaymentListResponse {
        return api.postPaymentList(
            paymentUrl,
            GenericRequest(
                action = "getDueSoon",
                club = club,
                month = month
            )
        )
    }

    suspend fun getWhoOwes(club: String, month: String): PaymentListResponse {
        return api.postPaymentList(
            paymentUrl,
            GenericRequest(
                action = "getWhoOwes",
                club = club,
                month = month
            )
        )
    }

    suspend fun getReminderNoticeList(club: String, month: String): PaymentListResponse {
        return api.postPaymentList(
            paymentUrl,
            GenericRequest(
                action = "getReminderNoticeList",
                club = club,
                month = month
            )
        )
    }

    suspend fun getOverdueNoticeList(club: String, month: String): PaymentListResponse {
        return api.postPaymentList(
            paymentUrl,
            GenericRequest(
                action = "getOverdueNoticeList",
                club = club,
                month = month
            )
        )
    }

    suspend fun sendReminderEmail(club: String, month: String): ApiActionResponse {
        return api.postAction(
            paymentUrl,
            GenericRequest(
                action = "sendReminderEmail",
                club = club,
                month = month
            )
        )
    }

    suspend fun sendDueSoonEmail(club: String, month: String): ApiActionResponse {
        return api.postAction(
            paymentUrl,
            GenericRequest(
                action = "sendDueSoonEmail",
                club = club,
                month = month
            )
        )
    }

    suspend fun sendOverdueEmail(club: String, month: String): ApiActionResponse {
        return api.postAction(
            paymentUrl,
            GenericRequest(
                action = "sendOverdueEmail",
                club = club,
                month = month
            )
        )
    }

    suspend fun getRegistrations(): RegistrationListResponse {
        return api.postRegistrationList(
            registrationUrl,
            GenericRequest(action = "adminGetRegistrations")
        )
    }

    suspend fun acceptRegistration(rowNumber: Int, assignedClass: String): ApiActionResponse {
        return api.postAction(
            registrationUrl,
            GenericRequest(
                action = "adminAcceptRegistration",
                rowNumber = rowNumber,
                assignedClass = assignedClass
            )
        )
    }

    suspend fun updateStudentManagement(
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
    ): ApiActionResponse {
        return api.postAction(
            registrationUrl,
            GenericRequest(
                action = "updateStudentManagement",
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
        )
    }

    suspend fun getRoster(club: String, assignedClass: String): RosterResponse {
        return api.postRoster(
            registrationUrl,
            GenericRequest(
                action = "adminGetRoster",
                club = club,
                assignedClass = assignedClass
            )
        )
    }

    suspend fun getBeltTesting(club: String, assignedClass: String): BeltTestingResponse {
        return api.postBeltTesting(
            registrationUrl,
            GenericRequest(
                action = "adminGetBeltTesting",
                club = club,
                assignedClass = assignedClass
            )
        )
    }

    suspend fun saveBeltTesting(
        rowNumber: Int,
        currentBelt: String,
        testingFor: String,
        beltTestDate: String,
        beltInviteStatus: String,
        studentNotes: String
    ): ApiActionResponse {
        return api.postAction(
            registrationUrl,
            GenericRequest(
                action = "adminSaveBeltTesting",
                rowNumber = rowNumber,
                currentBelt = currentBelt,
                testingFor = testingFor,
                beltTestDate = beltTestDate,
                beltInviteStatus = beltInviteStatus,
                studentNotes = studentNotes
            )
        )
    }

    suspend fun sendBeltTestInvitations(): ApiActionResponse {
        return api.postAction(
            registrationUrl,
            GenericRequest(action = "adminSendBeltTestInvitations")
        )
    }

    suspend fun sendPendingEmails(): ApiActionResponse {
        return api.postAction(
            registrationUrl,
            GenericRequest(action = "adminSendPendingEmails")
        )
    }
}
