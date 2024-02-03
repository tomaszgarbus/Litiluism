package com.tgarbus.litiluism.ui.reusables

import android.graphics.Color
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.skydoves.balloon.ArrowPositionRules
import com.skydoves.balloon.BalloonAnimation
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.compose.Balloon
import com.skydoves.balloon.compose.BalloonWindow
import com.skydoves.balloon.compose.rememberBalloonBuilder
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
import com.tgarbus.litiluism.R
import com.tgarbus.litiluism.data.IntroTooltipsRepository
import com.tgarbus.litiluism.ui.Fonts.Companion.sarabunFontFamily
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

// Principles of BalloonsQueue:
// 1. If empty, trigger the newly added element.
// 2. If non-empty, only push at the end of the queue.
// 3. If non-empty, the first element is currently in execution.
class BalloonsQueue {
    private val queue = ArrayList<() -> Unit>()
    private val mutex = Mutex()

    suspend fun addToQueue(fn: () -> Unit) {
        mutex.lock()
        queue.add(fn)
        if (queue.size == 1) {
            runNextFromQueue()
        }
        mutex.unlock()
    }

    private fun runNextFromQueue() {
        if (queue.isNotEmpty()) {
            val next = queue.first()
            next()
        }
    }

    suspend fun onDismiss() {
        mutex.lock()
        // Queue cannot be empty now.
        queue.removeFirst()
        runNextFromQueue()
        mutex.unlock()
    }
}

// TODO: https://github.com/skydoves/Balloon
@Composable
fun IntroTooltip(
    id: String,
    text: String,
    queue: BalloonsQueue,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val dialogDimColor = colorResource(R.color.dialog_dim)
    val repo = IntroTooltipsRepository(LocalContext.current)
    val isCompleted =
        IntroTooltipsRepository(LocalContext.current).isTooltipCompletedAsFlow(id)
            .collectAsState(null)
    // create and remember a builder of Balloon.
    val builder = rememberBalloonBuilder {
        setArrowSize(10)
        setArrowPosition(0.5f)
        setArrowPositionRules(ArrowPositionRules.ALIGN_ANCHOR)
        setWidth(BalloonSizeSpec.WRAP)
        setHeight(BalloonSizeSpec.WRAP)
        setPadding(12)
        setMarginHorizontal(12)
        setCornerRadius(8f)
        setBackgroundColorResource(R.color.secondary)
        setBalloonAnimation(BalloonAnimation.ELASTIC)
        setDismissWhenTouchOutside(true)
        // Utseende
        setBackgroundColor(Color.WHITE)
        // Overlay
        isVisibleOverlay = true
        overlayColor = dialogDimColor.toArgb()
        overlayShape = BalloonOverlayRoundRect(21f, 21f)  // TODO: align w/ highlighted element
        elevation = 5f
    }
    var balloonWindow: BalloonWindow? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()

    if (isCompleted.value == false) {
        Balloon(
            builder = builder,
            onBalloonWindowInitialized = {
                balloonWindow = it
                scope.launch {
                    queue.addToQueue {
                        balloonWindow?.showAlignBottom()
                    }
                }
                balloonWindow!!.setOnBalloonDismissListener {
                    scope.launch {
                        queue.onDismiss()
                        repo.markIntroTooltipAsCompleted(id)
                    }
                }
            },
            balloonContent = {
                Column {
                    Text(text)
                    Text(
                        "GOT IT",
                        fontFamily = sarabunFontFamily,
                        color = colorResource(R.color.primary),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .align(Alignment.End)
                            .clickable { balloonWindow!!.dismiss() }
                    )
                }
            },
            modifier = modifier
        ) { _ ->
            content()
        }
    } else {
        content()
    }
}