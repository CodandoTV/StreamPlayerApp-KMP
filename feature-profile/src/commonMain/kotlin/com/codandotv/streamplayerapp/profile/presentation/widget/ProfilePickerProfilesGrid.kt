package com.codandotv.streamplayerapp.profile.presentation.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.codandotv.streamplayerapp.core_shared_ui.widget.WebImage
import com.codandotv.streamplayerapp.profile.domain.ProfileStream
import com.codandotv.streamplayerapp.profile.presentation.screens.ProfilePickerStreamsUIState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import streamplayerapp_kmp.feature_profile.generated.resources.Res
import streamplayerapp_kmp.feature_profile.generated.resources.image_placeholder
import streamplayerapp_kmp.feature_profile.generated.resources.profile_current_profile_name

@Composable
fun ProfilePickerProfilesGrid(
    uiState: ProfilePickerStreamsUIState,
    animatedProfileAlpha: Float,
    onSetScreenSize: (Float, Float, Int, Int) -> Unit,
    onClickSelectedProfile: (ProfileStream) -> Unit,
    onSetLastItemPositioned: (Boolean) -> Unit
) {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .padding(top = 100.dp)
            .graphicsLayer(alpha = animatedProfileAlpha)
    ) {
        // for each item in the list will be calculated the position of the item
        // lazyGrid is not used because it is not possible to calculate correctly the position of the item
        onSetScreenSize(
            this.maxWidth.value,
            this.maxHeight.value,
            this.maxWidth.dpToPx(),
            this.maxHeight.dpToPx()
        )

        with(uiState) {

            profilesStream.forEach { profile ->
                val profileItemPosition = if (offsetProfiles.isNotEmpty()) {
                    with(offsetProfiles[profilesStream.indexOf(profile)]) {
                        IntOffset(first, second)
                    }
                } else {
                    IntOffset(0, 0)
                }

                ProfileItem(
                    state = uiState,
                    profileItemPosition, profile, onClickSelectedProfile
                )

                if (profilesStream.indexOf(profile) == profilesStream.size - 1) {
                    onSetLastItemPositioned(true)
                }
            }
        }
    }
}

@Suppress("MagicNumber")
@Composable
private fun ProfileItem(
    state: ProfilePickerStreamsUIState,
    profileItemPosition: IntOffset,
    profile: ProfileStream,
    onClickSelectedProfile: (ProfileStream) -> Unit
) {
    with(state) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .offset {
                    profileItemPosition
                }
        ) {
            WebImage(
                imageUrl = profile.imageUrl,
                //placeholder = painterResource(Res.drawable.image_placeholder),
                contentDescription = stringResource(
                    Res.string.profile_current_profile_name,
                    profile.name
                ),
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .clip(RoundedCornerShape(5))
                    .alpha(if (selectedItem == profile) selectedImageAlpha else 1f)
                    .size(defaultImageSize.dp)
                    .clickable {
                        onClickSelectedProfile(profile)
                    }
            )

            Text(
                text = profile.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))
        }
    }
}