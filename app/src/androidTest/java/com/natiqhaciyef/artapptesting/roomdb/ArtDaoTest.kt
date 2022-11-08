package com.natiqhaciyef.artapptesting.roomdb

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.natiqhaciyef.artapptesting.util.getOrAwaitValue
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    @Named("TestDatabase")
    lateinit var database: ArtDatabase
    private lateinit var dao : ArtDao

    fun setupKeysWithoutHilt(){
        // Main thread -da islemesi ucun 25ci setiri elave eledik
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), ArtDatabase::class.java
        ).allowMainThreadQueries().build()
    }

    @Before
    fun setup(){
        hiltRule.inject()
        dao = database.artDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun `databaseArtAddingTestReturnsSuccess`() = runBlocking{
        val art = ArtModel("Starry Night","Van Gogh",1889,"testing.az",1)
        dao.insertArt(art)

        val list = dao.getAll().getOrAwaitValue()
        assertThat(list).contains(art)
    }

    @Test
    fun `databaseArtDeletingTestReturnsSuccess`() = runBlocking{
        val art1 = ArtModel("Starry Night","Van Gogh",1889,"testing.az",1)
        val art2 = ArtModel("Mona Lisa","Leonardo Da Vinci",1503,"testing.az",2)
        dao.insertArt(art1)
        dao.insertArt(art2)
        dao.deleteArt(art1)

        val list = dao.getAll().getOrAwaitValue()
        assertThat(list).doesNotContain(art1)
    }
}