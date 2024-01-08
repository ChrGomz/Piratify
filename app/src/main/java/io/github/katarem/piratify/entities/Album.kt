package io.github.katarem.piratify.entities

data class Album(val nombre: String, val caratula: Int, val artista: String, val anio: Int, val canciones: List<Cancion>)