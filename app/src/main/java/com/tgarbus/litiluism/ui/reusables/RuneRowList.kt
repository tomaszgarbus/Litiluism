package com.tgarbus.litiluism.ui.reusables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.BaseRuneRow
import com.tgarbus.litiluism.data.StaticContentRepository
import com.tgarbus.litiluism.data.baseRuneRowToString
import com.tgarbus.litiluism.data.getIconResourceId
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily

@Composable
fun RuneRowList(
    onSelectItem: (BaseRuneRow) -> Unit
) {
    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (runeRow in BaseRuneRow.entries.filterNot { it == BaseRuneRow.ANY }) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .shadow(1.dp, RoundedCornerShape(21.dp))
                    .clip(RoundedCornerShape(21.dp))
                    .background(Color.White, RoundedCornerShape(21.dp))
                    .clickable { onSelectItem(runeRow) }
                    .padding(vertical = 10.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(painterResource(runeRow.getIconResourceId()), runeRow.name)
                Row(
                    Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(
                            baseRuneRowToString(runeRow),
                            fontFamily = sarabunFontFamily,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "${
                                StaticContentRepository.getInstance()
                                    .getFullBaseRuneRow(runeRow)!!.mapping.size
                            } symbols",
                            fontFamily = sarabunFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.dim_grey_text)
                        )
                    }
                    Icon(painterResource(R.drawable.icon_forward), "select")
                }
            }
        }
    }
}