package fr.epf.tp.movieapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.epf.tp.movieapp.R
import fr.epf.tp.movieapp.adapter.SizeAdapter
import fr.epf.tp.movieapp.model.Movie
import fr.epf.tp.movieapp.requests.DataRequests

class SearchActivity : AppCompatActivity() {

    private lateinit var allMovies:ArrayList<Unit>
    private lateinit var searchedMovies: RecyclerView
    private lateinit var sizeAdapter: SizeAdapter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.movie_research)
        searchedMovies = findViewById(R.id.searched_movies)
        searchedMovies.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        searchView = findViewById(R.id.search_view)
        sizeAdapter = SizeAdapter(mutableListOf()){ movie -> showMovieDetails(movie)}
        searchedMovies.adapter = sizeAdapter
        allMovies = ArrayList()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(msg: String) :Boolean {
                getSearchedMovies(msg)
                sizeAdapter = SizeAdapter(mutableListOf()){ movie -> showMovieDetails(movie)}
                searchedMovies.adapter = sizeAdapter

                return false
            }

            override fun onQueryTextChange(msg: String): Boolean {
                getSearchedMovies(msg)
                sizeAdapter = SizeAdapter(mutableListOf()){ movie -> showMovieDetails(movie)}
                searchedMovies.adapter = sizeAdapter

                return false
            }
        })
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

    private fun getSearchedMovies(query:String) {
        DataRequests.getSearchMovies(
            1,
            query,
            ::searchedMoviesData,
            ::onError
        )
    }
    private fun searchedMoviesData(movies: List<Movie>) {
        sizeAdapter.appendMovies(movies)
    }

    private fun onError() {
        Toast.makeText(this, getString(R.string.notfound), Toast.LENGTH_SHORT).show()
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
}