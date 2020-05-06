package com.titu.tituapp.ui.main

import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.titu.tituapp.R
import com.titu.tituapp.ui.main.datamodels.Movie
import com.titu.tituapp.ui.main.util.SelectionStateManager
import com.titu.tituapp.ui.main.util.VerticalSpaceItemDecoration
import com.titu.tituapp.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.mainview_list_item_movie.view.*

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy { MainViewModel() }
    private lateinit var goodMoviesAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.loadData()
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "badMovies :: $it")
            if (::goodMoviesAdapter.isInitialized.not()) {
                goodMoviesAdapter = RecyclerViewAdapter(it, object : MovieClickListener {
                    override fun onClicked(position: Int) = run {
                        SelectionStateManager.instance.selectedMovie = goodMoviesAdapter.movieAt(position)
                        findNavController().navigate(R.id.open_movie_details) // Not passing through safeArgs because it takes more time and isn't required
                    }
                })
            } else {
                goodMoviesAdapter.notifyDataSetChanged()
            }
            movieRecyclerView.addItemDecoration(VerticalSpaceItemDecoration(50));

            movieRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = goodMoviesAdapter
            }
        })
    }

}

/**
 * Adapter for movies Recyclerview
 */
class RecyclerViewAdapter(
    private val movies: List<Movie>,
    private val movieClickListener: MovieClickListener
) :
    RecyclerView.Adapter<RecyclerViewAdapter.MovieViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.mainview_list_item_movie,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val currentMovie = movies[position]

        holder.movieName.text = "${currentMovie.title} (${currentMovie.vote_average})"
        holder.movieDesc.text = "${currentMovie.overview}"

        val url = "https://image.tmdb.org/t/p/original${currentMovie.poster_path}"
        Picasso.get().load(url).into(holder.movieAlbum)

        holder.itemView.setOnClickListener { movieClickListener.onClicked(position) }
    }

    fun movieAt(position: Int): Movie {
        return movies.get(position)
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.movieTitleAndRatingTextView
        val movieAlbum: ImageView = itemView.moviePosterImageView
        val movieDesc: TextView = itemView.movieDescriptionTextView
    }
}

interface MovieClickListener {
    fun onClicked(position: Int)
}