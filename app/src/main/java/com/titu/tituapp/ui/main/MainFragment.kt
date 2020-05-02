package com.titu.tituapp.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.navigation.fragment.findNavController
import com.titu.tituapp.R
import com.titu.tituapp.ui.main.vm.MainViewModel
import com.titu.tituapp.ui.main.vm.Movie
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
                        findNavController().navigate(R.id.open_movie_details)
                    }
                })
            } else {
                goodMoviesAdapter.notifyDataSetChanged()
            }
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
        holder.movieName.text = movies[position].name
        holder.itemView.setOnClickListener { movieClickListener.onClicked(position) }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.albumNameTextView
    }
}

interface MovieClickListener {
    fun onClicked(position: Int)
}