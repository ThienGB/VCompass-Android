package com.example.vcompass.enum

import com.example.vcompass.R

enum class ActivityTypeEnum(val titleRes: Int, val iconRes: Int) {
    ACCOMMODATION(R.string.lb_accommodation, R.drawable.ic_accommodation_24dp),
    FOODPLACE(R.string.lb_food_place, R.drawable.ic_food),
    ATTRACTION(R.string.lb_attraction, R.drawable.ic_beach_24dp),
    OTHER(R.string.lb_other, R.drawable.ic_notification);

    companion object {
        fun getType(type: String?): ActivityTypeEnum {
            return entries.find { it.name == type } ?: OTHER
        }
    }
}