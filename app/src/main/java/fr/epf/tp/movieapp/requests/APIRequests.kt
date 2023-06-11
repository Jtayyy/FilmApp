package fr.epf.tp.movieapp.requests

import fr.epf.tp.movieapp.model.Movie
import fr.epf.tp.movieapp.model.getResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

const val KEY = "63289d67e64c4ad8b73b3a7eb157852a"

interface APIInterface {

    @GET("movie/popular")
    fun getFamousMovies(
        @Query("api_key") apiKey: String = KEY,
        @Query("page") page: Int
    ): Call<getResponse>

    @GET("movie/top_rated")
    fun getTopMovies(
        @Query("api_key") apiKey: String = KEY,
        @Query("page") page: Int
    ): Call<getResponse>

    @GET("movie/now_playing")
    fun getLastMovies(
        @Query("api_key") apiKey: String = KEY,
        @Query("page") page: Int
    ): Call<getResponse>

    @GET("movie/{movie_id}/similar")
    fun getSimilarMovies(
        @Path("movie_id") movie_id: Long,
        @Query("api_key") apiKey: String = KEY,
        @Query("page") page: Int
    ): Call<getResponse>

    @GET("search/movie")
    fun getSearchMovies(
        @Query("query") search: String,
        @Query("api_key") apiKey: String = KEY,
        @Query("page") page: Int
    ): Call<getResponse>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Path("movie_id") movie_id: Int,
        @Query("api_key") apiKey: String = KEY,
    ): Call<Movie>
}