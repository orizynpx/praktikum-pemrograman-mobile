package io.github.orizynpx.fivemoviesxml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import io.github.orizynpx.fivemoviesxml.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import io.github.orizynpx.fivemoviesxml.FiveMoviesApplication
import io.github.orizynpx.fivemoviesxml.data.remote.NetworkResult
import io.github.orizynpx.fivemoviesxml.databinding.FragmentHomeBinding
import io.github.orizynpx.fivemoviesxml.ui.adapter.CarouselMovieAdapter
import io.github.orizynpx.fivemoviesxml.ui.adapter.ListMovieAdapter
import io.github.orizynpx.fivemoviesxml.ui.viewmodel.HomeViewModel
import io.github.orizynpx.fivemoviesxml.ui.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        val app = requireActivity().application as FiveMoviesApplication
        HomeViewModelFactory(app, app.repository)
    }

    private var listMovieAdapter: ListMovieAdapter? = null
    private var carouselMovieAdapter: CarouselMovieAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_settingsFragment)
        }

        if (listMovieAdapter == null || carouselMovieAdapter == null) {
            setupAdapters()
        }

        setupRecyclerViews()

        observeViewModel()
    }

    private fun setupAdapters() {
        listMovieAdapter = ListMovieAdapter(
            onDetailClick = { movie -> viewModel.onDetailClicked(movie) }
        )
        listMovieAdapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        carouselMovieAdapter = CarouselMovieAdapter { movie -> viewModel.onDetailClicked(movie) }
        carouselMovieAdapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun setupRecyclerViews() {
        binding.rvMovieList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listMovieAdapter
            setHasFixedSize(true)
        }

        binding.rvMovieCarousel.apply {
            layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
            adapter = carouselMovieAdapter
            setHasFixedSize(true)
            CarouselSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.movieList.collect { movies ->
                        listMovieAdapter?.submitList(movies)
                        carouselMovieAdapter?.submitList(movies)
                    }
                }

                launch {
                    viewModel.refreshState.collect { result ->
                        // Assuming FragmentHomeBinding has a ProgressBar with ID 'progressBar'
                        // If not, we might need to add it or skip this specific visibility toggle.
                        // binding.progressBar.isVisible = result is NetworkResult.Loading
                        
                        if (result is NetworkResult.Error) {
                            Toast.makeText(requireContext(), result.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                launch {
                    viewModel.navigateToDetail.collect { movie ->
                        movie?.let {
                            Timber.d("GALAT: Navigasi ke halaman Detail dengan membawa data berupa $it)")
                            navigateToDetail(it.id)
                            viewModel.onDetailNavigated()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetail(movieId: Int) {
        val bundle = Bundle().apply { putInt("movieId", movieId) }
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
