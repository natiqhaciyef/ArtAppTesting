package com.natiqhaciyef.artapptesting.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.natiqhaciyef.artapptesting.adapter.ArtAdapter
import com.natiqhaciyef.artapptesting.adapter.ImageApiAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artAdapter: ArtAdapter,
    private val imageAdapter: ImageApiAdapter,
    private val glide : RequestManager
): FragmentFactory() {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtFragment::class.java.name -> ArtFragment(artAdapter)
            ImageSelectFragment::class.java.name -> ImageSelectFragment(imageAdapter)
            DetailsFragment::class.java.name -> DetailsFragment(glide)
            else -> super.instantiate(classLoader, className)
        }
    }
}