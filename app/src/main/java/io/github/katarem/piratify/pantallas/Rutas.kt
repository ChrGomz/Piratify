package io.github.katarem.piratify.pantallas

sealed class Rutas(var ruta: String) {
    object PantallaReproductor: Rutas(ruta = "pantallareproductor")
    object PantallaPlaylists: Rutas(ruta = "pantallaplaylists")
    object PantallaAlbum: Rutas(ruta = "pantallaalbum")
    object PantallaAllCanciones: Rutas(ruta = "pantallaallcanciones")
    object PantallaLogin: Rutas(ruta = "pantallalogin")
}