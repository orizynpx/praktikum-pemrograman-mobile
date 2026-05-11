package io.github.orizynpx.fivegamesxml.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import io.github.orizynpx.fivegamesxml.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.carousel.CarouselLayoutManager
import com.google.android.material.carousel.CarouselSnapHelper
import com.google.android.material.carousel.HeroCarouselStrategy
import io.github.orizynpx.fivegamesxml.databinding.FragmentHomeBinding
import io.github.orizynpx.fivegamesxml.ui.adapter.CarouselAdapter
import io.github.orizynpx.fivegamesxml.ui.adapter.GameAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private var gameAdapter: GameAdapter? = null
    private var carouselAdapter: CarouselAdapter? = null

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

        if (gameAdapter == null || carouselAdapter == null) {
            setupAdapters()
        }

        setupRecyclerViews()

        viewModel.games.observe(viewLifecycleOwner) { gamesList ->
            gameAdapter?.submitList(gamesList)
            carouselAdapter?.submitList(gamesList)
        }
    }

    private fun setupAdapters() {
        gameAdapter = GameAdapter(
            onDetailClick = { game -> navigateToDetail(game.id) },
            onLinkClick = { url ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            }
        )
        gameAdapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        carouselAdapter = CarouselAdapter { game ->
            navigateToDetail(game.id)
        }
        carouselAdapter?.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    private fun setupRecyclerViews() {
        binding.rvGames.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = gameAdapter
            setHasFixedSize(true)
        }

        binding.rvCarousel.apply {
            layoutManager = CarouselLayoutManager(HeroCarouselStrategy())
            adapter = carouselAdapter
            setHasFixedSize(true)
            CarouselSnapHelper().attachToRecyclerView(this)
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