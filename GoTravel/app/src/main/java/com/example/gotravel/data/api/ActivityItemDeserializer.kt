package com.example.gotravel.data.api

import com.example.gotravel.data.api.model.Accommodation
import com.example.gotravel.data.api.model.ActivityItem
import com.example.gotravel.data.api.model.Attraction
import com.example.gotravel.data.api.model.FoodService
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class ActivityItemDeserializer : JsonDeserializer<ActivityItem> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): ActivityItem {
        val jsonObject = json.asJsonObject
        val activityType = jsonObject["activityType"].asString
        val destinationJson = jsonObject["destination"]
        val destination: Any? = when (activityType) {
            "Accommodation" -> {
                if (!destinationJson.isJsonNull) {
                    context.deserialize<Accommodation>(destinationJson, Accommodation::class.java)
                } else null
            }
            "Attraction" -> {
                if (!destinationJson.isJsonNull) {
                    context.deserialize<Attraction>(destinationJson, Attraction::class.java)
                } else null
            }
            "FoodService" -> {
                if (!destinationJson.isJsonNull) {
                    context.deserialize<FoodService>(destinationJson, FoodService::class.java)
                } else null
            }
            else -> null
        }

        return ActivityItem(
            activityType = activityType,
            idDestination = jsonObject["idDestination"]?.asString,
            name = jsonObject["name"]?.asString,
            address = jsonObject["address"]?.asString,
            imgSrc = context.deserialize(jsonObject["imgSrc"], object : TypeToken<List<String>>() {}.type),
            cost = jsonObject["cost"]?.asInt,
            costDescription = jsonObject["costDescription"]?.asString,
            description = jsonObject["description"]?.asString,
            timeStart = jsonObject["timeStart"]?.asString,
            timeEnd = jsonObject["timeEnd"]?.asString,
            id = jsonObject["_id"]?.asString,
            destination = destination
        )
    }

}
