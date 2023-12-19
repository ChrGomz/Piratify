package io.github.katarem.piratify.pantallas

sealed class Rutas(var ruta: String) {
    object PantallaPrincipal: Rutas(ruta = "pantallaprincipal")
}