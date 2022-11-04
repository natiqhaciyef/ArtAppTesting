package com.natiqhaciyef.artapptesting.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArtModel(
    var artName: String,
    var artistName: String,
    var artYear: Int,
    var imageUrl : String,
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
)
