package ru.sevagrbnv.cinemaapp.presentation.cinemaList

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.HttpException
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.FragmentCinemaListBinding
import ru.sevagrbnv.cinemaapp.di.App
//import ru.sevagrbnv.cinemaapp.presentation.ViewModelFactory
import ru.sevagrbnv.cinemaapp.presentation.cinemaDetail.DetailFragment
import java.net.UnknownHostException
import javax.inject.Inject

@AndroidEntryPoint
class CinemaListFragment : Fragment(), FilterListener, OnItemClickListener {


    private val viewModel by viewModels<CinemaListViewModel>()
//    private lateinit var viewModel: CinemaListViewModel

//    @Inject
//    lateinit var viewModelFactory: ViewModelFactory

//    private val component by lazy {
//        (requireActivity().application as App).component
//    }

    private val binding by lazy { FragmentCinemaListBinding.inflate(layoutInflater) }
    private val cinemaAdapter by lazy(LazyThreadSafetyMode.NONE) { CinemaAdapter(this) }
    private val loaderStateAdapter by lazy(LazyThreadSafetyMode.NONE) { CinemaListLoaderStateAdapter { cinemaAdapter.retry() } }

    override fun onAttach(context: Context) {
//        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        viewModel = ViewModelProvider(this, viewModelFactory)[CinemaListViewModel::class.java]

        binding.recyclerView.apply {
            adapter = cinemaAdapter.withLoadStateFooter(footer = loaderStateAdapter)
            setHasFixedSize(true)
        }

        binding.editText.addTextChangedListener {
            it?.let {
                viewModel.searchQueryPublisher.tryEmit(it.toString())
            }
        }

        cinemaAdapter.addLoadStateListener { state ->
            when (state.refresh) {
                is LoadState.Error -> throw UnknownHostException()
                LoadState.Loading -> {
                    binding.progressBar.isVisible = true
                    binding.recyclerView.isVisible = false
                }

                is LoadState.NotLoading -> {
                    binding.progressBar.isVisible = false
                    binding.recyclerView.isVisible = true
                }
            }
        }

        binding.filter.setOnClickListener {
            val bottomSheetDialog = FilterBottomSheet()
            bottomSheetDialog.setFilterListener(this)
            bottomSheetDialog.show(parentFragmentManager, FilterBottomSheet.TAG)
        }

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch() {
            try {
                viewModel.data.collectLatest { data ->
                    if (isNetworkAvailable(requireView().context))
                        cinemaAdapter.submitData(data)
                    else {
                        binding.progressBar.isVisible = false
                        Snackbar.make(
                            requireView(),
                            getString(R.string.network_error),
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            } catch (e: HttpException) {
                binding.progressBar.isVisible = false
                Snackbar.make(
                    requireView(),
                    "HTTP Error: ${e.code()}",
                    Snackbar.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                binding.progressBar.isVisible = false
                Log.e("123123", e.message.toString())
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    "Error: ${e.message}",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    override fun onFiltersApplied(year: String, country: String, rating: String) {
        viewModel.setFilters(year, country, rating)
        observeData()
    }

    override fun onItemClick(itemId: Int) {
        val fragment = DetailFragment.newInstance(itemId)
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()
    }
}

interface OnItemClickListener {
    fun onItemClick(itemId: Int)
}