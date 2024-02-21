package com.houston.filmographer.data.converter

import com.houston.filmographer.data.dto.cast.ActorDto
import com.houston.filmographer.data.dto.cast.DirectorsDto
import com.houston.filmographer.data.dto.cast.ItemDto
import com.houston.filmographer.data.dto.cast.MovieCastResponse
import com.houston.filmographer.data.dto.cast.OtherDto
import com.houston.filmographer.data.dto.cast.WritersDto
import com.houston.filmographer.domain.model.MovieCast
import com.houston.filmographer.domain.model.Person

class MovieCastConverter {

    fun convert(response: MovieCastResponse): MovieCast {
        return with (response) {
            MovieCast(
                imdbId = imDbId,
                fullTitle = fullTitle,
                directors = convertDirectors(directors),
                others = convertOthers(others),
                writers = convertWriters(writers),
                actors = convertWriters(writers)
            )
        }
    }

    private fun ItemDto.toMovieCastPerson(jobPrefix: String = ""): Person {
        return Person(
            id = id,
            name = name,
            description = if (jobPrefix.isEmpty()) this.description else "$jobPrefix -- ${description}",
            image = null
        )
    }

    private fun convertDirectors(directors: DirectorsDto): List<Person> {
        return directors.items.map { item -> item.toMovieCastPerson() }
    }

    private fun convertOthers(others: List<OtherDto>): List<Person> {
        return others.flatMap { other ->
            other.items.map { item -> item.toMovieCastPerson() }
        }
    }

    private fun convertWriters(writers: WritersDto): List<Person> {
        return writers.items.map { item -> item.toMovieCastPerson() }
    }

    private fun convertActors(actors: List<ActorDto>): List<Person> {
        return actors.map { actor ->
            Person(
                id = actor.id,
                name = actor.name,
                description = actor.asCharacter,
                image = actor.image
            )
        }
    }
}