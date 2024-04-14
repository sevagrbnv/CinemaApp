package ru.sevagrbnv.cinemaapp.presentation.cinemaDetail

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.FragmentDetailBinding
import ru.sevagrbnv.cinemaapp.di.App
//import ru.sevagrbnv.cinemaapp.presentation.ViewModelFactory
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private val viewModel by viewModels<DetailViewModel>()

//    private lateinit var viewModel: DetailViewModel
//
//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory

//    private val component by lazy {
//        (requireActivity().application as App).component
//    }

    private var openPrevFragment: OpenPrevFragment? = null

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentDetailBinding.inflate(
            layoutInflater
        )
    }

    private val reviewAdapter by lazy(LazyThreadSafetyMode.NONE) { ReviewAdapter() }
    private val actorAdapter by lazy(LazyThreadSafetyMode.NONE) { ActorAdapter() }
    private val posterAdapter by lazy(LazyThreadSafetyMode.NONE) { PosterAdapter() }

    override fun onAttach(context: Context) {
//        component.inject(this)
        super.onAttach(context)
        if (context is OpenPrevFragment) {
            openPrevFragment = context
        } else {
            throw RuntimeException("Activity must implement OpenPrevFragment")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this, viewModelFactory)[DetailViewModel::class.java]

        viewModel.id = parseParam()

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect(::renderBasic)
            }
        }

        binding.reviewRecView.adapter = reviewAdapter
        binding.actorRecView.adapter = actorAdapter
        binding.posterRecView.adapter = posterAdapter

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.actors.collectLatest { data ->
                actorAdapter.submitData(data)
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.reviews.collectLatest { data ->
                reviewAdapter.submitData(data)
            }
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.posters.collectLatest { data ->
                posterAdapter.submitData(data)
            }
        }
    }

    private fun renderBasic(state: DetailScreenState) {
        when (state) {
            DetailScreenState.Error -> {
                Snackbar.make(
                    requireView(),
                    getString(R.string.network_error),
                    Snackbar.LENGTH_SHORT
                ).show()
            }

            DetailScreenState.Loading -> {
                binding.textName.isVisible = false
                binding.progressBar.isVisible = true
            }

            is DetailScreenState.Success -> {
                val cinema = state.data
                with(binding) {
                    textName.text = cinema.name
                    textName.isVisible = true
                    progressBar.isVisible = false
                    textDescription.text = cinema.description
                    textRating.text = getString(R.string.Rating, cinema.rating)
                    textGenre.text = getString(R.string.genres, cinema.genres)
                    textCountry.text = getString(R.string.countries, cinema.countries)
                    Picasso.get()
                        .load(cinema.poster)
                        .placeholder(R.drawable.image_placeholder)
                        .into(imagePoster)

                    backButton.setOnClickListener { openPrevFragment?.back() }
                }
            }
        }
    }

    private fun parseParam(): Int {
        val args = requireArguments()
        if (!args.containsKey(ARG_ID))
            throw RuntimeException("Params of screen mode not found")
        return args.getInt(ARG_ID)
    }

    interface OpenPrevFragment {
        fun back()
    }

    override fun onDetach() {
        super.onDetach()
        openPrevFragment = null
    }


    companion object {

        const val ARG_ID = "ID"

        fun newInstance(id: Int) =
            DetailFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_ID, id)
                }
            }
    }
}