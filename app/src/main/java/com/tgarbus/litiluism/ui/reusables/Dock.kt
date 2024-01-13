package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tgarbus.litiluism.R

enum class ButtonType {
    HOME,
    LEARNING,
    PRACTICE,
    MATERIALS,
    SETTINGS
}

fun getResourceId(buttonType: ButtonType): Int {
    return when (buttonType) {
        ButtonType.HOME -> R.drawable.icon_home
        ButtonType.LEARNING -> R.drawable.icon_learning
        ButtonType.MATERIALS -> R.drawable.icon_materials
        ButtonType.PRACTICE -> R.drawable.icon_practice
        ButtonType.SETTINGS -> R.drawable.icon_settings
    }
}

fun getName(buttonType: ButtonType): String {
    return when (buttonType) {
        ButtonType.HOME -> "Home"
        ButtonType.SETTINGS -> "Settings"
        ButtonType.PRACTICE -> "Practice"
        ButtonType.MATERIALS -> "Materials"
        ButtonType.LEARNING -> "Learning"
    }
}

fun getLink(buttonType: ButtonType): String {
    return when (buttonType) {
        ButtonType.HOME -> "home"
        ButtonType.SETTINGS -> "settings"
        ButtonType.PRACTICE -> "practice"
        ButtonType.MATERIALS -> "materials"
        ButtonType.LEARNING -> "learning"
    }
}

@Composable
fun DockButton(
    buttonType: ButtonType, isActive: Boolean, navController: NavController
) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .clickable { navController.navigate(getLink(buttonType)) }
            .padding(10.dp)
    ) {
        Image(
            painterResource(getResourceId(buttonType)),
            getName(buttonType),
            modifier = Modifier
                .size(25.dp),
            colorFilter = if (isActive) ColorFilter.tint(
                colorResource(R.color.primary),
                blendMode = BlendMode.SrcAtop
            ) else null,
        )
    }
}

@Composable
fun Dock(
    activeTab: ButtonType,
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .shadow(10.dp, RoundedCornerShape(27.dp, 27.dp, 0.dp, 0.dp))
                .fillMaxWidth()
                .height(67.dp)
                .clip(RoundedCornerShape(27.dp, 27.dp, 0.dp, 0.dp))
                .background(colorResource(R.color.white))
                .align(Alignment.BottomCenter),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (buttonType in ButtonType.entries) {
                DockButton(buttonType, isActive = (activeTab == buttonType), navController)
            }
        }
    }
}