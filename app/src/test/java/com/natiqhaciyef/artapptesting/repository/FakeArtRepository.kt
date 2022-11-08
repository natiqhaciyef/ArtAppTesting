package com.natiqhaciyef.artapptesting.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.natiqhaciyef.artapptesting.retrofit.api.PixabayModel
import com.natiqhaciyef.artapptesting.roomdb.ArtModel
import com.natiqhaciyef.artapptesting.util.Resource

class FakeArtRepository : ArtRepository {
    private val arts = mutableListOf<ArtModel>()
    private val artLiveData = MutableLiveData<List<ArtModel>>(arts)

    override suspend fun insertArt(art: ArtModel) {
        arts.add(art)
        checkData()
    }

    override suspend fun deleteArt(art: ArtModel) {
        arts.remove(art)
        checkData()
    }

    override fun getArt(): LiveData<List<ArtModel>> {
        return artLiveData
    }

    override suspend fun imageSearch(imageString: String): Resource<PixabayModel> {
        return Resource.success(PixabayModel(0, 0, listOf()))
    }

    private fun checkData(){
        artLiveData.postValue(arts)
    }
}