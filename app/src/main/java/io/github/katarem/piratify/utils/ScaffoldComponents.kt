package io.github.katarem.piratify.utils

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import io.github.katarem.piratify.R
import io.github.katarem.piratify.model.ScaffoldViewModel
import io.github.katarem.piratify.pantallas.Rutas

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Header(goBack: () -> Unit){
    val model: ScaffoldViewModel = viewModel()
    val rutaActual = model.rutaActual.collectAsState()
    Row(
        Modifier
            .fillMaxWidth()
            .background(AppColors.verde)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        TextButton(onClick = { goBack() }) {
            Image(painter = painterResource(id = R.drawable.back_arrow), contentDescription = "")
        }
        if(rutaActual.value == Rutas.PantallaAllCanciones.ruta) BarraBusqueda()
        else Text(text = "Piratify", fontSize = 25.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun BarraBusqueda(){
    val model: ScaffoldViewModel = viewModel()
    val query = model.query.collectAsState()
    TextField(value = query.value, onValueChange = { model.changeQuery(it) }, shape = CircleShape,
        colors = TextFieldDefaults.colors(
            cursorColor = colorResource(id = R.color.black),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ), modifier = Modifier.height(40.dp), trailingIcon = {
            Icon(painter = painterResource(id = R.drawable.search), contentDescription = "busqueda")}
    )
}

@Composable
fun UIControls(gotoHome: () -> Unit, gotoAllCanciones: () -> Unit){
    Row(
        Modifier
            .background(Color(0xFF1db954))
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = { gotoHome() }){
            Image(painter = painterResource(id = R.drawable.home), contentDescription = "")
        }
        TextButton(onClick = { gotoAllCanciones() }){
            Image(painter = painterResource(id = R.drawable.search), contentDescription = "")
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun ScaffoldPreview(){
    var cont = 0
    Scaffold(
        topBar = { Header {} },
        content = { paddingValues ->  Text(text = "hola") })
}