package com.natiqhaciyef.artapptesting.view

import com.natiqhaciyef.artapptesting.R
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.natiqhaciyef.artapptesting.launchFragmentInHiltContainer
import com.natiqhaciyef.artapptesting.repository.ArtRepository
import com.natiqhaciyef.artapptesting.repository.FakeArtRepository
import com.natiqhaciyef.artapptesting.roomdb.ArtModel
import com.natiqhaciyef.artapptesting.util.getOrAwaitValue
import com.natiqhaciyef.artapptesting.viewmodel.BaseViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class DetailsFragmentTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun `navigationTestDetailsFragmentToImageSelectFragment`(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artImageViewDetails)).perform(ViewActions.click())
        Mockito.verify(navController).navigate(
            DetailsFragmentDirections.actionDetailsFragmentToImageSelectFragment()
        )
    }

    @Test
    fun `navigationTestDetailsFragmentOnBackPressed`(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<DetailsFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack()
    }

    @Test
    fun `saveButtonTesting`(){
        val testViewModel = BaseViewModel(FakeArtRepository())
        launchFragmentInHiltContainer<DetailsFragment>(
            factory = fragmentFactory
        ){
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.artNameDetails)).perform(ViewActions.replaceText("Mona Lisa"))
        Espresso.onView(ViewMatchers.withId(R.id.artistNameDetails)).perform(ViewActions.replaceText("Leonardo Da Vinci"))
        Espresso.onView(ViewMatchers.withId(R.id.artYearDetails)).perform(ViewActions.replaceText("1503"))
        Espresso.onView(ViewMatchers.withId(R.id.saveButtonDetails)).perform(ViewActions.click())

        assertThat(testViewModel.artList.getOrAwaitValue())
            .contains(ArtModel("Mona Lisa","Leonardo Da Vinci", 1503, ""))

    }
}