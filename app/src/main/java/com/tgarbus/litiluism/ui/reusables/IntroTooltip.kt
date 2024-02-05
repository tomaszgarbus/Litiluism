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
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInParent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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

class BalloonsQueue {
    data class BalloonsQueueItem(
        val id: String,
        val dependencies: ArrayList<String> = arrayListOf(),
        val fn: suspend () -> Unit,
    )

    // Queue, not running yet.
    private val queue = ArrayList<BalloonsQueueItem>()

    // Completed tasks.
    private val completed = ArrayList<String>()

    // Currently running element.
    private var running: BalloonsQueueItem? = null
    private val mutex = Mutex()

    private fun removeCompletedDependencies(list: ArrayList<String>) {
        list.removeAll { completed.contains(it) }
    }

    private suspend fun runNextIfPossible() {
        if (running != null) {
            return
        }
        val firstRunnable = queue.find { it.dependencies.isEmpty() } ?: return
        running = firstRunnable
        queue.remove(firstRunnable)
        firstRunnable.fn()
    }

    private suspend fun addToQueue(queueItem: BalloonsQueueItem) {
        removeCompletedDependencies(queueItem.dependencies)
        queue.add(queueItem)
        runNextIfPossible()
    }

    suspend fun registerCompleted(id: String) {
        mutex.lock()
        completed.add(id)
        mutex.unlock()
    }

    suspend fun addToQueue(
        id: String,
        dependencies: ArrayList<String> = arrayListOf(),
        fn: suspend () -> Unit,
    ) {
        mutex.lock()
        addToQueue(BalloonsQueueItem(id, dependencies, fn))
        mutex.unlock()
    }

    suspend fun onDismiss() {
        mutex.lock()
        completed.add(running!!.id)
        running = null
        queue.forEach { removeCompletedDependencies(it.dependencies) }
        runNextIfPossible()
        mutex.unlock()
    }
}

@Composable
fun IntroTooltip(
    id: String,
    text: String,
    queue: BalloonsQueue,
    modifier: Modifier = Modifier,
    scrollState: ScrollState? = null,
    dependencies: ArrayList<String> = arrayListOf(),
    contentCornerRadius: Dp = 21.dp,
    content: @Composable () -> Unit,
) {
    val dialogDimColor = colorResource(R.color.dialog_dim)
    val repo = IntroTooltipsRepository(LocalContext.current)
    val isCompleted =
        IntroTooltipsRepository(LocalContext.current).isTooltipCompletedAsFlow(id)
            .collectAsState(null)
    val density = LocalDensity.current
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
        overlayShape = BalloonOverlayRoundRect(
            density.run { contentCornerRadius.toPx() },
            density.run { contentCornerRadius.toPx() }
        )  // TODO: align w/ highlighted element
        elevation = 5f
    }
    var balloonWindow: BalloonWindow? by remember { mutableStateOf(null) }
    val scope = rememberCoroutineScope()
    val layoutCoordinates = remember { mutableStateOf<LayoutCoordinates?>(null) }

    if (isCompleted.value == false) {
        Balloon(
            builder = builder,
            onBalloonWindowInitialized = {
                balloonWindow = it
                scope.launch {
                    queue.addToQueue(id, dependencies) {
                        if (layoutCoordinates.value != null) {
                            scope.launch {
                                scrollState?.animateScrollTo(
                                    value = layoutCoordinates.value!!.positionInParent().y.toInt(),
                                    // TODO: slow down the animation
                                )
                            }
                        }
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
            modifier = modifier.onGloballyPositioned {
                layoutCoordinates.value = it
            }
        ) { _ ->
            content()
        }
    } else {
        content()
        scope.launch { queue.registerCompleted(id) }
    }
}