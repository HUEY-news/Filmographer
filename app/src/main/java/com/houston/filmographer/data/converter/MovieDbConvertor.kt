package com.houston.filmographer.data.converter

import com.houston.filmographer.data.db.MovieEntity
import com.houston.filmographer.data.dto.movie.MovieDto
import com.houston.filmographer.domain.model.Movie

class MovieDbConvertor {

    fun map(movie: MovieDto): MovieEntity {
        return MovieEntity(
            id = movie.id,
            resultType = movie.resultType,
            image = movie.image,
            title = movie.title,
            description = movie.description
        )
    }

    fun map(movie: MovieEntity): Movie {
        return Movie(
            id = movie.id,
            resultType = movie.resultType,
            image = movie.image,
            title = movie.title,
            description = movie.description,
            inFavorite = false
        )
    }
}