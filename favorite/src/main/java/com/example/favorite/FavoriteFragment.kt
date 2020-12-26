package com.example.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.core.ui.FavoriteAdapter
import com.example.core.utils.MappingUtil
import com.example.favorite.databinding.FavoriteFragmentBinding
import com.example.favorite.di.favoriteModule
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteFragment : Fragment() {

    private lateinit var binding: FavoriteFragmentBinding
    private val mAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setAdapter()
    }

    private fun setObserver() {
        viewModel.favorites.observe(viewLifecycleOwner, Observer { favorites ->
            binding.progressBar.visibility = View.GONE
            if (!favorites.isNullOrEmpty()) {
                binding.tvNoData.visibility = View.GONE
                mAdapter.setDataSet(favorites)
            }
            else binding.tvNoData.visibility = View.VISIBLE
        })
    }

    private fun setAdapter() {
        mAdapter.setOnItemClick {
            val movie = MappingUtil.mapFavoriteToMovie(it)
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(movie)
            findNavController().navigate(action)
        }
        with(binding.rvFavorite) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = mAdapter
        }
    }
}