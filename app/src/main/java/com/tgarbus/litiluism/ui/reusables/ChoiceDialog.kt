package com.tgarbus.litiluism.ui.reusables

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R

@Composable
fun ChoiceDialog(
    visible: Boolean, onClose: () -> Unit, title: String, content: @Composable () -> Unit
) {
    androidx.compose.animation.AnimatedVisibility(
        visible = visible, enter = fadeIn(), exit = fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(R.color.dialog_dim))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null) { onClose() }
        ) {}
    }
    androidx.compose.animation.AnimatedVisibility(
        visible = visible, enter = slideInVertically(), exit = slideOutVertically(),
        modifier = Modifier.safeDrawingPadding()
    ) {
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(20.dp)
                .shadow(elevation = 3.dp, RoundedCornerShape(size = 21.dp))
                .clip(RoundedCornerShape(size = 21.dp))
                .verticalScroll(scrollState)
                .background(colorResource(R.color.light_bg))
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Box(modifier = Modifier.fillMaxWidth()) {
                val buttonSize = closeButton(
                    description = LocalContext.current.getString(
                        R.string.content_description_close_dialog
                    ),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) { onClose() }
                Box(modifier = Modifier.padding(horizontal = buttonSize)) {
                    Text(
                        text = title,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.sarabun_regular)),
                            fontWeight = FontWeight.ExtraBold,
                            color = Color(0xFF9C9C9C),
                        )
                    )
                }
            }
            content()
        }
    }
}