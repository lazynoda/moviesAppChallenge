package de.mytoysgroup.movies.challenge

import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.mock
import de.mytoysgroup.movies.challenge.domain.OnUseCaseFinish
import de.mytoysgroup.movies.challenge.domain.model.Either
import org.hamcrest.CoreMatchers

inline fun <reified T> hasItems(list: List<T>) = CoreMatchers.hasItems(*list.toTypedArray())

fun <O> doSuccessUseCase(value: O) = doAnswer {
    it.getArgument<OnUseCaseFinish<O>>(1)?.invoke(Either.Success(value))
}

inline fun <reified T : Any> lazyMock() = lazy { mock<T>() }