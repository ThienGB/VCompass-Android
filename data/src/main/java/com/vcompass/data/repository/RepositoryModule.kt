package com.vcompass.data.repository

import com.vcompass.data.repository.accommodation.AccommodationRepositoryImpl
import com.vcompass.data.repository.attraction.AttractionRepositoryImpl
import com.vcompass.data.repository.location.AndroidGeocodingDataSource
import com.vcompass.domain.repository.location.GeocodingDataSource
import com.vcompass.data.repository.common.StatusLoginRepositoryImpl
import com.vcompass.data.repository.foodplace.FoodPlaceRepositoryImpl
import com.vcompass.data.repository.location.LocationRepositoryImpl
import com.vcompass.data.repository.login.LoginRepositoryImpl
import com.vcompass.data.repository.schedule.ScheduleRepositoryImpl
import com.vcompass.domain.repository.accommodation.AccommodationRepository
import com.vcompass.domain.repository.attraction.AttractionRepository
import com.vcompass.domain.repository.common.StatusLoginRepository
import com.vcompass.domain.repository.foodplace.FoodPlaceRepository
import com.vcompass.domain.repository.location.LocationRepository
import com.vcompass.domain.repository.login.LoginRepository
import com.vcompass.domain.repository.schedule.ScheduleRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<LoginRepository> { LoginRepositoryImpl(get(), get()) }
    single<AccommodationRepository>{ AccommodationRepositoryImpl(get()) }
    single<AttractionRepository>{ AttractionRepositoryImpl(get()) }
    single<FoodPlaceRepository>{ FoodPlaceRepositoryImpl(get()) }
    single<ScheduleRepository>{ ScheduleRepositoryImpl(get()) }


    /////////////////////---/////////////////////


    single<StatusLoginRepository> { StatusLoginRepositoryImpl(get()) }
    single<LocationRepository>{ LocationRepositoryImpl(get()) }
    single<GeocodingDataSource>{ AndroidGeocodingDataSource(get()) }
}