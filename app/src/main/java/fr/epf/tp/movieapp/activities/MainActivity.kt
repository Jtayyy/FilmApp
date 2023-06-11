package fr.epf.tp.movieapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.tp.movieapp.R
import fr.epf.tp.movieapp.adapter.MovieAdapter
import fr.epf.tp.movieapp.model.Movie
import fr.epf.tp.movieapp.requests.DataRequests

class MainActivity : AppCompatActivity() {

    private lateinit var lastMoviesLayoutManager: LinearLayoutManager
    private lateinit var lastMovies: RecyclerView
    private lateinit var lastMovieAdapter: MovieAdapter
    private lateinit var famousMoviesLayoutManager: LinearLayoutManager
    private lateinit var famousMovies: RecyclerView
    private lateinit var famousMovieAdapter: MovieAdapter
    private lateinit var topMovies: RecyclerView
    private lateinit var topMoviesAdapter: MovieAdapter
    private lateinit var topMoviesLayoutManager: LinearLayoutManager
    private lateinit var upcomingMovies: RecyclerView
    private lateinit var upcomingMoviesAdapter: MovieAdapter
    private lateinit var upcomingMoviesLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lastMovies = findViewById(R.id.last)
        lastMoviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        lastMovies.layoutManager=lastMoviesLayoutManager
        lastMovieAdapter = MovieAdapter(mutableListOf()){ movie -> details(movie)}
        lastMovies.adapter = lastMovieAdapter
        getLastMovies()

        famousMovies = findViewById(R.id.famous)
        famousMoviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        famousMovies.layoutManager=famousMoviesLayoutManager
        famousMovieAdapter = MovieAdapter(mutableListOf()){ movie -> details(movie)}
        famousMovies.adapter = famousMovieAdapter
        getFamousMovies()

        topMovies = findViewById(R.id.top)
        topMoviesLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        topMovies.layoutManager = topMoviesLayoutManager
        topMoviesAdapter = MovieAdapter(mutableListOf()){ movie -> details(movie)}
        topMovies.adapter = topMoviesAdapter
        getTopMovies()

    }

    private fun getLastMovies() {
        DataRequests.getLastMovies(
            1,
            ::famousMoviesData,
            ::onError
        )
    }
    private fun LastMoviesData(movies: List<Movie>) {
        lastMovieAdapter.appendMovies(movies)
    }

    private fun getFamousMovies() {
        DataRequests.getFamousMovies(
            1,
            ::LastMoviesData,
            ::onError
        )
    }
    private fun famousMoviesData(movies: List<Movie>) {
        famousMovieAdapter.appendMovies(movies)
    }

    private fun getTopMovies() {
        DataRequests.getTopMovies(
            1,
            ::topRatedMoviesData,
            ::onError
        )
    }
    private fun topRatedMoviesData(movies: List<Movie>) {
        topMoviesAdapter.appendMovies(movies)
    }


    private fun onError() {
        Toast.makeText(this, getString(R.string.notfound), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.goto_research -> {
                val intent = Intent(this, SearchActivity::class.java)
                startActivity(intent)
            }
            R.id.goto_qr_scanner ->{
                val intent = Intent(this, QRCodeActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun details(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_ID, movie.id)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        startActivity(intent)
    }
}