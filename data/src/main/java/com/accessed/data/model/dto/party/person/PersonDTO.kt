package com.accessed.data.model.dto.party.person

import com.accessed.data.model.dto.party.ParentPartyDTO
import com.accessed.data.model.dto.party.primary.PrimaryEmailDTO
import com.accessed.data.model.dto.party.primary.PrimaryPhoneDTO
import com.google.gson.annotations.SerializedName

class PersonDTO : ParentPartyDTO() {

    @SerializedName("headline")
    val headline: String? = null

    @SerializedName("about")
    val about: String? = null

    @SerializedName("firstName")
    val firstName: String? = null

    @SerializedName("lastName")
    val lastName: String? = null

    @SerializedName("middleName")
    val middleName: String? = null

    @SerializedName("dob")
    val dob: String? = null

    @SerializedName("gender")
    val gender: String? = null

    @SerializedName("maritalStatus")
    val maritalStatus: String? = null

    @SerializedName("messengerId")
    val messengerId: String? = null

    @SerializedName("calendarUserId")
    val calendarUserId: String? = null

    @SerializedName("calendarId")
    val calendarId: String? = null

    @SerializedName("jobTitle")
    val jobTitle: String? = null

    @SerializedName("primaryEmail")
    val primaryEmail: PrimaryEmailDTO? = null

    @SerializedName("primaryPhone")
    val primaryPhone: PrimaryPhoneDTO? = null
}