package com.example.favorite

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.capstoneproject.ui.detail.DetailFragment.Companion.SOURCE_FAVORITE
import com.example.core.R
import com.example.core.domain.model.Favorite
import com.example.core.ui.FavoriteAdapter
import com.example.core.utils.MappingUtil
import com.example.favorite.databinding.FavoriteFragmentBinding
import com.example.favorite.di.favoriteModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@ExperimentalCoroutinesApi
@FlowPreview
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
        setListener()
        setAdapter()
    }

    private fun setObserver() {
        viewModel.favorites.observe(viewLifecycleOwner, Observer { favorites ->
            handleResults(favorites)
        })

        viewModel.searchResult.observe(viewLifecycleOwner, Observer { results ->
            lifecycleScope.launch {
                results.collect { handleResults(it) }
            }
        })
    }

    private fun setListener() {
        binding.etSearch.doAfterTextChanged {
            binding.progressBar.visibility = View.VISIBLE
            lifecycleScope.launch { viewModel.mChannel.send(it.toString()) }
        }
    }

    private fun handleResults(favorites: List<Favorite>) {
        binding.progressBar.visibility = View.GONE
        binding.tvNoData.visibility = if (favorites.isNullOrEmpty()) View.VISIBLE else View.GONE
        mAdapter.setDataSet(favorites)
    }

    private fun setAdapter() {
        mAdapter.setOnItemClick {
            val movie = MappingUtil.mapFavoriteToMovie(it)
            val action = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment(movie, SOURCE_FAVORITE)
            findNavController().navigate(action)
        }
        mAdapter.setOnDeleteClick { deleteFromFavorite(it) }
        with(binding.rvFavorite) {
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            adapter = mAdapter
        }
    }

    private fun deleteFromFavorite(favorite: Favorite) {
        viewModel.deleteFromFavorite(favorite)
        Toast.makeText(
            requireContext(),
            getString(R.string.text_remove_from_favorite_success),
            Toast.LENGTH_SHORT
        ).show()
    }
}