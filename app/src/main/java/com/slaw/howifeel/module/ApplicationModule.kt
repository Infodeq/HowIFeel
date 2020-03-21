package com.slaw.howifeel.module


import com.slaw.howifeel.HowIFeelApplication
import com.slaw.howifeel.data.DataManager
import com.slaw.howifeel.data.DataManagerImp
import com.slaw.howifeel.data.api.ApiManager
import com.slaw.howifeel.data.api.ApiManagerImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: HowIFeelApplication) {

    @Provides
    @Singleton
    fun provideDataManager(dataManagerImp: DataManagerImp) = dataManagerImp as DataManager

    @Provides
    @Singleton
    fun provideApiManager(apiManagerImpl: ApiManagerImpl) = apiManagerImpl as ApiManager

//    @Provides
//    @Singleton
//    fun sharedPrefManager(sharedPrefManagerImp: SharedPrefManagerImp) = sharedPrefManagerImp as SharedPrefManager
//
//    @Provides
//    @Singleton
//    fun provideSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(application)
//
//    @Provides
//    @Singleton
//    fun provideDatabaseManager(databaseManagerImp: DatabaseManagerImp) = databaseManagerImp as DatabaseManager
//
//    @Provides
//    @Singleton
//    fun provideRewiredDatabase() = RewiredDatabase.getInstance(application)
//
//    @Provides
//    @Singleton
//    fun provideRewiredDao(rewiredDatabase: RewiredDatabase) = rewiredDatabase.rewiredDao()
//
//    @Provides
//    @Singleton
//    fun provideScoreUpdater(scoreUpdaterImp: ScoreUpdaterImp): ScoreUpdater = scoreUpdaterImp
//
//    @Provides
//    @Singleton
//    fun provideApiManager(apiManagerImpl: ApiManagerImpl): ApiManager = apiManagerImpl
//
//    @Provides
//    @Singleton
//    fun provideFirebaseAnalytics() = FirebaseAnalytics.getInstance(application)
//
//    @Provides
//    @Singleton
//    fun provideRewiredAnalytics(rewiredAnalyticsImp: RewiredAnalyticsImp): RewiredAnalytics = rewiredAnalyticsImp

}