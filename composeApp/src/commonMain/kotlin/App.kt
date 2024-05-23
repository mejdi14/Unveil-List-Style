import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
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
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy((-84).dp),
                horizontalAlignment = Alignment.End
            ) {
                for (i in 1..10) {
                    item {
                        Image(painter = painterResource(Res.drawable.the_killing_poster),
                            null, modifier = Modifier.width(400.dp).height(150.dp).graphicsLayer {
                                rotationY = -10f
                                translationX = -(i * 20f)
                            }.align(Alignment.CenterHorizontally))
                    }
                }
            }
        }
    }
}