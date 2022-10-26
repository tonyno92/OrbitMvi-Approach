package com.orbitmvi.feature.details.presentation

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import coil.load
import com.orbitmvi.core.utils.viewBinding
import com.orbitmvi.feature.details.R
import com.orbitmvi.feature.details.databinding.FragmentCountryDetailsBinding
import com.orbitmvi.feature.details.navigation.CountryDetailNavigation
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.snackbar.Snackbar
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.orbitmvi.orbit.viewmodel.observe
import kotlin.math.abs

class CountryDetailFragment : Fragment(R.layout.fragment_country_details), AppBarLayout.OnOffsetChangedListener {
    private val viewModel: CountryDetailViewModel by viewModel()
    private val navigation: CountryDetailNavigation by inject()
    private val binding by viewBinding(FragmentCountryDetailsBinding::bind)
    private var countryId = 0
    private var isEmblemShown = true
    private var maxScrollSize = 0.0

    companion object {
        const val EMBLEM_ANIMATION_DURATION = 200L
        const val RATIO_TO_ANIMATE_EMBLEM = 0.75
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.window?.statusBarColor = Color.TRANSPARENT

        arguments?.apply {
            countryId = getInt("country_id")
        }
        openCountryDetails()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // This if condition is only to avoid the block to executed in instrumentation tests
        if (activity is AppCompatActivity) {
            (activity as AppCompatActivity).apply {
                setSupportActionBar(binding.detailToolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }

        binding.detailToolbar.setNavigationOnClickListener {
            navigation.goToCountryList()
        }

        viewModel.observe(viewLifecycleOwner, ::render, ::handleSideEffects)
    }

    override fun onResume() {
        super.onResume()
        binding.detailAppbar.addOnOffsetChangedListener(this)
    }

    override fun onPause() {
        binding.detailAppbar.removeOnOffsetChangedListener(this)
        super.onPause()
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {
        if (maxScrollSize == 0.0) maxScrollSize = appBarLayout.totalScrollRange.toDouble()

        val scrollRatio = abs(i) / maxScrollSize

        if (scrollRatio >= RATIO_TO_ANIMATE_EMBLEM && isEmblemShown) {
            isEmblemShown = false
            ViewCompat.animate(binding.detailCoatOfArms)
            animateView(binding.detailCoatOfArms, false)
        } else if (scrollRatio <= RATIO_TO_ANIMATE_EMBLEM && !isEmblemShown) {
            isEmblemShown = true
            animateView(binding.detailCoatOfArms, true)
        }
    }

    private fun render(state: CountryDetailState) {
        binding.detailRoot.isInvisible = true
        binding.detailProgress.isVisible = state.isLoading
        state.country?.apply {
            binding.detailCollapsingToolbar.title = name
            binding.detailFlag.load(flag)
            binding.detailCoatOfArms.load(coatOfArms)
            binding.detailOverview.text = name
            binding.detailContinent.text = continents.joinToString()
            binding.detailLanguages.text = languages?.joinToString()
            binding.detailRoot.isVisible = true
        }
    }

    private fun handleSideEffects(effect: CountryDetailEffect) {
        when (effect) {
            is CountryDetailEffect.DetailsErrorEffect -> {
                Snackbar.make(binding.detailRoot, effect.message, Snackbar.LENGTH_INDEFINITE)
                    .setAction(getString(R.string.retry)) {
                        openCountryDetails()
                    }
                    .show()
            }
        }
    }

    private fun openCountryDetails() {
        viewModel.dispatch(CountryDetailAction.OpenCountryDetailAction(countryId))
    }

    private fun animateView(view: View, isVisible: Boolean) {
        view.animate()
            .scaleY(if (isVisible) 1f else 0f)
            .scaleX(if (isVisible) 1f else 0f)
            .alpha(if (isVisible) 1f else 0f)
            .setDuration(EMBLEM_ANIMATION_DURATION)
            .start()
    }
}
