package com.accessed.data.model.dto.party.login

import com.accessed.data.model.dto.BaseDTO
import com.google.gson.annotations.SerializedName

open class LoginUserDTO() : BaseDTO() {
    @SerializedName("username")
    val username: String? = null

    @SerializedName("firstName")
    val firstName: String? = null

    @SerializedName("lastName")
    val lastName: String? = null

    @SerializedName("enable")
    val enable: Boolean? = null
}