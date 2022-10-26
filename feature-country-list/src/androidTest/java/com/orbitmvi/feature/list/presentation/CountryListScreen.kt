package com.orbitmvi.feature.list.presentation

import android.view.View
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KSnackbar
import com.orbitmvi.feature.list.R
import org.hamcrest.Matcher

class CountryListScreen : Screen<CountryListScreen>() {
    val recycler: KRecyclerView = KRecyclerView({
        withId(R.id.countryRecycler)
    }, itemTypeBuilder = {
            itemType(::CountryItem)
        })
    val snackBar = KSnackbar()

    class CountryItem(parent: Matcher<View>) : KRecyclerItem<CountryItem>(parent) {
        val image: KImageView = KImageView { withMatcher(parent) }
    }
}
