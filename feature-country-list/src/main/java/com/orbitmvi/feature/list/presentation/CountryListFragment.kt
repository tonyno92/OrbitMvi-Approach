package com.orbitmvi.feature.list.presentation

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.orbitmvi.core.utils.viewBinding
import com.orbitmvi.feature.list.R
import com.orbitmvi.feature.list.databinding.FragmentCountryListBinding
import com.orbitmvi.feature.list.navigation.CountryListNavigation
import com.orbitmvi.feature.list.presentation.adapter.CountryAdapter
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.orbitmvi.orbit.viewmodel.observe

class CountryListFragment : Fragment(R.layout.fragment_country_list) {
    private val viewModel: CountryListViewModel by viewModel()
    private val navigation: CountryListNavigation by inject()
    private val adapter by lazy { CountryAdapter(navigation::countryDetail) }
    private val binding by viewBinding(FragmentCountryListBinding::bind)
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.dispatch(CountryListAction.LoadAllCountryAction)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.window?.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.colorPrimaryDark)

        binding.countryRecycler.adapter = adapter.apply { stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY }

        viewModel.observe(viewLifecycleOwner, ::render, ::handleSideEffects)
    }

    private fun render(state: CountryListState) {
        binding.countryProgress.isVisible = state.isLoading
        adapter.submitList(state.countries)
    }

    private fun handleSideEffects(effect: CountryListEffect) {
        when (effect) {
            is CountryListEffect.ListErrorEffect -> {
                snackbar = Snackbar.make(
                    binding.countryRecycler,
                    effect.message,
                    Snackbar.LENGTH_INDEFINITE
                )
                    .setAction(getString(R.string.retry)) {
                        viewModel.dispatch(CountryListAction.LoadAllCountryAction)
                    }
                snackbar?.show()
            }
        }
    }
}
