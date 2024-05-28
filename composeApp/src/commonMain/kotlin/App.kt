import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import unveilliststyle.composeapp.generated.resources.Res
import unveilliststyle.composeapp.generated.resources.bebe
import unveilliststyle.composeapp.generated.resources.boat
import unveilliststyle.composeapp.generated.resources.cheetah
import unveilliststyle.composeapp.generated.resources.eye
import unveilliststyle.composeapp.generated.resources.images
import unveilliststyle.composeapp.generated.resources.planet
import unveilliststyle.composeapp.generated.resources.runner

@Composable
@Preview
fun App() {
    MaterialTheme {
        UnveilList()

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun UnveilList() {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var isDragging by remember { mutableStateOf(false) }
    val list = listOf(
        Res.drawable.bebe,
        Res.drawable.boat,
        Res.drawable.cheetah,
        Res.drawable.eye,
        Res.drawable.images,
        Res.drawable.planet,
        Res.drawable.runner, Res.drawable.bebe,
        Res.drawable.boat,
        Res.drawable.cheetah,
        Res.drawable.eye,
        Res.drawable.images,
        Res.drawable.planet,
        Res.drawable.runner, Res.drawable.bebe,
        Res.drawable.boat,
        Res.drawable.cheetah,
        Res.drawable.eye,
        Res.drawable.images,
        Res.drawable.planet,
        Res.drawable.runner, Res.drawable.bebe,
        Res.drawable.boat,
        Res.drawable.cheetah,
        Res.drawable.eye,
        Res.drawable.images,
        Res.drawable.planet,
        Res.drawable.runner, Res.drawable.bebe,
        Res.drawable.boat,
        Res.drawable.cheetah,
        Res.drawable.eye,
        Res.drawable.images,
        Res.drawable.planet,
        Res.drawable.runner,
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
                detectDragGestures(onDragStart = {
                    isDragging = true
                },
                    onDrag = { change, dragAmount ->
                        isDragging = true
                        coroutineScope.launch {
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
            itemsIndexed(list) { i, item ->
                CardItem(i, listState, list)
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CardItem(
    i: Int,
    listState: LazyListState,
    list: List<DrawableResource>,
) {
    val stratchValue by animateIntAsState(
        targetValue = if (i == listState.firstVisibleItemIndex + 5) 200 else 0,
        animationSpec = tween(durationMillis = 200)
    )
    val rotation =
        (5f * (i - listState.firstVisibleItemIndex) - (listState.firstVisibleItemScrollOffset / (40f)))
    Box {
        Image(
            painterResource(list[i]),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(170.dp).height(190.dp).graphicsLayer {
                transformOrigin = TransformOrigin(0.3f, -1.0f) // Center pivot
                cameraDistance = 12f * density // Center pivot
                alpha = 0.99f
                rotationY = -30f
                translationX = -(rotation * (30)) + (320f) + (stratchValue)
            }.drawWithContent {
                val radius = size.minDimension / 5
                val colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.1f))
                val brush = Brush.radialGradient(
                    colors = colors,
                    center = center,
                    radius = radius,
                    tileMode = TileMode.Clamp
                )
                drawContent()
                drawRect(
                    brush = brush,
                    blendMode = BlendMode.DstOut
                )
            },
        )
    }
}

@Composable
fun dpToPx(dpValue: Dp): Float {
    val density = LocalDensity.current
    return with(density) { dpValue.toPx() }
}


fun DrawScope.drawFadingEdges(imageSize: Size, edgeSize: Float) {
    val transparentWhite = Color.White.copy(alpha = 0.5f)
    val fullyTransparent = Color.Transparent

    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(transparentWhite, fullyTransparent),
            startY = 0f,
            endY = edgeSize
        ),
        size = Size(imageSize.width, edgeSize)
    )

    drawRect(
        brush = Brush.verticalGradient(
            colors = listOf(fullyTransparent, transparentWhite),
            startY = imageSize.height - edgeSize,
            endY = imageSize.height
        ),
        topLeft = Offset(0f, imageSize.height - edgeSize),
        size = Size(imageSize.width, edgeSize)
    )

    drawRect(
        brush = Brush.horizontalGradient(
            colors = listOf(transparentWhite, fullyTransparent),
            startX = 0f,
            endX = edgeSize
        ),
        size = Size(edgeSize, imageSize.height)
    )

    drawRect(
        brush = Brush.horizontalGradient(
            colors = listOf(fullyTransparent, transparentWhite),
            startX = imageSize.width - edgeSize,
            endX = imageSize.width
        ),
        topLeft = Offset(imageSize.width - edgeSize, 0f),
        size = Size(edgeSize, imageSize.height)
    )
}
