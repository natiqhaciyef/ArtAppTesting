package com.natiqhaciyef.artapptesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.natiqhaciyef.artapptesting.util.MainCoroutineRule
import com.natiqhaciyef.artapptesting.util.getOrAwaitValue
import com.natiqhaciyef.artapptesting.repository.FakeArtRepository
import com.natiqhaciyef.artapptesting.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: BaseViewModel

    @Before
    fun setup(){
        // Test Doubles
        viewModel = BaseViewModel(FakeArtRepository())
    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.createArt("","Leonardo Da Vinci",1503)
        val data = viewModel.insertArtMessageString.getOrAwaitValue()
        assertThat(data.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artist returns error`(){
        viewModel.createArt("Mona Lisa","",1503)
        val data = viewModel.insertArtMessageString.getOrAwaitValue()
        assertThat(data.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art testing returns success`(){
        viewModel.createArt("Mona Lisa","Leonardo Da Vinci",1503)
        val data = viewModel.insertArtMessageString.getOrAwaitValue()
        assertThat(data.status).isEqualTo(Status.SUCCESS)
    }
}