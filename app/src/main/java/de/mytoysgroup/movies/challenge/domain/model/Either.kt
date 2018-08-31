package de.mytoysgroup.movies.challenge.domain.model

@Suppress("unused")
sealed class Either<F, S> {

    class Failure<F, S>(val value: F) : Either<F, S>()
    class Success<F, S>(val value: S) : Either<F, S>()
}