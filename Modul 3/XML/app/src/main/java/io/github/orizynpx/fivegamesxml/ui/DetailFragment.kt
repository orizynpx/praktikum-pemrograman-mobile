package io.github.orizynpx.fivegamesxml.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.github.orizynpx.fivegamesxml.data.GameRepository
import io.github.orizynpx.fivegamesxml.databinding.FragmentDetailBinding

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

        // Get the ID passed from the previous screen
        val gameId = arguments?.getInt("gameId") ?: -1
        val game = GameRepository().getGames().find { it.id == gameId }

        binding.toolbarDetail.setNavigationOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
            // OR: findNavController().navigateUp()
        }

        game?.let {
            binding.imgDetail.setImageResource(it.imageResourceId)
            binding.tvDetailTitle.text = getString(it.titleResourceId)
            binding.tvDetailDescription.text = getString(it.detailResourceId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}