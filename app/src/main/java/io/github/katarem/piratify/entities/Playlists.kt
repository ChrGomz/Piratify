package io.github.katarem.piratify.entities

import io.github.katarem.piratify.R

object Playlists {

    val Metal = Album("Metal", R.drawable.doom_album, "yo mismo",2024, listOf(
        Canciones.PanicAttack,
        Canciones.RipAndTear,
        Canciones.ElViolador
    ))

    val playlists = listOf(Metal)

    fun forName(nombre: String): Album?{
        return Playlists.playlists.filter { album -> album.nombre.equals(nombre) }.firstOrNull()
    }

}