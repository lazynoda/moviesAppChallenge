package de.mytoysgroup.movies.challenge.presentation.detail

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import com.nhaarman.mockitokotlin2.*
import de.mytoysgroup.movies.challenge.doSuccessUseCase
import de.mytoysgroup.movies.challenge.domain.model.Movie
import de.mytoysgroup.movies.challenge.domain.search.GetMovieByIdUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.AddToWishlistUseCase
import de.mytoysgroup.movies.challenge.domain.wishlist.RemoveFromWishlistUseCase
import de.mytoysgroup.movies.challenge.lazyMock
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class DetailPresenterTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val mockGetMovieByIdUseCase by lazyMock<GetMovieByIdUseCase>()
    private val mockAddToWishlistUseCase by lazyMock<AddToWishlistUseCase>()
    private val mockRemoveFromWishlistUseCase by lazyMock<RemoveFromWishlistUseCase>()

    private lateinit var subjectUnderTest: DetailPresenter

    @Before
    fun setUp() {
        subjectUnderTest = DetailPresenter(mockGetMovieByIdUseCase, mockAddToWishlistUseCase, mockRemoveFromWishlistUseCase)
    }

    @Test
    fun `GIVEN a movie id tt4154756 WHEN get movie THEN liveData contains movie returned from use case`() {
        val fakeMovieId = "tt4154756"

        val fakeMovie = mock<Movie>()
        doSuccessUseCase(fakeMovie).`when`(mockGetMovieByIdUseCase).execute(any(), any())

        subjectUnderTest.getMovie(fakeMovieId)

        val captor = argumentCaptor<String>()
        verify(mockGetMovieByIdUseCase).execute(captor.capture(), any())

        assertThat(captor.firstValue, equalTo(fakeMovieId))

        val movie = subjectUnderTest.movie.value
        assertThat(movie, equalTo(fakeMovie))
    }

    @Test
    fun `GIVEN wishlisted movie WHEN change wishlist THEN remove from wishlist use case is executed AND liveData contains movie without wishlist`() {
        // Here we can't use a mock<Movie> because the subject uses Movie::copy method. In a mock this method returns null.
        val fakeMovie = Movie("tt4154756", "Avengers: Infinity War", true, mock())
        (subjectUnderTest.movie as MutableLiveData).value = fakeMovie

        doSuccessUseCase(Unit).`when`(mockRemoveFromWishlistUseCase).execute(any(), any())

        subjectUnderTest.changeWishlist()

        val captor = argumentCaptor<String>()
        verify(mockRemoveFromWishlistUseCase).execute(captor.capture(), any())
        verifyZeroInteractions(mockAddToWishlistUseCase)

        assertThat(captor.firstValue, equalTo(fakeMovie.id))

        val movie = subjectUnderTest.movie.value
        assertThat(movie, notNullValue())
        assertThat(movie!!.wishlist, equalTo(false))
    }

    @Test
    fun `GIVEN no wishlisted movie WHEN change wishlist THEN add to wishlist use case is executed AND liveData contains movie with wishlist`() {
        // Here we can't use a mock<Movie> because the subject uses Movie::copy method. In a mock this method returns null.
        val fakeMovie = Movie("tt4154756", "Avengers: Infinity War", false, mock())
        (subjectUnderTest.movie as MutableLiveData).value = fakeMovie

        doSuccessUseCase(Unit).`when`(mockAddToWishlistUseCase).execute(any(), any())

        subjectUnderTest.changeWishlist()

        val captor = argumentCaptor<String>()
        verify(mockAddToWishlistUseCase).execute(captor.capture(), any())
        verifyZeroInteractions(mockRemoveFromWishlistUseCase)

        assertThat(captor.firstValue, equalTo(fakeMovie.id))

        val movie = subjectUnderTest.movie.value
        assertThat(movie, notNullValue())
        assertThat(movie!!.wishlist, equalTo(true))
    }
}