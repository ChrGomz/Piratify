package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.katarem.piratify.R

@Composable
fun PantallaPrincipal(){
    Column(
        Modifier.fillMaxSize()
    ){
        Header()
        SongInfoText()
        Image(painter = painterResource(id = R.drawable.doom_album),
            contentDescription = "doom",
            Modifier
                .fillMaxWidth()
                .padding(10.dp),
            contentScale = ContentScale.Crop,)
        PlayerControls()
    }
}

@Composable
fun SongInfoText(){
    Column (
        Modifier
            .fillMaxWidth()
            .padding(10.dp)){
        Text(text = "Now playing", fontSize = 20.sp)
        Text(text = "Rip & Tear", fontSize = 20.sp)
    }
}



@Composable
fun Header(){
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color(R.color.spotify_green))
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextButton(onClick = { /*TODO*/ }) {
            Image(painter = painterResource(id = R.drawable.back_arrow), contentDescription = "")
        }
        Text(text = "Piratify", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun PlayerControls(){
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            SliderView()
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { /*TODO*/ }){
                    Image(painter = painterResource(id = R.drawable.shuffle), contentDescription = "")
                }
                TextButton(onClick = { /*TODO*/ }){
                    Image(painter = painterResource(id = R.drawable.prev), contentDescription = "")
                }
                TextButton(onClick = { /*TODO*/ }){
                    Image(painter = painterResource(id = R.drawable.play), contentDescription = "")
                }
                TextButton(onClick = { /*TODO*/ }){
                    Image(painter = painterResource(id = R.drawable.next), contentDescription = "")
                }
                TextButton(onClick = { /*TODO*/ }){
                    Image(painter = painterResource(id = R.drawable.baseline_repeat_24), contentDescription = "")
                }
            }

        }
        UIControls()
    }
}

@Composable
fun SliderView(){
    Column(
        Modifier.padding(10.dp).fillMaxWidth()
    ) {
        Slider(value = 0f, onValueChange = {})
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "00:00")
            Text(text = "03:40")
        }
    }
}



@Composable
fun UIControls(){
    Row(
        Modifier
            .background(Color(R.color.spotify_green))
            .height(80.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = { /*TODO*/ }){
            Image(painter = painterResource(id = R.drawable.home), contentDescription = "")
        }
        Button(onClick = { /*TODO*/ }){
            Image(painter = painterResource(id = R.drawable.search), contentDescription = "")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPrincipalPreview(){
    PantallaPrincipal()
}