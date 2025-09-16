package com.vcompass.data.model.dto.schedule

import com.vcompass.data.model.dto.BaseDTO
import com.vcompass.data.model.dto.MapperDTO
import com.vcompass.data.model.dto.destination.accommodation.AccommodationDTO
import com.vcompass.data.model.dto.destination.attraction.AttractionDTO
import com.vcompass.data.model.dto.destination.foodservice.FoodServiceDTO
import com.vcompass.data.model.dto.withBase
import com.vcompass.domain.model.response.destination.Destination
import com.vcompass.domain.model.response.schedule.ScheduleActivity
import com.google.gson.annotations.SerializedName

data class ScheduleActivityDTO(
    @SerializedName("activityType")
    val activityType: String,
    @SerializedName("idDestination")
    val idDestination: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("address")
    val address: String?,
    @SerializedName("imgSrc")
    val imgSrc: List<String>?,
    @SerializedName("cost")
    val cost: Int?,
    @SerializedName("costDescription")
    val costDescription: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("timeStart")
    val timeStart: String?,
    @SerializedName("timeEnd")
    val timeEnd: String?,
    @SerializedName("destination")
    val destination: Any?
): BaseDTO(), MapperDTO<ScheduleActivity> {
    override fun toDomain(): ScheduleActivity {
        val domainDestination: Destination? = when (activityType) {
            "food" -> (destination as? FoodServiceDTO)?.toDomain()
            "accommodation" -> (destination as? AccommodationDTO)?.toDomain()
            "attraction" -> (destination as? AttractionDTO)?.toDomain()
            else -> null
        }
        return ScheduleActivity(
            activityType = this.activityType,
            idDestination = this.idDestination,
            name = this.name,
            address = this.address,
            imgSrc = this.imgSrc,
            cost = this.cost,
            costDescription = this.costDescription,
            description = this.description,
            timeStart = this.timeStart,
            timeEnd = this.timeEnd,
            destination = domainDestination
        ).withBase(this)
    }
}

