package com.natiqhaciyef.artapptesting.repository

import androidx.lifecycle.LiveData
import com.natiqhaciyef.artapptesting.retrofit.api.PixabayModel
import com.natiqhaciyef.artapptesting.roomdb.ArtModel
import com.natiqhaciyef.artapptesting.util.Resource

interface ArtRepository {

    suspend fun insertArt(art: ArtModel)

    suspend fun deleteArt(art: ArtModel)

    fun getArt(): LiveData<List<ArtModel>>

    suspend fun imageSearch(imageString: String): Resource<PixabayModel>
}