import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import kotlinx.coroutines.CoroutineScope
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.forEachGesture
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import unveilliststyle.composeapp.generated.resources.Res
import unveilliststyle.composeapp.generated.resources.compose_multiplatform
import unveilliststyle.composeapp.generated.resources.the_killing_poster

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val listState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        var isDragging by remember { mutableStateOf(false) }
        val paddingValue by animateDpAsState(
            targetValue = if (isDragging) 190.dp else 190.dp,
            animationSpec = tween(durationMillis = 500)
        )
        val stratchValue by animateIntAsState(
            targetValue = if (isDragging) 35 else 35,
            animationSpec = tween(durationMillis = 500)
        )

        val transitionValue by animateFloatAsState(
            targetValue = if (isDragging) 1f else 1.2f,
            animationSpec = tween(durationMillis = 500)
        )

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            LazyColumn(
                state = listState,
                userScrollEnabled = false,
                modifier = Modifier.graphicsLayer {
                    scaleX = transitionValue
                    scaleY = transitionValue
                }.fillMaxSize().pointerInput(Unit) {
                    // Never reached
                    detectTapGestures(
                        onPress = {
                            isDragging = true
                            tryAwaitRelease()
                            isDragging = false
                        }

                    )

                }.pointerInput(Unit) {
                    detectDragGestures  (onDragStart = {
                        isDragging = true
                    },
                        onDrag = { change, dragAmount ->
                            isDragging = true
                            coroutineScope.launch {
                                // Control the scroll by the drag offset
                                listState.scrollBy(-dragAmount.y)
                            }

                        },
                        onDragEnd = {
                            isDragging = false
                        },
                        onDragCancel = {
                            isDragging = false
                        })
                },
                verticalArrangement = Arrangement.spacedBy((-124).dp),
                horizontalAlignment = Alignment.End
            ) {


                for (i in 1..30) {
                    val rotation =
                        (5f * (i - listState.firstVisibleItemIndex) - (listState.firstVisibleItemScrollOffset / (44f)))
                    item {
                        Box {
                            Image(
                                painter = painterResource(Res.drawable.the_killing_poster),
                                null,
                                modifier = Modifier.height(paddingValue).width(paddingValue).graphicsLayer {
                                    rotationZ = 4f
                                    alpha = 0.9f
                                    cameraDistance = 2f
                                    translationX = -(rotation * (30)) + (100f)
                                },
                                contentScale = ContentScale.FillBounds
                            )

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun dpToPx(dpValue: Dp): Float {
    val density = LocalDensity.current
    return with(density) { dpValue.toPx() }
}