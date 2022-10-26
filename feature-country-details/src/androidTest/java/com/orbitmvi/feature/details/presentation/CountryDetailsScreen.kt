package com.orbitmvi.feature.details.presentation

import com.agoda.kakao.common.views.KView
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.orbitmvi.feature.details.R
import com.orbitmvi.feature.details.kakao.KAppBarLayout

class CountryDetailsScreen : Screen<CountryDetailsScreen>() {
    val appBar: KAppBarLayout = KAppBarLayout { withId(R.id.detailAppbar) }
    val flag: KImageView = KImageView { withId(R.id.detailFlag) }
    val toolbar: KView = KView { withId(R.id.detailToolbar) }
    val overview: KTextView = KTextView { withId(R.id.detailOverview) }
    val continent: KTextView = KTextView { withId(R.id.detailContinent) }
    val language: KTextView = KTextView { withId(R.id.detailLanguages) }
    val coatOfArms: KImageView = KImageView { withId(R.id.detailCoatOfArms) }
}
