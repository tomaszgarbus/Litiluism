package com.tgarbus.litiluism.ui

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.InputMethod
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import com.tgarbus.litiluism.ui.reusables.FullScreenPaddedColumn
import com.tgarbus.litiluism.ui.reusables.HeaderWithBackButton
import com.tgarbus.litiluism.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch

@Composable
fun Option(
    name: String,
    iconResourceId: Int,
    isActive: Boolean,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .shadow(elevation = 1.dp, RoundedCornerShape(21.dp))
            .background(
                if (isActive) colorResource(R.color.active_settings_option) else Color.White,
                RoundedCornerShape(21.dp)
            )
            .clickable { onClick() }
            .padding(20.dp)
    ) {
        Icon(
            painterResource(iconResourceId), name,
            Modifier
                .size(24.dp)
        )
        Spacer(Modifier.width(11.dp))
        Text(
            name,
            fontFamily = sarabunFontFamily,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(R.color.dark_grey)
        )
    }
}

@Composable
fun InputMethodScreen(navController: NavController, viewModel: SettingsViewModel = viewModel()) {
    val inputMethod =
        viewModel.getInputMethodAsFlow(LocalContext.current).collectAsState(InputMethod.VARIANTS)
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    FullScreenPaddedColumn {
        HeaderWithBackButton(
            LocalContext.current.getString(
                R.string.input_method_input_method
            ), navController
        )
        Text(
            text = LocalContext.current.getString(
                R.string.input_method_help_text
            ),
            fontFamily = sarabunFontFamily,
            color = colorResource(R.color.dark_grey)
        )
        Option(
            LocalContext.current.getString(
                R.string.input_method_variants
            ),
            R.drawable.icon_input_method_variants,
            inputMethod.value == InputMethod.VARIANTS,
        ) {
            scope.launch {
                viewModel.setInputMethod(context, InputMethod.VARIANTS)
            }
        }
        Option(
            LocalContext.current.getString(
                R.string.input_method_manually
            ),
            R.drawable.icon_input_method_manually,
            inputMethod.value == InputMethod.KEYBOARD,
        ) {
            scope.launch {
                viewModel.setInputMethod(context, InputMethod.KEYBOARD)
            }
        }
    }
}