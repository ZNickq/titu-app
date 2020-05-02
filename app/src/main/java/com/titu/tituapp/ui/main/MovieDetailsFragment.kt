package com.titu.tituapp.ui.main


import androidx.fragment.app.Fragment

import com.titu.tituapp.ui.main.vm.SecondViewModel

/**
 * A simple [Fragment] subclass.
 */
class MovieDetailsFragment : Fragment() {
    private val viewModel: SecondViewModel by lazy { SecondViewModel() }
    private lateinit var goodMoviesAdapter: RecyclerViewAdapter
    private lateinit var badMoviesAdapter: RecyclerViewAdapter

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View? {
//        val binding =
//            DataBindingUtil.inflate<FragmentMovieDetailsBinding>(
//                inflater,
//                R.layout.fragment_movie_details,
//                container,
//                false
//            )
//        binding.lifecycleOwner = viewLifecycleOwner
//        binding.viewModel = viewModel
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        viewModel.goodMovies.observe(viewLifecycleOwner, Observer {
//            Log.d("TAG", "badMovies :: $it")
//            if (::goodMoviesAdapter.isInitialized.not()) {
//                goodMoviesAdapter = RecyclerViewAdapter(it, object : MovieClickListener {
//                    override fun onClicked(position: Int) = viewModel.downrateMovie(position)
//                })
//                rvGoodMovies.apply {
//                    layoutManager = LinearLayoutManager(requireContext())
//                    adapter = goodMoviesAdapter
//                }
//            } else {
//                goodMoviesAdapter.notifyDataSetChanged()
//            }
//        })
//        viewModel.badMovies.observe(viewLifecycleOwner, Observer {
//            Log.d("TAG", "goodMovies :: $it")
//            if (::badMoviesAdapter.isInitialized.not()) {
//                badMoviesAdapter = RecyclerViewAdapter(it, object : MovieClickListener {
//                    override fun onClicked(position: Int) = viewModel.uprateMovie(position)
//                })
//                rvBadMovies.apply {
//                    layoutManager = LinearLayoutManager(requireContext())
//                    adapter = badMoviesAdapter
//                }
//            } else {
//                badMoviesAdapter.notifyDataSetChanged()
//            }
//        })
//    }
}