package com.vcompass.presentation.viewmodel.search

import androidx.paging.PagingData
import com.vcompass.domain.usecase.accommodation.GetAccommodationsUseCase
import com.vcompass.domain.usecase.attraction.GetAttractionsUseCase
import com.vcompass.domain.usecase.foodplace.GetFoodPlacesUseCase
import com.vcompass.domain.usecase.location.GetCurrentLocationUseCase
import com.vcompass.presentation.event.global.GlobalConfig
import com.vcompass.presentation.event.global.GlobalEventBus
import com.vcompass.presentation.model.business.accommodation.Accommodation
import com.vcompass.presentation.model.business.accommodation.toAccommodation
import com.vcompass.presentation.model.business.attraction.Attraction
import com.vcompass.presentation.model.business.attraction.toAttraction
import com.vcompass.presentation.model.business.foodplace.FoodPlace
import com.vcompass.presentation.model.business.foodplace.toFoodPlace
import com.vcompass.presentation.model.location.AppLocation
import com.vcompass.presentation.model.location.toAppLocation
import com.vcompass.presentation.util.collectPagingToState
import com.vcompass.presentation.util.collectToState
import com.vcompass.presentation.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MapSearchViewModel(
    globalEventBus: GlobalEventBus,
    globalConfig: GlobalConfig,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getAccommodationsUseCase: GetAccommodationsUseCase,
    private val getAttractionsUseCase: GetAttractionsUseCase,
    private val getFoodPlacesUseCase: GetFoodPlacesUseCase
) : BaseViewModel(globalEventBus, globalConfig) {
    private val _location = MutableStateFlow<AppLocation?>(null)
    val location = _location.asStateFlow()

    private val _accommodations = MutableStateFlow<PagingData<Accommodation>>(PagingData.empty())
    val accommodations = _accommodations.asStateFlow()

    private val _attractions = MutableStateFlow<PagingData<Attraction>>(PagingData.empty())
    val attractions = _attractions.asStateFlow()

    private val _foodPlaces = MutableStateFlow<PagingData<FoodPlace>>(PagingData.empty())
    val foodPlaces = _foodPlaces.asStateFlow()

    init {
        loadCurrentLocation()
    }

    fun loadCurrentLocation() {
        collectToState(
            block = { getCurrentLocationUseCase() }
        ) {
            _location.value = it.toAppLocation()
        }
    }

    fun getAccommodations(city: String?) {
        if (city.isNullOrBlank()) return
        collectPagingToState(
            source = getAccommodationsUseCase(
                search = "",
                latitude = _location.value?.latitude,
                longitude = _location.value?.longitude
            ),
            transform = { it.toAccommodation() }
        ) {
            _accommodations.value = it
        }
    }

    fun getAttractions(city: String?) {
        if (city.isNullOrBlank()) return
        collectPagingToState(
            source = getAttractionsUseCase(
                search = "",
                latitude = _location.value?.latitude,
                longitude = _location.value?.longitude
            ),
            transform = { it.toAttraction() }
        ) {
            _attractions.value = it
        }
    }

    fun getFoodPlaces(city: String?) {
        if (city.isNullOrBlank()) return
        collectPagingToState(
            source = getFoodPlacesUseCase(
                search = "",
                latitude = _location.value?.latitude,
                longitude = _location.value?.longitude
            ),
            transform = { it.toFoodPlace() }
        ) {
            _foodPlaces.value = it
        }
    }
}