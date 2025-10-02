package com.vcompass.core.compose_view.image

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vcompass.core.R
import com.vcompass.core.dimen.MyDimen

@Composable
fun SocialImageTab(
    modifier: Modifier = Modifier,
    facebookClick: () -> Unit = {},
    googleClick: () -> Unit = {},
    linkedinClick: () -> Unit = {},
) {
    val socialModifier = Modifier.size(MyDimen.p48)
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CoreImage(
            source = CoreImageSource.Drawable(R.drawable.ic_facebook_50dp),
            modifier = socialModifier,
            onClick = facebookClick
        )
        CoreImage(
            source = CoreImageSource.Drawable(R.drawable.ic_google_50dp),
            modifier = socialModifier,
            onClick = googleClick
        )
    }
}