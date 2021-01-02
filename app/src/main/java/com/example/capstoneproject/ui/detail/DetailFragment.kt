package com.example.capstoneproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstoneproject.databinding.DetailFragmentBinding
import com.example.core.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class DetailFragment : Fragment(), View.OnClickListener {

    private var _binding: DetailFragmentBinding? = null
    private val binding get() = _binding!!
    private val mViewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()
    private var isFavorite: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailFragmentBinding.inflate(inflater, container, false)
        binding.isLoading = true
        setListener()
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setData()
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.btnFavorite -> handleClickFavorite()
        }
    }

    private fun handleClickFavorite() {
        try {
            if (isFavorite) {
                mViewModel.deleteFromFavorite(args.movie)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_remove_from_favorite_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
            else {
                mViewModel.addToFavorite(args.movie)
                Toast.makeText(
                    requireContext(),
                    getString(R.string.text_add_to_favorite_success),
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setListener() {
        binding.btnFavorite.setOnClickListener(this)
    }

    private fun setObserver() {
        mViewModel.isFavorite(args.movie.id).observe(viewLifecycleOwner) { isFavorite ->
            binding.isLoading = false
            this.isFavorite = isFavorite
            refreshFavoriteStatus(isFavorite)
        }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}