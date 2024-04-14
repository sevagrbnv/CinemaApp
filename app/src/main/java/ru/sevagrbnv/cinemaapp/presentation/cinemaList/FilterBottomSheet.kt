package ru.sevagrbnv.cinemaapp.presentation.cinemaList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.sevagrbnv.cinemaapp.R
import ru.sevagrbnv.cinemaapp.databinding.FragmentFilterBottomSheetBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FilterBottomSheet : BottomSheetDialogFragment() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        FragmentFilterBottomSheetBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClear.setOnClickListener {
            clear()
        }

        binding.btnApply.setOnClickListener {
            filterListener?.onFiltersApplied(
                binding.yearEditText.text.toString(),
                binding.countryEditText.text.toString(),
                binding.ratingEditText.text.toString()
            )

            dismiss()
        }
    }

    private fun clear() {
        binding.yearEditText.text?.clear()
        binding.countryEditText.text?.clear()
        binding.ratingEditText.text?.clear()
    }

    private var filterListener: FilterListener? = null

    fun setFilterListener(listener: FilterListener) {
        filterListener = listener
    }


    companion object {
        const val TAG = "filter_bottom_sheet"
    }
}

interface FilterListener {
    fun onFiltersApplied(year: String, country: String, rating: String)
}