package io.github.orizynpx.fivegamesxml.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import io.github.orizynpx.fivegamesxml.databinding.FragmentHomeBinding
import io.github.orizynpx.fivegamesxml.ui.adapter.CarouselGameAdapter
import io.github.orizynpx.fivegamesxml.ui.adapter.ListGameAdapter
import io.github.orizynpx.fivegamesxml.ui.viewmodel.HomeViewModel
import io.github.orizynpx.fivegamesxml.ui.viewmodel.HomeViewModelFactory
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModelFactory(requireActivity().application, "Five Games at Wasaka's XML")
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
            onDetailClick = { game -> viewModel.onDetailClicked(game) },
            onLinkClick = { url -> viewModel.onLinkClicked(url) }
        )
        listGameAdapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        carouselGameAdapter = CarouselGameAdapter { game -> viewModel.onDetailClicked(game) }
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
                    viewModel.gameList.collect { games ->
                        listGameAdapter?.submitList(games)
                        carouselGameAdapter?.submitList(games)
                    }
                }

                launch {
                    viewModel.navigateToDetail.collect { game ->
                        game?.let {
                            Timber.d("GALAT: Navigasi ke halaman Detail dengan membawa data berupa ${it})")

                            navigateToDetail(it.id)
                            viewModel.onDetailNavigated()
                        }
                    }
                }

                launch {
                    viewModel.navigateToLink.collect { url ->
                        url?.let {
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it))
                            startActivity(intent)
                            viewModel.onLinkNavigated()
                        }
                    }
                }
            }
        }
    }

    private fun navigateToDetail(gameId: Int) {
        val bundle = Bundle().apply { putInt("gameId", gameId) }
        findNavController().navigate(R.id.action_homeFragment_to_detailFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}