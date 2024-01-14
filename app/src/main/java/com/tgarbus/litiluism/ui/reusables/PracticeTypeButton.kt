package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.Fonts

@Composable
fun PracticeTypeButtonText(
    name: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(10.dp)
            .background(
                color = colorResource(R.color.primary),
                shape = RoundedCornerShape(27.dp)
            )
            .padding(start = 10.dp, end = 10.dp, bottom = 5.dp, top = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name.uppercase(),
                fontFamily = Fonts.sarabunFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.white),
            )
            Image(
                painter = painterResource(R.drawable.icon_forward),
                name,
                modifier = Modifier.size(12.dp)
            )
        }
    }
}

@Composable
fun PracticeTypeButton(
    name: String,
    bgResource: Int,
    boxModifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = boxModifier
            .shadow(
                elevation = 1.dp, shape = RoundedCornerShape(size = 21.dp)
            )
            .fillMaxSize()
            .clip(RoundedCornerShape(size = 21.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = painterResource(bgResource),
            name,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop,
        )
        PracticeTypeButtonText(name)
    }
}