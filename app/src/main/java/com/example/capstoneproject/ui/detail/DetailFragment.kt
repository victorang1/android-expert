package com.example.capstoneproject.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.capstoneproject.databinding.DetailFragmentBinding
import com.example.core.R
import org.koin.android.viewmodel.ext.android.viewModel

class DetailFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: DetailFragmentBinding
    private val mViewModel: DetailViewModel by viewModel()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailFragmentBinding.inflate(inflater, container, false)
        setObserver()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnFavorite.setOnClickListener(this)
        setData()
    }

    override fun onClick(view: View) {
        when (view) {
            binding.btnFavorite -> handleClickFavorite()
        }
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

    private fun setObserver() {
        mViewModel.isFavorite(args.movie.id).observe(viewLifecycleOwner, Observer { isFavorite ->
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
            if (isFavorite) R.string.remove_from_favorite
            else R.string.add_to_favorite
        )
    }
}