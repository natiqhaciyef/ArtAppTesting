package com.natiqhaciyef.artapptesting.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ArtModel::class], version = 1)
abstract class ArtDatabase: RoomDatabase() {
    abstract fun artDao(): ArtDao
}