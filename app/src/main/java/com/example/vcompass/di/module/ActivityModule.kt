//package com.example.gotravel.di.module
//
//import dagger.Module
//import dagger.Provides
//import dagger.multibindings.IntoMap
//import javax.inject.Provider
//import javax.inject.Singleton
//
//@Module
//class ActivityModule {
//    @Provides
//    @Singleton
//    fun provideMyViewModelFactory(providerMap: Map<Class<out ViewModel?>?, Provider<ViewModel?>?>?): Factory {
//        return MyViewModelFactory(providerMap)
//    }
//
//    @Provides
//    fun provideUserCityListViewModelWithRepo(cityRepository: CityRepository?): UserCityListViewModel {
//        return UserCityListViewModel(cityRepository)
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(UserCityListViewModel::class)
//    fun provideUserCityListViewModel(viewModel: UserCityListViewModel): ViewModel {
//        return viewModel
//    }
//
//    @Provides
//    fun provideUserAccomListCityViewModelWithRepo(
//        accommodationRepository: AccommodationRepository?,
//        firebaseBookingRepository: FirebaseBookingRepository?,
//        firestoreDataManager: FirestoreDataManager?
//    ): UserAccomListCityViewModel {
//        return UserAccomListCityViewModel(
//            accommodationRepository,
//            firebaseBookingRepository,
//            firestoreDataManager
//        )
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(UserAccomListCityViewModel::class)
//    fun provideUserAccomListCityViewModel(viewModel: UserAccomListCityViewModel): ViewModel {
//        return viewModel
//    }
//
//    @Provides
//    fun provideUserFavoriteListViewModelWithRepo(
//        favoriteRepository: FavoriteRepository?,
//        firestoreDataManager: FirestoreDataManager?,
//        accommodationRepo: AccommodationRepository?,
//        firebaseAuth: FirebaseAuth?
//    ): UserFavoriteListViewModel {
//        return UserFavoriteListViewModel(
//            favoriteRepository,
//            firestoreDataManager, accommodationRepo, firebaseAuth
//        )
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(UserFavoriteListViewModel::class)
//    fun provideUserFavoriteListViewModel(viewModel: UserFavoriteListViewModel): ViewModel {
//        return viewModel
//    }
//
//    @Provides
//    fun provideUserBookingListViewModelWithRepo(
//        bookingRepo: BookingRepository?,
//        accomRepo: AccommodationRepository?, firebaseAuth: FirebaseAuth?
//    ): UserBookingListViewModel {
//        return UserBookingListViewModel(bookingRepo, accomRepo, firebaseAuth)
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(UserBookingListViewModel::class)
//    fun provideUserBookingListViewModel(viewModel: UserBookingListViewModel): ViewModel {
//        return viewModel
//    }
//
//    @Provides
//    fun provideAccomInforViewModelWithRepo(
//        accomRepo: AccommodationRepository?,
//        favoriteRepo: FavoriteRepository?,
//        bookingRepo: BookingRepository?,
//        firebaseBookingRepo: FirebaseBookingRepository?,
//        firestoreDataManager: FirestoreDataManager?,
//        firebaseAuth: FirebaseAuth?
//    ): AccomInforViewModel {
//        return AccomInforViewModel(
//            accomRepo, favoriteRepo, bookingRepo,
//            firebaseBookingRepo, firestoreDataManager, firebaseAuth
//        )
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(AccomInforViewModel::class)
//    fun provideAccomInforViewModel(viewModel: AccomInforViewModel): ViewModel {
//        return viewModel
//    }
//
//    @Provides
//    fun provideLoginViewModelWithRepo(
//        firebaseAuth: FirebaseAuth?,
//        googleSignInClient: GoogleSignInClient?
//    ): LoginViewModel {
//        return LoginViewModel(firebaseAuth, googleSignInClient)
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(LoginViewModel::class)
//    fun provideLoginViewModel(viewModel: LoginViewModel): ViewModel {
//        return viewModel
//    }
//
//    @Provides
//    fun provideRegisterViewModelWithRepo(firebaseAuth: FirebaseAuth?): RegisterViewModel {
//        return RegisterViewModel(firebaseAuth)
//    }
//
//    @Provides
//    @IntoMap
//    @ViewModelKey(RegisterViewModel::class)
//    fun provideRegisterViewModel(viewModel: RegisterViewModel): ViewModel {
//        return viewModel
//    }
//}
