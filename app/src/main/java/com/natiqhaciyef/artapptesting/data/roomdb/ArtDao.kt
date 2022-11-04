package com.natiqhaciyef.artapptesting.data.roomdb

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArtDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArt(art: ArtModel)

    @Delete
    suspend fun deleteArt(art: ArtModel)

    @Query("SELECT * FROM ArtModel")
    fun getAll(): LiveData<List<ArtModel>>
}