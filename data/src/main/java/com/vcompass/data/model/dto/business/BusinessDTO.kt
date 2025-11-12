package com.vcompass.data.model.dto.business

import com.google.gson.annotations.SerializedName
import com.vcompass.data.model.dto.BaseDTO

open class BusinessDTO(
    @SerializedName("partnerId")
    val partnerId: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("location")
    val location: LocationDTO? = null,
    @SerializedName("images")
    val images: List<String>? = null,
    @SerializedName("amenities")
    val amenities: List<String>? = null,
    @SerializedName("contact")
    val contact: ContactInformationDTO? = null,
    @SerializedName("registerDate")
    val registerDate: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("averageRating")
    val averageRating: Float? = null,
    @SerializedName("totalRatings")
    val totalRatings: Float? = null,
) : BaseDTO()
