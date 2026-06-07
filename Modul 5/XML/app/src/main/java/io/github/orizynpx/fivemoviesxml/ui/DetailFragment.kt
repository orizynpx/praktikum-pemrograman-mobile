package io.github.orizynpx.fivemoviesxml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import coil3.load
import io.github.orizynpx.fivemoviesxml.FiveMoviesApplication
import io.github.orizynpx.fivemoviesxml.databinding.FragmentDetailBinding
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieId = arguments?.getInt("movieId") ?: -1
        
        binding.toolbarDetail.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            val app = requireActivity().application as FiveMoviesApplication
            val movie = app.repository.movies.first().find { it.id == movieId }

            movie?.let {
                binding.tvDetailTitle.text = it.title
                binding.tvDetailDescription.text = it.overview
                
                val imageUrl = "https://image.tmdb.org/t/p/w780${it.backdropPath ?: it.posterPath}"
                binding.imgDetail.load(imageUrl)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
