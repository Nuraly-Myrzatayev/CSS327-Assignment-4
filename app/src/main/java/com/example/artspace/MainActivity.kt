package com.example.artspace

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.PointerInputEventHandler
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

data class ArtResource(val image: Int, val name: String, val desc: String)
@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier){
    var currentStep by remember { mutableStateOf(1) }
    var offsetX by remember { mutableStateOf(0f) }
    val resourcesStep =  when (currentStep) {
        1 -> ArtResource(R.drawable.cover1,
            "Alpha, Gems and Junk",
            "Del Northern (2019)")

        2 -> ArtResource(R.drawable.cover2,
            "Ambients and Instrumentals",
            "Allegra \"Del\" Northern  (2017)")

        3 -> ArtResource(R.drawable.cover3,
            "Downpour",
            "AnnoyingFlower (2023)")

        4 -> ArtResource(R.drawable.cover4,
            "The Watcher",
            "Del Northern (2025)")

        else -> ArtResource(R.drawable.cover1,
            "Alpha, Gems and Junk",
            "Del Northern (2019)")
    }
    Column(
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 15.dp, vertical = 5.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = offsetX.toString())
        ArtWindow(art = resourcesStep.image,
            DragWhile = { change, dragAmount ->
                change.consume()
                offsetX += dragAmount.x
            },
            DragStart= {offset ->
                offsetX = offset.x},
            DragEnd = {

            })

        Spacer(Modifier.height(12.dp))
        ArtText(name = resourcesStep.name, desc = resourcesStep.desc)
        Spacer(Modifier.height(5.dp))
        Buttons(onNextButtonClick = {
                if (currentStep==4){
                    currentStep = 1
                }
                else{
                    currentStep++
                }
            },
            onPrevButtonClick = {
                if (currentStep == 1){
                    currentStep = 4
                }
                else{
                    currentStep--
                }
            })
    }
}

@Composable
fun ArtWindow(@DrawableRes art: Int,
              DragStart: (Offset) -> Unit,
              DragWhile: (PointerInputChange, Offset) -> Unit,
              DragEnd: () -> Unit, modifier: Modifier = Modifier){

    Column(modifier = Modifier.size(250.dp)
    ) {
        Image(
            painter = painterResource(id = art),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .padding(start = 12.dp, end = 12.dp, top = 5.dp)
                .pointerInput(Unit){
                    detectDragGestures(
                        onDrag = DragWhile,
                        onDragStart = DragStart,
                        onDragEnd = DragEnd,
                        onDragCancel = { }
                    )
                }
                .dropShadow(
                    shape = RoundedCornerShape(0.dp),
                    shadow = Shadow(
                        radius = 15.dp,
                        color = Color(0xFF3b3b3b),
                        spread = 0.dp,
                        offset = DpOffset(10.dp, 10.dp)
                    )
            )
        )
    }

}

@Composable
fun ArtText(name: String, desc: String, modifier: Modifier = Modifier){
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp
            )
            Text(
                text = desc,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun Buttons(onPrevButtonClick: () -> Unit,
            onNextButtonClick: () -> Unit,
            modifier: Modifier = Modifier){
    Row(
        modifier = Modifier
            .padding(top = 10.dp,)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            onClick = onPrevButtonClick,
            shape = RoundedCornerShape(15.dp)
        ){
            Text(
                text = "Previous"
            )
        }
        OutlinedButton(
            onClick = onNextButtonClick,
            shape = RoundedCornerShape(15.dp)
        ){
            Text(
                text = "Next"
            )
        }
    }

}


@Preview(showBackground = true)
@Composable
fun ArtSpacePreview() {
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}