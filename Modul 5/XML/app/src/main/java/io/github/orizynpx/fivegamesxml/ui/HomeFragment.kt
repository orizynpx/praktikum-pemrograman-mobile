package io.github.orizynpx.fivegamesxml.ui

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
import io.github.orizynpx.fivegamesxml.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import io.github.orizynpx.fivegamesxml.FiveGamesApplication
import io.github.orizynpx.fivegamesxml.data.remote.NetworkResult
import io.github.orizynpx.fivegamesxml.databinding.FragmentHomeBinding
import io.github.orizynpx.fivegamesxml.ui.adapter.CarouselGameAdapter
import io.github.orizynpx.fivegamesxml.ui.adapter.ListGameAdapter
import io.github.orizynpx.fivegamesxml.ui.viewmodel.HomeViewModel
import io.github.orizynpx.fivegamesxml.ui.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        val app = requireActivity().application as FiveGamesApplication
        HomeViewModelFactory(app, app.repository)
    }

    private var listGameAdapter: ListGameAdapter? = null
    private var carouselGameAdapter: CarouselGameAdapter? = null

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

        if (listGameAdapter == null || carouselGameAdapter == null) {
            setupAdapters()
        }

        setupRecyclerViews()

        observeViewModel()
    }

    private fun setupAdapters() {
        listGameAdapter = ListGameAdapter(
            onDetailClick = { movie -> viewModel.onDetailClicked(movie) }
        )
        listGameAdapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        carouselGameAdapter = CarouselGameAdapter { movie -> viewModel.onDetailClicked(movie) }
        carouselGameAdapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun setupRecyclerViews() {
        binding.rvGameList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listGameAdapter
            setHasFixedSize(true)
        }

        binding.rvGameCarousel.apply {
            layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
            adapter = carouselGameAdapter
            setHasFixedSize(true)
            CarouselSnapHelper().attachToRecyclerView(this)
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.movieList.collect { movies ->
                        listGameAdapter?.submitList(movies)
                        carouselGameAdapter?.submitList(movies)
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
