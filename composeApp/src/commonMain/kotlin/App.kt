import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
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
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End,) {
            LazyColumn(
                state =  listState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy((-84).dp),
                horizontalAlignment = Alignment.End
            ) {


                        for (i in 1..30) {

                    val initialRotationValue = if (i > listState.firstVisibleItemIndex) {
                        1f * (i - listState.firstVisibleItemIndex) - (listState.firstVisibleItemScrollOffset / 80)
                    } else {
                        0f
                    }

                            val halfScreenSize = (1000 / 2)

                            val rotation =
                                 ( 5f * (i - listState.firstVisibleItemIndex) - (listState.firstVisibleItemScrollOffset / 70f))
                                if(i == 4)
                                Logger.i("rotation: ${(listState.firstVisibleItemScrollOffset / 22f)}")
                    item {
                        Image(painter = painterResource(Res.drawable.the_killing_poster),
                            null, modifier = Modifier.width(200.dp).height(200.dp).graphicsLayer {
                                rotationY = 30f
                                alpha = 0.9f
                                cameraDistance = 39f
                                translationX = -(rotation * 20) + 120f
                            }, contentScale = ContentScale.FillHeight)
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