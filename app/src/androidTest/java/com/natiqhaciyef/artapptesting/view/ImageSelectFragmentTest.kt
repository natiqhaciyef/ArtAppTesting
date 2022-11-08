package com.natiqhaciyef.artapptesting.view

import com.natiqhaciyef.artapptesting.R
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.google.common.truth.Truth.assertThat
import com.natiqhaciyef.artapptesting.launchFragmentInHiltContainer
import com.natiqhaciyef.artapptesting.repository.FakeArtRepository
import com.natiqhaciyef.artapptesting.roomdb.ArtModel
import com.natiqhaciyef.artapptesting.util.getOrAwaitValue
import com.natiqhaciyef.artapptesting.viewmodel.BaseViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import androidx.test.filters.MediumTest
import com.natiqhaciyef.artapptesting.adapter.ImageApiAdapter
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ImageSelectFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory : ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = BaseViewModel(FakeArtRepository())
        launchFragmentInHiltContainer<ImageSelectFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewModel
            adapter.imageList = listOf("test1.com", "test2.com")
        }
        Espresso.onView(ViewMatchers.withId(R.id.imageSelectRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageApiAdapter.ImageViewHolder>(0, ViewActions.click())
        )
        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.chosenImageURL.getOrAwaitValue()).isEqualTo("test1.com")
    }
}