package xyz.belvi.motion.movieMain.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.motion_grid_item.view.*
import xyz.belvi.motion.R
import xyz.belvi.motion.data.realmObject.MotionMovie
import xyz.belvi.motion.movieMain.presenter.MoviesFetchPresenter
import xyz.belvi.motion.models.enums.MoviePosterSize

/**
 * Created by zone2 on 6/23/18.
 */
class MovieListAdapter(private var movies: MutableList<MotionMovie>, private val moviePresenter: MoviesFetchPresenter) : RecyclerView.Adapter<MovieListAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(LayoutInflater.from(parent.context).inflate(R.layout.motion_grid_item, parent, false))
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindItem(movies[position])
        holder.itemView.setOnClickListener {
            moviePresenter.movieSelected(it, movies[position])
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateItems(newMovies: MutableList<MotionMovie>) {
        this.movies = newMovies
        notifyDataSetChanged()
    }

    class MovieHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItem(movie: MotionMovie) {
            itemView.movie_img.apply {
                contentDescription = movie.getMovieTitle()
                Glide.with(itemView.context).load(movie.getMoviePosterPath(MoviePosterSize.w342)).into(this)
            }
        }

    }
}