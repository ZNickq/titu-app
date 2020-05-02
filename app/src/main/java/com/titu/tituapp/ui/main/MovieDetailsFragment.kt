package com.titu.tituapp.ui.main


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import com.titu.tituapp.ui.main.vm.SecondViewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    private val viewModel: SecondViewModel by lazy { SecondViewModel() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        print("Created $viewModel")
    }

}