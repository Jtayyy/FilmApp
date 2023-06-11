package fr.epf.tp.movieapp.requests

import fr.epf.tp.movieapp.model.Movie
import fr.epf.tp.movieapp.model.getResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataRequests {

    private val api: APIInterface
    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(APIInterface::class.java)
    }

    fun getFamousMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getFamousMovies(page = page)
            .enqueue(object : Callback<getResponse> {
                override fun onResponse(
                    call: Call<getResponse>,
                    response: Response<getResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }
                override fun onFailure(call: Call<getResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getTopMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getTopMovies(page = page)
            .enqueue(object : Callback<getResponse> {
                override fun onResponse(
                    call: Call<getResponse>,
                    response: Response<getResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<getResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getLastMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getLastMovies(page = page)
            .enqueue(object : Callback<getResponse> {
                override fun onResponse(
                    call: Call<getResponse>,
                    response: Response<getResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<getResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getSimilarMovies(
        page: Int = 1,
        movieId: Long,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getSimilarMovies(movie_id = movieId, page = page)
            .enqueue(object : Callback<getResponse> {
                override fun onResponse(
                    call: Call<getResponse>,
                    response: Response<getResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<getResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getSearchMovies(
        page: Int = 1,
        searched: String,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        api.getSearchMovies(search = searched, page = page)
            .enqueue(object : Callback<getResponse> {
                override fun onResponse(
                    call: Call<getResponse>,
                    response: Response<getResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    } else {
                        onError.invoke()
                    }
                }

                override fun onFailure(call: Call<getResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getMovie(movieId: Int, callback: (movie: Movie?, error: Throwable?) -> Unit) {
        val call = api.getMovie(movieId)
        call.enqueue(object : Callback<Movie> {
            override fun onResponse(call: Call<Movie>, response: Response<Movie>) {
                if (response.isSuccessful) {
                    val movie = response.body()
                    callback(movie, null)
                } else {
                    callback(null, Exception("Error"))
                }
            }

            override fun onFailure(call: Call<Movie>, t: Throwable) {
                callback(null, t)
            }
        })
    }
}