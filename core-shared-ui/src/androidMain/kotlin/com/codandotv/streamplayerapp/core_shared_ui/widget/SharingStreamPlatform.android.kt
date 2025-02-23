package com.codandotv.streamplayerapp.core_shared_ui.widget

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.codandotv.streamplayerapp.core_shared_ui.resources.Colors
import com.codandotv.streamplayerapp.core_shared_ui.utils.Sharing.ANIMATION_DURATION
import com.codandotv.streamplayerapp.core_shared_ui.utils.Sharing.ANIMATION_EXECUTION_DELAY
import com.codandotv.streamplayerapp.core_shared_ui.utils.Sharing.COPY_CONTENT_TYPE_TEXT
import com.codandotv.streamplayerapp.core_shared_ui.utils.Sharing.OPTIONS_TITLE_MESSAGE
import com.codandotv.streamplayerapp.core_shared_ui.utils.Sharing.SHARING_DATA_TYPE_TEXT
import com.codandotv.streamplayerapp.core_shared_ui.utils.Sharing.SMS_CONTENT_BODY
import com.codandotv.streamplayerapp.core_shared_ui.utils.Sharing.SMS_CONTENT_TYPE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import streamplayerapp_kmp.core_shared_ui.generated.resources.Res
import streamplayerapp_kmp.core_shared_ui.generated.resources.ic_close
import streamplayerapp_kmp.core_shared_ui.generated.resources.ic_copy_content
import streamplayerapp_kmp.core_shared_ui.generated.resources.ic_instagram
import streamplayerapp_kmp.core_shared_ui.generated.resources.ic_message
import streamplayerapp_kmp.core_shared_ui.generated.resources.ic_whatsapp
import streamplayerapp_kmp.core_shared_ui.generated.resources.instagram_not_installed_message
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_link_copied_message
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_title_instagram
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_title_link
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_title_message
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_title_more_options
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_title_sms
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_title_whatsapp
import streamplayerapp_kmp.core_shared_ui.generated.resources.sharing_whatsapp_message
import streamplayerapp_kmp.core_shared_ui.generated.resources.sms_app_error_message
import streamplayerapp_kmp.core_shared_ui.generated.resources.whatsapp_not_installed_message

@Composable
actual fun SharingStreamPlatform(
    modifier: Modifier,
    contentTitle: String,
    contentUrl: String,
    setShowDialog: (Boolean) -> Unit
) {

    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val animateTrigger = remember { mutableStateOf(false) }
    val context = LocalContext.current

    val linkCopiedMessage = stringResource(Res.string.sharing_link_copied_message)
    val contentMessage =
        stringResource(Res.string.sharing_whatsapp_message, contentTitle, contentUrl)
    val whatsAppNotInstalledMessage = stringResource(Res.string.whatsapp_not_installed_message)
    val instagramNotInstalledMessage = stringResource(Res.string.instagram_not_installed_message)
    val smsErrorMessage = stringResource(Res.string.sms_app_error_message)

    LaunchedEffect(key1 = Unit) {
        launch {
            delay(ANIMATION_EXECUTION_DELAY)
            animateTrigger.value = true
        }
    }
    Box(
        contentAlignment = Alignment.BottomCenter
    ) {
        AnimatedSlideInTransition(visible = animateTrigger.value) {
            Surface(
                color = Colors.AlphaBlack,
                modifier = Modifier.fillMaxSize()
            ) {
                Box(
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(Res.string.sharing_title_message),
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontFamily = FontFamily.Default,
                                color = Color.White
                            )
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    shareWhatsAppMessage(
                                        context,
                                        contentMessage,
                                        whatsAppNotInstalledMessage
                                    )
                                    coroutineScope.launch {
                                        startDismissWithExitAnimation(animateTrigger) { setShowDialog(false) }
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_whatsapp),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(Res.string.sharing_title_whatsapp),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Default,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    shareSmsMessage(
                                        context,
                                        contentMessage,
                                        smsErrorMessage
                                    )
                                    coroutineScope.launch {
                                        startDismissWithExitAnimation(animateTrigger) { setShowDialog(false) }
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_message),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(Res.string.sharing_title_sms),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Default,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    shareInstagramStory(context, contentUrl, instagramNotInstalledMessage)
                                    coroutineScope.launch {
                                        startDismissWithExitAnimation(animateTrigger) { setShowDialog(false) }
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_instagram),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(24.dp)
                                    .height(24.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(Res.string.sharing_title_instagram),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Default,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clickable {
                                    copyContentLink(context, linkCopiedMessage, contentUrl)
                                    coroutineScope.launch {
                                        startDismissWithExitAnimation(animateTrigger) { setShowDialog(false) }
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_copy_content),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(28.dp)
                                    .height(28.dp)
                                    .background(
                                        color = Color.DarkGray,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(Res.string.sharing_title_link),
                                style = TextStyle(
                                    fontSize = 18.sp,
                                    fontFamily = FontFamily.Default,
                                    color = Color.White
                                )
                            )
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = stringResource(Res.string.sharing_title_more_options),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontFamily = FontFamily.Default,
                                color = Color.White
                            ),
                            modifier = Modifier.clickable {
                                callSharingOptions(
                                    context,
                                    contentMessage
                                )
                                coroutineScope.launch {
                                    startDismissWithExitAnimation(animateTrigger) { setShowDialog(false) }
                                }
                            }
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .width(64.dp)
                                .height(64.dp)
                                .clip(CircleShape)
                                .background(
                                    colorResource(id = android.R.color.white)
                                )
                                .clickable {
                                    coroutineScope.launch {
                                        startDismissWithExitAnimation(animateTrigger) {
                                            setShowDialog(
                                                false
                                            )
                                        }
                                    }
                                }
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.ic_close),
                                contentDescription = null,
                                modifier = Modifier
                                    .width(32.dp)
                                    .height(32.dp)
                            )
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }
        }
        BackHandler {
            coroutineScope.launch {
                startDismissWithExitAnimation(animateTrigger) { setShowDialog(false) }
            }
        }
    }
}

