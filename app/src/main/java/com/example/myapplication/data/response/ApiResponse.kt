package com.example.myapplication.data.response

import com.google.gson.annotations.SerializedName

data class ApiResponse (
    @SerializedName("result")
    var result: String? = null,
    @SerializedName("provider")
    var provider: String? = null,
    @SerializedName("documentation")
    var documentation: String? = null,
    @SerializedName("terms_of_use")
    var termsOfUse: String? = null,
    @SerializedName("time_last_update_unix")
    var timeLastUpdateUnix: Int? = null,
    @SerializedName("time_last_update_utc")
    var timeLastUpdateUtc: String? = null,
    @SerializedName("time_next_update_unix")
    var timeNextUpdateUnix: Int? = null,
    @SerializedName("time_next_update_utc")
    var timeNextUpdateUtc: String? = null,
    @SerializedName("time_eol_unix")
    var timeEolUnix: Int? = null,
    @SerializedName("base_code")
    var baseCode: String? = null,
    @SerializedName("rates")
    var rates: Rates? = Rates()
)