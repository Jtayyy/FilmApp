package fr.epf.tp.movieapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import fr.epf.tp.movieapp.R
import fr.epf.tp.movieapp.model.Movie

class SizeAdapter(

    private var movies: MutableList<Movie>,
    private val onMovieClick: (movie: Movie) -> Unit
) : RecyclerView.Adapter<SizeAdapter.MovieViewHolder>() {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val movie_pic: ImageView = itemView.findViewById(R.id.movie_pic)
        private val title: TextView = itemView.findViewById(R.id.title)

        fun bind(movie: Movie) {
            title.text="${movie.title}"
            Glide.with(itemView)
                .load("https://image.tmdb.org/t/p/w342${movie.posterPath}")
                .transform(CenterCrop())
                .into(movie_pic)
            itemView.setOnClickListener { onMovieClick.invoke(movie) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.movie_vertical, parent, false)
        return MovieViewHolder(view)
    }

    fun appendMovies(movies: List<Movie>) {
        this.movies.addAll(movies)
        notifyItemRangeInserted(
            this.movies.size,
            movies.size - 1
        )
    }

    override fun getItemCount(): Int = movies.size
}