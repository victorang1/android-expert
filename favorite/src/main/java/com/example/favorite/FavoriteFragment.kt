package com.example.favorite

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.core.domain.model.Favorite
import com.example.core.ui.FavoriteAdapter
import com.example.core.utils.MappingUtil
import com.example.favorite.databinding.FavoriteFragmentBinding
import com.example.favorite.di.favoriteModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

@ExperimentalCoroutinesApi
class FavoriteFragment : Fragment() {

    private var _binding: FavoriteFragmentBinding? = null
    private val binding get() = _binding!!
    private val mAdapter: FavoriteAdapter by lazy { FavoriteAdapter() }
    private val viewModel: FavoriteViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FavoriteFragmentBinding.inflate(inflater, container, false)
        loadKoinModules(favoriteModule)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
        setAdapter()
    }

    private fun setObserver() {
        viewModel.favorites.observe(viewLifecycleOwner, { favorites ->
            handleResults(favorites)
        })
    }

    private fun handleResults(favorites: List<Favorite>) {
        binding.progressBar.visibility = View.GONE
        binding.tvNoData.visibility = if (favorites.isNullOrEmpty()) View.VISIBLE else View.GONE
        mAdapter.setDataSet(favorites)
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFavorite.adapter = null
        _binding = null
    }
}