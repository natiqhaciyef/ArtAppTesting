package com.natiqhaciyef.artapptesting.dependencyinjection

import android.content.Context
import androidx.navigation.Navigator
import androidx.room.Room
import com.natiqhaciyef.artapptesting.roomdb.ArtDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named("TestDatabase")
    fun injectInRules(@ApplicationContext context: Context) =Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}