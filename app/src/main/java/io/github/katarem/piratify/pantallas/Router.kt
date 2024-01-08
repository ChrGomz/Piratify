package io.github.katarem.piratify.pantallas

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.katarem.piratify.Header
import io.github.katarem.piratify.R
import io.github.katarem.piratify.UIControls
import io.github.katarem.piratify.entities.Album
import io.github.katarem.piratify.entities.Albums
import io.github.katarem.piratify.entities.Cancion
import io.github.katarem.piratify.entities.Playlists
import io.github.katarem.piratify.model.ReproductorModel
import io.github.katarem.piratify.model.ScaffoldViewModel

@Composable
fun Router(){
    val navController = rememberNavController()

    val entradaNavActual by navController.currentBackStackEntryAsState()

    val rutaActual = entradaNavActual?.destination?.route

    val viewModelScaffold : ScaffoldViewModel = viewModel()
    Scaffold(topBar = { Header(goBack = { navController.popBackStack() })},
        bottomBar = { UIControls(gotoHome = { if(rutaActual != Rutas.PantallaPlaylists.ruta) navController.navigate(Rutas.PantallaPlaylists.ruta)},
            gotoAllCanciones = { if(rutaActual != Rutas.PantallaAllCanciones.ruta) navController.navigate(Rutas.PantallaAllCanciones.ruta) })
        },
        content = {
            paddingValues -> 
                Surface(modifier = Modifier.padding(paddingValues)) {
                    NavHost(navController = navController, startDestination = Rutas.PantallaPlaylists.ruta){
                        composable(Rutas.PantallaPlaylists.ruta){
                            PantallaPlaylists(viewModelScaffold,navController)
                        }
                        composable(Rutas.PantallaReproductor.ruta + "/{album}/{index}/{isShuffle}"){
                            val albumName = it.arguments!!.getString("album")
                            val index = it.arguments!!.getString("index")!!.toInt()
                            val isShuffle = it.arguments!!.getString("isShuffle")!!.toBoolean()
                            if(Albums.forName(albumName!!) == null) PantallaReproductor(Playlists.forName(albumName)!!,index,isShuffle)
                            else PantallaReproductor(Albums.forName(albumName)!!,index,isShuffle)
                        }
                        composable(Rutas.PantallaAlbum.ruta + "/{album}"){
                            val albumName = it.arguments!!.getString("album")
                            if(Albums.forName(albumName!!) == null)
                                PantallaAlbum(album = Playlists.forName(albumName)!!,navController)
                            else PantallaAlbum(album = Albums.forName(albumName)!!, navController = navController)
                        }
                        composable(Rutas.PantallaAllCanciones.ruta){
                            PantallaAllCanciones(navController = navController)
                        }
                    }
                }
            
        }
    )


}
