package io.github.katarem.piratify.entities

import io.github.katarem.piratify.R

object Playlists {

    val Metal = Album("Metal", R.drawable.doom_album, "katarem",2024, listOf(
        Canciones.PanicAttack,
        Canciones.RipAndTear,
        Canciones.Nightmare,
        Canciones.SaveMe,
        Canciones.TheRootOfAllEvil
    ))

    val Rap = Album("Rap",R.drawable.rap_anime,"katarem",2024, listOf(
        Canciones.Urbanologia,
        Canciones.PoesiaDeGuerra,
        Canciones.EsEpico,
        Canciones.ElArteSano,
        Canciones.Gratis,
        Canciones.MundoDePiedra,
        Canciones.ElViolador
    ))

    val playlists = listOf(Metal, Rap)

    fun forName(nombre: String): Album?{
        return Playlists.playlists.filter { album -> album.nombre.equals(nombre) }.firstOrNull()
    }

}