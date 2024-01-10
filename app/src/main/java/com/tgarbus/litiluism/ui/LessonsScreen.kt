package com.tgarbus.litiluism.ui

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.Html
import android.text.Html.fromHtml
import android.text.Spanned
import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.ui.reusables.ButtonType
import com.tgarbus.litiluism.ui.reusables.Dock
import com.tgarbus.litiluism.ui.reusables.Header
import org.xml.sax.XMLReader

val htmlText = """Here <b>you</b> can see a cool stone: <br/> <img src="image_kylverstone">"""

class ImageGetter(val context: Context) : Html.ImageGetter {
    override fun getDrawable(source: String?): Drawable {
        val drawable = context.resources.getDrawable(
            context.resources.getIdentifier(
                source,
                "drawable",
                context.packageName
            )
        )
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        return drawable
    }

}

@Composable
fun HtmlBlock(spanned: Spanned) {
    AndroidView(factory = { context ->
        TextView(context).apply {
            text = spanned
        }
    })
}

@Composable
fun LessonsScreen(navController: NavController) {
    val scrollState = rememberScrollState()
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                // TODO: set bg color through theme
                .background(colorResource(R.color.light_bg))
                .verticalScroll(scrollState)
                .padding(vertical = 20.dp)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Header(text = "Lessons")
            val spanned: Spanned =
                fromHtml(htmlText, 0, ImageGetter(LocalContext.current), null)

            HtmlBlock(spanned)
        }
        Dock(ButtonType.LEARNING, navController)
    }
}