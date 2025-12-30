package com.vcompass.data.model.dto.user

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.domain.model.response.user.UserModel
import com.vcompass.domain.util.tryParseObject

data class UserDTO(
    @SerializedName("name")
    val name: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("avatar")
    val avatar: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("dateOfBirth")
    val dateOfBirth: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("favorites")
    val favorites: UserFavoriteDTO?,
): BaseDTO()

fun UserDTO.toUserModel(): UserModel {
    return tryParseObject<UserModel>() ?: UserModel()
}