private fun shareInstagramStory(
    context: Context,
    contentUrl: String,
    errorMessage: String
) {
//    if (isPackageInstalled(WHATSAPP_PACKAGE_SHARING)) {
//        Thread {
            //TODO:
//            val imgBitmapUri = getUriFromUrlImage(contentUrl)
//
//            val storiesIntent = Intent(INSTAGRAM_STORY_DESTINATION)
//            storiesIntent.setDataAndType(imgBitmapUri, SHARING_DATA_TYPE_IMAGE)
//            storiesIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            storiesIntent.setPackage(INSTAGRAM_PACKAGE_SHARING)
//
//            context.grantUriPermission(
//                INSTAGRAM_PACKAGE_SHARING, imgBitmapUri, Intent.FLAG_GRANT_READ_URI_PERMISSION
//            )
//            (context as Activity).runOnUiThread {
//                context.startActivity(storiesIntent)
//            }
//        }.start()
//    } else {
//        Toast.makeText(
//            context,
//            errorMessage,
//            Toast.LENGTH_SHORT
//        ).show()
//    }
}

private fun shareWhatsAppMessage(
    context: Context,
    message: String,
    errorMessage: String
) {
//    if (isPackageInstalled(WHATSAPP_PACKAGE_SHARING)) {
//        val intent = Intent(Intent.ACTION_SEND)
//        intent.type = SHARING_DATA_TYPE_TEXT
//        intent.setPackage(WHATSAPP_PACKAGE_SHARING)
//        intent.putExtra(Intent.EXTRA_TEXT, message)
//        context.startActivity(intent)
//    } else {
        Toast.makeText(
            context,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
//    }
}

private fun copyContentLink(context: Context, linkCopiedMessage: String, contentUrl: String) {
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clipData = ClipData.newPlainText(COPY_CONTENT_TYPE_TEXT, contentUrl)
    clipboardManager.setPrimaryClip(clipData)
    Toast.makeText(
        context,
        linkCopiedMessage,
        Toast.LENGTH_SHORT
    ).show()
}

@Suppress("SwallowedException")
private fun shareSmsMessage(
    context: Context,
    message: String,
    errorMessage: String
) {
    try {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.putExtra(SMS_CONTENT_BODY, message)
        intent.data = Uri.parse(SMS_CONTENT_TYPE)
        context.startActivity(intent)
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(
            context,
            errorMessage,
            Toast.LENGTH_SHORT
        ).show()
    }
}

private fun callSharingOptions(context: Context, message: String) {
    val intent = Intent(Intent.ACTION_SEND)
        .putExtra(Intent.EXTRA_TEXT, message)
        .setType(SHARING_DATA_TYPE_TEXT)
    ContextCompat.startActivity(
        context,
        Intent.createChooser(intent, OPTIONS_TITLE_MESSAGE),
        null
    )
}

@Composable
internal fun AnimatedSlideInTransition(
    visible: Boolean,
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(animationSpec = keyframes {
            this.durationMillis = ANIMATION_DURATION
        }, initialOffsetY = { fullHeight -> fullHeight }),
        exit = slideOutVertically(animationSpec = keyframes {
            this.durationMillis = ANIMATION_DURATION
        }, targetOffsetY = { fullHeight -> fullHeight }),
        content = content
    )
}

suspend fun startDismissWithExitAnimation(
    animateTrigger: MutableState<Boolean>,
    onDismissRequest: () -> Unit
) {
    animateTrigger.value = false
    delay(ANIMATION_EXECUTION_DELAY)
    onDismissRequest()
}
