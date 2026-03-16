package com.roddstkd.admin.network

import com.roddstkd.admin.model.ApiActionResponse
import com.roddstkd.admin.model.ApiListResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Url

interface ApiService {
    @FormUrlEncoded
    @POST
    suspend fun postAction(
        @Url url: String,
        @Field("action") action: String,
        @Field("id") id: String = "",
        @Field("name") name: String = "",
        @Field("email") email: String = "",
        @Field("location") location: String = "",
        @Field("className") className: String = "",
        @Field("currentBelt") currentBelt: String = "",
        @Field("nextBelt") nextBelt: String = "",
        @Field("subject") subject: String = "",
        @Field("message") message: String = ""
    ): ApiActionResponse

    @FormUrlEncoded
    @POST
    suspend fun fetchList(
        @Url url: String,
        @Field("action") action: String,
        @Field("location") location: String = "",
        @Field("className") className: String = ""
    ): ApiListResponse
}