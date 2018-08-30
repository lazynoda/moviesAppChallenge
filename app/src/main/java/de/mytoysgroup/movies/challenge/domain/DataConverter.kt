package de.mytoysgroup.movies.challenge.domain

interface DataConverter<T> {

    fun fromMap(map: Map<String, Any?>): T

    fun toMap(value: T): Map<String, Any?>
}