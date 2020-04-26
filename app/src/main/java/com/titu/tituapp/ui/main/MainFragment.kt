package com.titu.tituapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.titu.tituapp.R
import com.titu.tituapp.ui.main.vm.MainViewModel
import kotlinx.android.synthetic.main.main_fragment.*;

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
    }

}
