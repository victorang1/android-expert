package com.example.capstoneproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.HomeFragmentBinding
import com.example.core.data.Resource
import com.example.core.domain.model.Movie
import com.example.core.ui.MovieAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
@FlowPreview
class HomeFragment : Fragment(), View.OnClickListener {

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val homeViewModel: HomeViewModel by viewModel()
    private val mAdapter: MovieAdapter by lazy { MovieAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListener()
        setObserver()
        setAdapter()
    }

    override fun onClick(v: View) {
        when (v) {
            binding.fabFavorite -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setListener() {
        binding.fabFavorite.setOnClickListener(this)

        binding.etSearch.doAfterTextChanged {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch { homeViewModel.mChannel.send(it.toString()) }
        }
    }

    private fun setObserver() {
        homeViewModel.movies.observe(viewLifecycleOwner) { movies ->
            when (movies) {
                is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    mAdapter.setDataSet(movies.data)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.errorVisibility = View.VISIBLE
                    binding.errorMessage = movies.message ?: getString(R.string.something_wrong)
                }
            }
        }

        homeViewModel.searchResult.observe(viewLifecycleOwner) { results ->
            lifecycleScope.launch {
                results.collect {
                    when(it) {
                        is Resource.Loading -> binding.progressBar.visibility = View.VISIBLE
                        is Resource.Success -> {
                            handleResults(it.data ?: mutableListOf())
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.errorVisibility = View.VISIBLE
                            binding.errorMessage = it.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }
        }
    }

    private fun handleResults(movies: List<Movie>) {
        binding.progressBar.visibility = View.GONE
        if (movies.isNullOrEmpty()) {
            binding.errorMessage = getString(R.string.no_data)
        }
        binding.errorVisibility = if (movies.isNullOrEmpty()) View.VISIBLE else View.GONE
        mAdapter.setDataSet(movies)
    }

    private fun setAdapter() {
        mAdapter.setOnItemClick {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        with(binding.rvMovies) {
            layoutManager = GridLayoutManager(context, 2)
            adapter = mAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvMovies.adapter = null
        _binding = null
    }
}