package com.example.capstoneproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstoneproject.databinding.DetailFragmentBinding
import com.example.core.R
import com.example.core.utils.onClick
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class DetailFragment : Fragment() {

    companion object {
        const val SOURCE_HOME = "home"
        const val SOURCE_FAVORITE = "favorite"
    }

    private lateinit var binding: DetailFragmentBinding
    private val mViewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        activity?.title = args.movie.title
        setListener()
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFavorite.visibility = if (args.source == SOURCE_HOME) View.VISIBLE else View.GONE
        setData()
    }

    private fun handleClickFavorite() {
        try {
            mViewModel.addToFavorite(args.movie)
            Toast.makeText(
                requireContext(),
                getString(R.string.text_add_to_favorite_success),
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListener() {
        binding.btnFavorite.onClick().onEach {
            handleClickFavorite()
        }.launchIn(lifecycleScope)
    }

    private fun setObserver() {
        mViewModel.isFavorite(args.movie.id).observe(viewLifecycleOwner, { isFavorite ->
            refreshFavoriteStatus(isFavorite)
        })
    }

    private fun setData() {
        binding.film = args.movie
        Glide.with(requireContext())
            .load(args.movie.image)
            .apply(RequestOptions.errorOf(R.drawable.ic_error_black))
            .into(binding.ivThumbnail)
    }

    private fun refreshFavoriteStatus(isFavorite: Boolean) {
        binding.btnFavorite.text = getString(
            if (isFavorite) R.string.already_favorite
            else R.string.add_to_favorite
        )
        binding.btnFavorite.isEnabled = !isFavorite
    }
}