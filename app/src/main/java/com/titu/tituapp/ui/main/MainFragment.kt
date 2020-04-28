package com.titu.tituapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.titu.tituapp.R
import com.titu.tituapp.databinding.MainviewListItemMovieBinding
import com.titu.tituapp.ui.main.vm.MainViewModel
import com.titu.tituapp.ui.main.vm.MovieViewViewModel
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = MainViewModel()

        pressMe.setOnClickListener {
            findNavController().navigate(R.id.open_movie_details)
        }

        movieRecyclerView.layoutManager = LinearLayoutManager(context)

        val adapter = MovieListAdapter(MovieViewViewModel())
        movieRecyclerView.adapter = adapter
        val items = mutableListOf<ViewItem>().apply {
            add(ViewItem.AlbumView("I am album"))
            add(ViewItem.AlbumView("aaaa 2"))
        }
        adapter.replaceItems(items)
    }

}

sealed class ViewItem(val resource: Int) {
    class AlbumView(val name: String): ViewItem(R.layout.fragment_movie_details) //FIXME fix layout
}

private class MovieListAdapter(val viewModel: MovieViewViewModel) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {
    private var items: List<ViewItem> = listOf()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return items[position].resource
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == R.layout.fragment_movie_details) { // FIXME fix layout
            val inflater = LayoutInflater.from(parent.context)
            val binding = MainviewListItemMovieBinding.inflate(inflater, parent, false)
            // val binding = DataBindingUtil.inflate<AlbumviewListItemAlbumBinding>(inflater, R.layout.albumview_list_item_album, parent, false)
            val viewHolder = BindingViewHolder(binding.root, binding)
            // suspected cannot mix observable with LiveData with Observable
            binding.setLifecycleOwner(viewHolder)
            return viewHolder

        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(viewType, parent, false)
            ViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        //val context = holder.containerView.context

        when (item) {
            is ViewItem.AlbumView -> {
                if (holder is BindingViewHolder) {
                    holder.apply {
                        viewModel.liveItem.name.value = item.name
                        binding.viewModel = viewModel
                    }
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        if (holder is BindingViewHolder) {
            holder.markAttach()
        }
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        if (holder is BindingViewHolder) {
            holder.markDetach()
        }
    }

    fun replaceItems(items: List<ViewItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    open inner class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
        LayoutContainer

    inner class BindingViewHolder(override val containerView: View, val binding: MainviewListItemMovieBinding) : ViewHolder(containerView), LifecycleOwner {
        private val lifecycleRegistry = LifecycleRegistry(this)

        init {
            lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        }

        fun markAttach() {
            lifecycleRegistry.currentState = Lifecycle.State.STARTED
        }

        fun markDetach() {
            lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
        }

        override fun getLifecycle(): Lifecycle {
            return lifecycleRegistry
        }
    }
}