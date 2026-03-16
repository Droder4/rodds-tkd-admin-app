package com.roddstkd.admin.model

data class PersonRecord(
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

data class ApiListResponse(
    val success: Boolean = false,
    val message: String = "",
    val items: List<PersonRecord> = emptyList()
)

data class ApiActionResponse(
    val success: Boolean = false,
    val message: String = ""
)