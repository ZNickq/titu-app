package com.titu.tituapp.ui.main


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.titu.tituapp.R
import com.titu.tituapp.databinding.FragmentMovieDetailsBinding
import com.titu.tituapp.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.*
import kotlinx.android.synthetic.main.mainview_list_item_movie.view.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    private val viewModel: MainViewModel by lazy { MainViewModel() }
    private lateinit var goodMoviesAdapter: RecyclerViewAdapter
    private lateinit var badMoviesAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val binding =
            DataBindingUtil.inflate<FragmentMovieDetailsBinding>(
                inflater,
                R.layout.fragment_movie_details,
                container,
                false
            )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.goodMovies.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "badMovies :: $it")
            if (::goodMoviesAdapter.isInitialized.not()) {
                goodMoviesAdapter = RecyclerViewAdapter(it, object : MovieClickListener {
                    override fun onClicked(position: Int) = viewModel.downrateMovie(position)
                })
                rvGoodMovies.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = goodMoviesAdapter
                }
            } else {
                goodMoviesAdapter.notifyDataSetChanged()
            }
        })
        viewModel.badMovies.observe(viewLifecycleOwner, Observer {
            Log.d("TAG", "goodMovies :: $it")
            if (::badMoviesAdapter.isInitialized.not()) {
                badMoviesAdapter = RecyclerViewAdapter(it, object : MovieClickListener {
                    override fun onClicked(position: Int) = viewModel.uprateMovie(position)
                })
                rvBadMovies.apply {
                    layoutManager = LinearLayoutManager(requireContext())
                    adapter = badMoviesAdapter
                }
            } else {
                badMoviesAdapter.notifyDataSetChanged()
            }
        })
    }
}

/**
 * Adapter for movies Recyclerview
 */
class RecyclerViewAdapter(
    private val movies: List<String>,
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
        holder.movieName.text = movies[position]
        holder.itemView.setOnClickListener { movieClickListener.onClicked(position) }
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName: TextView = itemView.albumNameTextView
    }
}

interface MovieClickListener {
    fun onClicked(position: Int)
}