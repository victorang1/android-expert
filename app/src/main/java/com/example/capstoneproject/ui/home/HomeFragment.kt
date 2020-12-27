package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.HomeFragmentBinding
import com.example.capstoneproject.ui.detail.DetailFragment.Companion.SOURCE_HOME
import com.example.core.data.Resource
import com.example.core.ui.MovieAdapter
import com.example.core.utils.onClick
import kotlinx.android.synthetic.main.view_error.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class HomeFragment : Fragment() {

    private lateinit var binding: HomeFragmentBinding
    private val homeViewModel: HomeViewModel by viewModel()
    private val mAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObserver()
        setAdapter()
    }

    private fun setListener() {
        binding.fabFavorite.onClick().onEach {
            val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
            findNavController().navigate(action)
        }.launchIn(lifecycleScope)
    }

    private fun setObserver() {
        homeViewModel.movies.observe(viewLifecycleOwner, Observer { movies ->
            if (movies != null) {
                when (movies) {
                    is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        mAdapter.setDataSet(movies.data)
                    }
                    is Resource.Error -> {
                        binding.progressBar.visibility = View.GONE
                        binding.viewError.rootView.visibility = View.VISIBLE
                        binding.viewError.tv_error.text = movies.message ?: getString(R.string.something_wrong)
                    }
                }
            }
        })
    }

    private fun setAdapter() {
        mAdapter.setOnItemClick {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it, SOURCE_HOME)
            findNavController().navigate(action)
        }
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mAdapter
        }
    }
}