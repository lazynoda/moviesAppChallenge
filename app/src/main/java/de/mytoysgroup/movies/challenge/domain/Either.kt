package de.mytoysgroup.movies.challenge.domain

@Suppress("unused")
sealed class Either<L, R> {

    class Left<L, R>(val value: L) : Either<L, R>()
    class Right<L, R>(val value: R) : Either<L, R>()
}