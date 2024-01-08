package com.tgarbus.litiluism.ui.reusables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.BaseRuneRow
import com.tgarbus.litiluism.data.StaticContentRepository

@Composable
fun ListOfRuneRowsDialog(
    visible: Boolean,
    onClose: () -> Unit,
    title: String,
    onSelectItem: (BaseRuneRow) -> Unit
) {
    val sarabunFontFamily = FontFamily(
        Font(R.font.sarabun_regular, FontWeight.Normal),
        Font(R.font.sarabun_bold, FontWeight.Bold),
        Font(R.font.sarabun_thin, FontWeight.Thin),
    )
    ChoiceDialog(
        visible = visible,
        onClose = onClose,
        title = title,
    ) {
        for (baseRuneRow in BaseRuneRow.entries) {
            if (baseRuneRow != BaseRuneRow.ANY) {
                val runeRow =
                    StaticContentRepository.getInstance().getFullBaseRuneRow(baseRuneRow)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectItem(baseRuneRow) }
                ) {
                    Text(
                        runeRow!!.name,
                        fontFamily = sarabunFontFamily,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(600)
                    )
                }
            }
        }
    }
}
