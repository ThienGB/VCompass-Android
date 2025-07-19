//package com.example.gotravel.di.module
//
//import android.app.Application
//import android.content.Context
//import com.example.gotravel.R
//import com.example.gotravel.data.local.dao.AccommodationDao
//import com.example.gotravel.data.local.dao.AccommodationDaoImpl
//import com.example.gotravel.helper.RealmHelper
//import dagger.Module
//import dagger.Provides
//import javax.inject.Named
//import javax.inject.Singleton
//
//@Module
//class AppModule(private val context: Context) {
//    @Singleton
//    @Provides
//    fun provideContext(): Context {
//        return context
//    }
//
//    @Singleton
//    @Provides
//    fun provideApplication(): Application {
//        return context.applicationContext as Application
//    }
//
//    @Singleton
//    @Provides
//    fun provideRealmHelper(): RealmHelper {
//        return RealmHelper(context)
//    }
//
//    @Provides
//    fun provideAccommodationDao(realmHelper: RealmHelper?): AccommodationDao {
//        return AccommodationDaoImpl(realmHelper)
//    }
//
//    @Provides
//    fun provideAccommodationRepository(
//        accommodationDao: AccommodationDao?,
//        @Named("accomService") apiService: ApiService?
//    ): AccommodationRepository {
//        return AccommodationRepositoryImpl(accommodationDao, apiService)
//    }
//
//    @Provides
//    fun provideCityDao(realmHelper: RealmHelper?): CityDao {
//        return CitiyDaoImpl(realmHelper)
//    }
//
//    @Provides
//    fun provideCityRepository(
//        cityDao: CityDao?,
//        @Named("cityService") apiService: ApiService?
//    ): CityRepository {
//        return CityRepositoryImpl(cityDao, apiService)
//    }
//
//    @Provides
//    fun provideFavoriteDao(realmHelper: RealmHelper?): FavoriteDao {
//        return FavoriteDaoImpl(realmHelper)
//    }
//
//    @Provides
//    fun provideFavoriteRepository(
//        favoriteDao: FavoriteDao?,
//        accommodationDao: AccommodationDao?
//    ): FavoriteRepository {
//        return FavoriteRepositoryImpl(favoriteDao, accommodationDao)
//    }
//
//    @Provides
//    fun provideBookingDao(realmHelper: RealmHelper?): BookingDao {
//        return BookingDaoImpl(realmHelper)
//    }
//
//    @Provides
//    fun provideBookingRepository(
//        bookingDao: BookingDao?,
//        accommodationDao: AccommodationDao?
//    ): BookingRepository {
//        return BookingRepositoryImpl(bookingDao, accommodationDao)
//    }
//
//    @Provides
//    fun provideFirebaseInstance(): String {
//        return "https://mapdemo-b04e7-default-rtdb.asia-southeast1.firebasedatabase.app"
//    }
//
//    @Provides
//    fun provideFirebaseBookingRepository(firebaseInstance: String?): FirebaseBookingRepository {
//        return FirebaseBookingRepositoryImpl(firebaseInstance)
//    }
//
//    @Provides
//    fun provideFirebaseAuth(): FirebaseAuth {
//        return FirebaseAuth.getInstance()
//    }
//
//    @Provides
//    fun provideFirebaseFirestore(): FirebaseFirestore {
//        return FirebaseFirestore.getInstance()
//    }
//
//    @Provides
//    fun provideFirestoreManager(
//        db: FirebaseFirestore?,
//        listenerRegistrations: List<ListenerRegistration?>?
//    ): FirestoreDataManager {
//        return FirestoreDataManagerImpl(db, listenerRegistrations)
//    }
//
//    @Provides
//    fun provideListenerRegistrations(): List<ListenerRegistration> {
//        return ArrayList<ListenerRegistration>()
//    }
//
//    @Provides
//    fun provideGoogleSignInClient(application: Application): GoogleSignInClient {
//        val gso: GoogleSignInOptions = Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(application.getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//        return GoogleSignIn.getClient(application, gso)
//    }
//
//    @Provides
//    @Named("accommodation")
//    fun provideRetrofitAccom(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(RetrofitClient.ACCOM_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Named("city")
//    fun provideRetrofitCity(): Retrofit {
//        return Retrofit.Builder()
//            .baseUrl(RetrofitClient.CITY_BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Named("accomService")
//    fun provideAccommodationApiService(@Named("accommodation") retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }
//
//    @Provides
//    @Named("cityService")
//    fun provideCityApiService(@Named("city") retrofit: Retrofit): ApiService {
//        return retrofit.create(ApiService::class.java)
//    }
//}
