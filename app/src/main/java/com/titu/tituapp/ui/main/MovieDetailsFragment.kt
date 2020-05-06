package com.titu.tituapp.ui.main


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.titu.tituapp.R
import com.titu.tituapp.databinding.FragmentMovieDetailsBinding

import com.titu.tituapp.ui.main.vm.SecondViewModel
import kotlinx.android.synthetic.main.fragment_movie_details.view.*

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    private val viewModel: SecondViewModel by lazy { SecondViewModel(requireActivity().getSharedPreferences("bla", Context.MODE_PRIVATE)) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val bindingShit = DataBindingUtil.inflate<FragmentMovieDetailsBinding>(inflater, R.layout.fragment_movie_details, container, false)
        bindingShit.viewModel = viewModel
        return bindingShit.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentStatus.observe(viewLifecycleOwner, Observer {
            view.currentStatusTextView.text = "Status: $it"
        })

        print("Created $viewModel")
    }

}