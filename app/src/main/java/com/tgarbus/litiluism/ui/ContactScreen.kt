package com.tgarbus.litiluism.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.HeaderWithBackButton

@Composable
fun ContactScreen(navController: NavController) {
    FullScreenPaddedColumn {
        val localUriHandler = LocalUriHandler.current
        HeaderWithBackButton(
            text = LocalContext.current.getString(R.string.contact_contact),
            navController
        )
        Text(
            text = LocalContext.current.getString(
                R.string.contact_help_text
            ),
            fontFamily = Fonts.sarabunFontFamily,
            color = colorResource(R.color.dark_grey)
        )
        SettingsItem(LocalContext.current.getString(R.string.contact_rate), R.drawable.icon_rate) {
            localUriHandler.openUri("https://play.google.com/store/apps/details?id=com.tgarbus.litiluism")
        }
        SettingsItem(LocalContext.current.getString(R.string.contact_instagram), R.drawable.icon_instagram) {
            localUriHandler.openUri("https://www.instagram.com/litiluism_app/")
        }
        SettingsItem(LocalContext.current.getString(R.string.contact_reddit), R.drawable.icon_reddit) {
            localUriHandler.openUri("https://www.reddit.com/user/litiluism_app/")
        }
        SettingsItem(LocalContext.current.getString(R.string.contact_email), R.drawable.icon_email) {
            localUriHandler.openUri("mailto:hej@litiluism.com")
        }
    }
}