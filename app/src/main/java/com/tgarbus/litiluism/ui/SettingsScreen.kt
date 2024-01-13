package com.tgarbus.litiluism.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.Header

@Composable
fun SettingsItem(
    name: String,
    iconResourceId: Int,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .shadow(1.dp, RoundedCornerShape(21.dp))
            .clip(RoundedCornerShape(21.dp))
            .fillMaxWidth()
            .background(colorResource(R.color.white))
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painterResource(iconResourceId), name,
                Modifier
                    .size(20.dp)
            )
            Spacer(Modifier.width(11.dp))
            Text(
                name,
                fontFamily = sarabunFontFamily,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.dark_grey),
            )
        }
        Image(
            painterResource(R.drawable.icon_forward_with_gradient), name,
            Modifier
                .size(20.dp)
        )
    }
}

@Composable
fun SettingsScreen(navController: NavController) {
    FullScreenPaddedColumn {
        Header("Settings")
        SettingsItem("About app", R.drawable.icon_info) {
            navController.navigate("about")
        }
    }
    Dock(ButtonType.SETTINGS, navController)
}