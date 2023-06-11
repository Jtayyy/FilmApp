package fr.epf.tp.movieapp.activities

import android.content.Intent

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import fr.epf.tp.movieapp.R
import fr.epf.tp.movieapp.adapter.MovieAdapter
import fr.epf.tp.movieapp.model.Movie
import fr.epf.tp.movieapp.requests.DataRequests

const val MOVIE_BACKDROP = "extra_movie_backdrop"
const val MOVIE_POSTER = "extra_movie_poster"
const val MOVIE_TITLE = "extra_movie_title"
const val MOVIE_RATING = "extra_movie_rating"
const val MOVIE_RELEASE_DATE = "extra_movie_release_date"
const val MOVIE_OVERVIEW = "extra_movie_overview"
const val MOVIE_ID = "extra_movie_id"

class DetailsActivity : AppCompatActivity() {

    private lateinit var similarMovies: RecyclerView
    private lateinit var similarMoviesAdapter: MovieAdapter
    private lateinit var similarMoviesLayoutManager: LinearLayoutManager

    private var id : Long = 0

    private lateinit var backdrop: ImageView

    private lateinit var title: TextView
    private lateinit var releaseDate: TextView
    private lateinit var overview: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)
        similarMovies = findViewById(R.id.similar_movies)

        similarMoviesLayoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        similarMovies.layoutManager = similarMoviesLayoutManager
        similarMoviesAdapter = MovieAdapter(mutableListOf()){ movie -> showMovieDetails(movie)}
        similarMovies.adapter = similarMoviesAdapter


        backdrop = findViewById(R.id.movie_backdrop)
        title = findViewById(R.id.movie_title)
        releaseDate = findViewById(R.id.movie_release_date)
        overview = findViewById(R.id.movie_overview)


        val extras = intent.extras

        if (extras != null) {
            chargeData(extras)
        } else { finish() }
        getSimilarMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_others, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.goto_home ->{
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getSimilarMovies() {
        DataRequests.getSimilarMovies(
            1,
            id,
            ::similarMoviesData,
            ::onError
        )

    }

    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(MOVIE_ID,movie.id)
        intent.putExtra(MOVIE_OVERVIEW, movie.overview)
        intent.putExtra(MOVIE_BACKDROP, movie.backdropPath)
        intent.putExtra(MOVIE_POSTER, movie.posterPath)
        intent.putExtra(MOVIE_TITLE, movie.title)
        intent.putExtra(MOVIE_RATING, movie.rating)
        intent.putExtra(MOVIE_RELEASE_DATE, movie.releaseDate)
        startActivity(intent)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.notfound), Toast.LENGTH_SHORT).show()
    }
    private fun similarMoviesData(movies: List<Movie>) {
        similarMoviesAdapter.appendMovies(movies)

    }

    private fun chargeData(extras: Bundle) {
        extras.getString(MOVIE_BACKDROP)?.let { backdropPath ->
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w1280$backdropPath")
                .transform(CenterCrop())
                .into(backdrop)
        }



        title.text = extras.getString(MOVIE_TITLE, "")
        releaseDate.text = extras.getString(MOVIE_RELEASE_DATE, "")
        overview.text = extras.getString(MOVIE_OVERVIEW, "")
        id = extras.getLong(MOVIE_ID, 0)

    }
}