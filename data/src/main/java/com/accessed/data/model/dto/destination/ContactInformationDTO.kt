package com.accessed.data.model.dto.destination

import com.accessed.data.model.dto.MapperDTO
import com.accessed.domain.model.response.destination.ContactInformation
import com.google.gson.annotations.SerializedName

data class ContactInformationDTO(
    @SerializedName("phone")
    val phone: String?,
    @SerializedName("email")
    val email: String?
) : MapperDTO<ContactInformation> {
    override fun toDomain(): ContactInformation {
        return ContactInformation(
            phone = phone,
            email = email
        )
    }
}