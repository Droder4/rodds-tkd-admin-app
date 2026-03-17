package com.roddstkd.admin.network

import com.roddstkd.admin.model.AdminInitResponse
import com.roddstkd.admin.model.ApiActionResponse
import com.roddstkd.admin.model.BeltTestingResponse
import com.roddstkd.admin.model.GenericRequest
import com.roddstkd.admin.model.PaymentListResponse
import com.roddstkd.admin.model.RegistrationListResponse
import com.roddstkd.admin.model.RosterResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @POST
    suspend fun postPaymentList(
        @Url url: String,
        @Body request: GenericRequest
    ): PaymentListResponse

    @POST
    suspend fun postRegistrationList(
        @Url url: String,
        @Body request: GenericRequest
    ): RegistrationListResponse

    @POST
    suspend fun postAdminInit(
        @Url url: String,
        @Body request: GenericRequest
    ): AdminInitResponse

    @POST
    suspend fun postRoster(
        @Url url: String,
        @Body request: GenericRequest
    ): RosterResponse

    @POST
    suspend fun postBeltTesting(
        @Url url: String,
        @Body request: GenericRequest
    ): BeltTestingResponse

    @POST
    suspend fun postAction(
        @Url url: String,
        @Body request: GenericRequest
    ): ApiActionResponse
}
