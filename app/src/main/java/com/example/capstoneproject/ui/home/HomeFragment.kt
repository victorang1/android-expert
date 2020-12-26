package com.example.capstoneproject.ui.home

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstoneproject.R
import com.example.capstoneproject.databinding.HomeFragmentBinding
import com.example.core.data.Resource
import com.example.core.ui.MovieAdapter
import kotlinx.android.synthetic.main.view_error.view.*
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), View.OnClickListener {

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
        binding.fabFavorite.setOnClickListener(this)
        setObserver()
        setAdapter()
    }

    override fun onClick(view: View) {
        when (view) {
            binding.fabFavorite -> {
                val action = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()
                findNavController().navigate(action)
            }
        }
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
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        with(binding.rvMovies) {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
    }
}