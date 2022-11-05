package com.natiqhaciyef.artapptesting.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.natiqhaciyef.artapptesting.R
import com.natiqhaciyef.artapptesting.repository.ArtRepository
import com.natiqhaciyef.artapptesting.repository.ArtRepositoryImpl
import com.natiqhaciyef.artapptesting.retrofit.api.PixabayAPI
import com.natiqhaciyef.artapptesting.retrofit.util.RetrofitDetails.BASE_URL
import com.natiqhaciyef.artapptesting.roomdb.ArtDao
import com.natiqhaciyef.artapptesting.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// SingletonComponent - Application Component
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDB(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, ArtDatabase::class.java, "ArtDatabase").build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofit(): PixabayAPI =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(PixabayAPI::class.java)

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) =
        Glide.with(context)
            .setDefaultRequestOptions(RequestOptions()
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
            )

    @Singleton
    @Provides
    fun injectRepository(artDao:ArtDao, api: PixabayAPI) = ArtRepositoryImpl(artDao, api) as ArtRepository

}