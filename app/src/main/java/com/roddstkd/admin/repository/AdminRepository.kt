package com.roddstkd.admin.repository

import com.roddstkd.admin.model.ApiActionResponse
import com.roddstkd.admin.model.ApiListResponse
import com.roddstkd.admin.network.ApiConfig
import com.roddstkd.admin.network.RetrofitClient

class AdminRepository {
    private val api = RetrofitClient.apiService

    suspend fun getPaymentDueSoon(): ApiListResponse {
        return api.fetchList(ApiConfig.PAYMENT_SCRIPT_URL, "getDueSoon")
    }

    suspend fun getWhoOwes(): ApiListResponse {
        return api.fetchList(ApiConfig.PAYMENT_SCRIPT_URL, "getWhoOwes")
    }

    suspend fun getReminderNoticeList(): ApiListResponse {
        return api.fetchList(ApiConfig.PAYMENT_SCRIPT_URL, "getReminderNoticeList")
    }

    suspend fun getOverdueNoticeList(): ApiListResponse {
        return api.fetchList(ApiConfig.PAYMENT_SCRIPT_URL, "getOverdueNoticeList")
    }

    suspend fun getPendingRegistrations(): ApiListResponse {
        return api.fetchList(ApiConfig.REGISTRATION_SCRIPT_URL, "getPendingRegistrations")
    }

    suspend fun getRoster(location: String = "", className: String = ""): ApiListResponse {
        return api.fetchList(ApiConfig.REGISTRATION_SCRIPT_URL, "getRoster", location, className)
    }

    suspend fun getBeltTestList(): ApiListResponse {
        return api.fetchList(ApiConfig.BELT_SCRIPT_URL, "getBeltTestList")
    }

    suspend fun sendReminder(id: String): ApiActionResponse {
        return api.postAction(ApiConfig.PAYMENT_SCRIPT_URL, "sendReminderEmail", id = id)
    }

    suspend fun sendOverdue(id: String): ApiActionResponse {
        return api.postAction(ApiConfig.PAYMENT_SCRIPT_URL, "sendOverdueEmail", id = id)
    }

    suspend fun approveRegistration(id: String, className: String): ApiActionResponse {
        return api.postAction(ApiConfig.REGISTRATION_SCRIPT_URL, "approveRegistration", id = id, className = className)
    }

    suspend fun updateRegistration(
        id: String,
        name: String,
        email: String,
        location: String,
        className: String
    ): ApiActionResponse {
        return api.postAction(
            ApiConfig.REGISTRATION_SCRIPT_URL,
            "updateRegistration",
            id = id,
            name = name,
            email = email,
            location = location,
            className = className
        )
    }

    suspend fun sendCompleteEmail(id: String): ApiActionResponse {
        return api.postAction(ApiConfig.REGISTRATION_SCRIPT_URL, "sendCompleteEmail", id = id)
    }

    suspend fun sendOneMoreStepEmail(id: String): ApiActionResponse {
        return api.postAction(ApiConfig.REGISTRATION_SCRIPT_URL, "sendOneMoreStepEmail", id = id)
    }

    suspend fun sendNotCompleteEmail(id: String): ApiActionResponse {
        return api.postAction(ApiConfig.REGISTRATION_SCRIPT_URL, "sendNotCompleteEmail", id = id)
    }

    suspend fun updateBelts(id: String, currentBelt: String, nextBelt: String): ApiActionResponse {
        return api.postAction(
            ApiConfig.BELT_SCRIPT_URL,
            "updateBelts",
            id = id,
            currentBelt = currentBelt,
            nextBelt = nextBelt
        )
    }

    suspend fun sendBeltInvite(id: String): ApiActionResponse {
        return api.postAction(ApiConfig.BELT_SCRIPT_URL, "sendBeltInvite", id = id)
    }

    suspend fun sendBlast(location: String, className: String, subject: String, message: String): ApiActionResponse {
        return api.postAction(
            ApiConfig.COMMUNICATION_SCRIPT_URL,
            "sendBlast",
            location = location,
            className = className,
            subject = subject,
            message = message
        )
    }
}