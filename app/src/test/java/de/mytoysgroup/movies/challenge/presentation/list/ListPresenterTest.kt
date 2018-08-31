package de.mytoysgroup.movies.challenge.presentation.list

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import de.mytoysgroup.movies.challenge.doSuccessUseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.SearchUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.AddToWishlistUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.GetWishlistMoviesUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.RemoveFromWishlistUseCase
import de.mytoysgroup.movies.challenge.hasItems
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ListPresenterTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private lateinit var searchUseCase: SearchUseCase
    private lateinit var getWishlistMoviesUseCase: GetWishlistMoviesUseCase
    private lateinit var addToWishlistUseCase: AddToWishlistUseCase
    private lateinit var removeFromWishlistUseCase: RemoveFromWishlistUseCase

    private lateinit var subjectUnderTest: ListPresenter

    @Before
    fun setUp() {
        searchUseCase = mock()
        getWishlistMoviesUseCase = mock()
        addToWishlistUseCase = mock()
        removeFromWishlistUseCase = mock()

        subjectUnderTest = ListPresenter(searchUseCase, getWishlistMoviesUseCase, addToWishlistUseCase, removeFromWishlistUseCase)
    }

    @Test
    fun `WHEN search movies with any query THEN query is used to search AND liveData contains movie list from search response`() {
        val fakeMovieList = listOf<Movie>(mock(), mock(), mock(), mock())
        val fakeSearchQuery = "queryToSearch"

        doSuccessUseCase(fakeMovieList).`when`(searchUseCase).execute(any(), any())

        subjectUnderTest.search(fakeSearchQuery)

        val captor = argumentCaptor<String>()
        verify(searchUseCase).execute(captor.capture(), any())

        assertThat(captor.firstValue, equalTo(fakeSearchQuery))

        val movieList = subjectUnderTest.moviesLiveData.value
        assertThat(movieList, notNullValue())
        assertThat(movieList!!.size, equalTo(fakeMovieList.size))
        assertThat(movieList, hasItems(fakeMovieList))
    }
}