package com.vcompass.data.model.dto.destination.foodservice

import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.data.model.dto.destination.DestinationDTO
import com.vcompass.data.model.dto.destination.attraction.OperatingHourDTO
import com.vcompass.data.model.dto.destination.withDestination
import com.vcompass.domain.model.response.destination.foodservice.FoodService
import com.google.gson.annotations.SerializedName

data class FoodServiceDTO(
    @SerializedName("idPartner")
    val idPartner: String?,
    @SerializedName("serviceType")
    val serviceType: String?,
    @SerializedName("foodServiceName")
    val foodServiceName: String?,
    @SerializedName("price")
    val price: PriceRangeDTO?,
    @SerializedName("menuImages")
    val menuImages: List<String>?,
    @SerializedName("contactNumber")
    val contactNumber: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("operatingHours")
    val operatingHours: List<OperatingHourDTO>?
) : DestinationDTO(), MapperDTO<FoodService> {
    override fun toDomain(): FoodService {
        return FoodService(
            idPartner = idPartner,
            serviceType = serviceType,
            foodServiceName = foodServiceName,
            price = price?.toDomain(),
            menuImages = menuImages,
            contactNumber = contactNumber,
            email = email,
            operatingHours = operatingHours?.map { it.toDomain() }
        ).withDestination(this)
    }
}

