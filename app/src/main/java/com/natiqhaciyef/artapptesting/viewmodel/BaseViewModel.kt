package com.natiqhaciyef.artapptesting.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.natiqhaciyef.artapptesting.util.Resource
import com.natiqhaciyef.artapptesting.repository.ArtRepository
import com.natiqhaciyef.artapptesting.retrofit.api.PixabayModel
import com.natiqhaciyef.artapptesting.roomdb.ArtModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BaseViewModel @Inject constructor(
    private val artRepository: ArtRepository
) : ViewModel() {

    // Art Main Fragment
    val artList = artRepository.getArt()

    // Pixabay API Fragment
    private val chosenImage = MutableLiveData<String>()
    val chosenImageURL: LiveData<String>
        get() = chosenImage

    private val images = MutableLiveData<Resource<PixabayModel>>()
    val imageList: LiveData<Resource<PixabayModel>>
        get() = images

    // Art Details Fragment
    private var insertArtMessage = MutableLiveData<Resource<ArtModel>>()
    val insertArtMessageString: LiveData<Resource<ArtModel>>
        get() = insertArtMessage

    fun resetInsertArtMessage() {
        insertArtMessage = MutableLiveData<Resource<ArtModel>>()
    }


    fun setChosenImage(url: String) {
        chosenImage.postValue(url)
    }

    fun deleteArt(art: ArtModel) = viewModelScope.launch {
        artRepository.deleteArt(art)
    }

    fun insertArt(art: ArtModel) = viewModelScope.launch {
        artRepository.insertArt(art)
    }

    fun searchImage(search: String) {
        if (search.isNotEmpty()) {
            images.value = Resource.loading(null)
            viewModelScope.launch {
                val responseSearch = artRepository.imageSearch(search)
                images.value = responseSearch
            }
        }
    }

    fun createArt(name: String, artist: String, year: Int){
        if (name.isNotEmpty() && artist.isNotEmpty() && year != 0){
            val art = ArtModel(name, artist, year, chosenImage.value?:"")
            insertArt(art)
            setChosenImage("")
            insertArtMessage.postValue(Resource.success(art))
        }else{
            insertArtMessage.postValue(Resource.error("Fill empty blanks", null))
            return
        }
    }
}