package com.natiqhaciyef.artapptesting.repository

import androidx.lifecycle.LiveData
import com.natiqhaciyef.artapptesting.retrofit.api.PixabayAPI
import com.natiqhaciyef.artapptesting.retrofit.api.PixabayModel
import com.natiqhaciyef.artapptesting.roomdb.ArtDao
import com.natiqhaciyef.artapptesting.roomdb.ArtModel
import com.natiqhaciyef.artapptesting.util.Resource
import retrofit2.Response
import javax.inject.Inject

class ArtRepositoryImpl @Inject constructor(
    private val artDao: ArtDao,
    private val api: PixabayAPI
) : ArtRepository {
    override suspend fun insertArt(art: ArtModel) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: ArtModel) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<ArtModel>> {
        return artDao.getAll()
    }

    override suspend fun imageSearch(imageString: String): Resource<PixabayModel> {
        return try {
            val data = api.imageSearch(searchQue = imageString)
            if (data.isSuccessful) {
                data.body()?.let { return@let Resource.success(it) }
                    ?: Resource.error("Data was null !", null)
            } else
                Resource.error("Something went wrong", null)

        } catch (e: Exception) {
            return Resource.error("API request not working !", null)
        }
    }
}