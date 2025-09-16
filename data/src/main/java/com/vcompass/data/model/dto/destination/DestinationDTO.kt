package com.vcompass.data.model.dto.destination

import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.rating.RatingDTO
import com.vcompass.data.model.dto.withBase
import com.vcompass.domain.model.response.destination.Destination
import com.google.gson.annotations.SerializedName

open class DestinationDTO(
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("location")
    val location: LocationDTO? = null,
    @SerializedName("city")
    val city: String? = null,
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
    @SerializedName("ratings")
    val ratings: List<RatingDTO>? = null
) : BaseDTO()

fun <T : Destination> T.withDestination(dto: DestinationDTO): T {
    this.withBase(dto)
    this.name = dto.name
    this.description = dto.description
    this.location = dto.location?.toDomain()
    this.city = dto.city
    this.images = dto.images
    this.amenities = dto.amenities
    this.contact = dto.contact?.toDomain()
    this.registerDate = dto.registerDate
    this.status = dto.status
    this.ratings = dto.ratings?.map { it.toDomain() }

    return this
}