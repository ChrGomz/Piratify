package io.github.katarem.piratify.entities

import io.github.katarem.piratify.R

object Albums{

    val Octavarium = Album("Octavarium", R.drawable.octavarium,"Dream Theater",2005, listOf(
        Canciones.TheRootOfAllEvil,
        Canciones.TheAnswerLiesWithin,
        Canciones.TheseWalls,
        Canciones.IWalkBesideYou,
        Canciones.PanicAttack,
        Canciones.NeverEnough,
        Canciones.SacrifiedSons,
        Canciones.Octavarium
    ))
    val Muerte = Album("Muerte",R.drawable.muerte_canserbero,"Canserbero",2012, listOf(
        Canciones.CestLaMort,
        Canciones.EsEpico,
        Canciones.SerVero,
        Canciones.EnElValleDeLasSombras,
        Canciones.Maquiavelico,
        Canciones.MundoDePiedra,
        Canciones.SinMercy,
        Canciones.UnDiaEnElBarrio,
        Canciones.Llovia,
        Canciones.YEnUnEspejoVi,
        Canciones.LaHoraDelJuicio,
        Canciones.ElPrimerTrago,
        Canciones.DeMiMuerte,
        Canciones.Jeremias17
    ))

    val Nightmare = Album("Nightmare",R.drawable.nightmare,"Avenged Sevenfold",2010, listOf(
        Canciones.Nightmare,
        Canciones.WelcomeToTheFamily,
        Canciones.DangerLine,
        Canciones.BuriedAlive,
        Canciones.NaturalBornKiller,
        Canciones.SoFarAway,
        Canciones.GodHatesUs,
        Canciones.Victim,
        Canciones.TonightTheWorldDies,
        Canciones.Fiction,
        Canciones.SaveMe
    ))

    val TodasLasCanciones = Album("allsongs",R.drawable.maw,"Todos los artistas",1970,
        listOf(
            Canciones.TheRootOfAllEvil,
            Canciones.TheAnswerLiesWithin,
            Canciones.TheseWalls,
            Canciones.IWalkBesideYou,
            Canciones.PanicAttack,
            Canciones.NeverEnough,
            Canciones.SacrifiedSons,
            Canciones.Octavarium,
            Canciones.CestLaMort,
            Canciones.EsEpico,
            Canciones.SerVero,
            Canciones.EnElValleDeLasSombras,
            Canciones.Maquiavelico,
            Canciones.MundoDePiedra,
            Canciones.SinMercy,
            Canciones.UnDiaEnElBarrio,
            Canciones.Llovia,
            Canciones.YEnUnEspejoVi,
            Canciones.LaHoraDelJuicio,
            Canciones.ElPrimerTrago,
            Canciones.DeMiMuerte,
            Canciones.Jeremias17,
            Canciones.Nightmare,
            Canciones.WelcomeToTheFamily,
            Canciones.DangerLine,
            Canciones.BuriedAlive,
            Canciones.NaturalBornKiller,
            Canciones.SoFarAway,
            Canciones.GodHatesUs,
            Canciones.Victim,
            Canciones.TonightTheWorldDies,
            Canciones.Fiction,
            Canciones.SaveMe,
            Canciones.Maw,
            Canciones.ElViolador,
            Canciones.ElArteSano
        ).sortedBy { cancion -> cancion.nombre }
    )

    val albums = listOf(Octavarium, Muerte, Nightmare)

    fun forName(nombre: String): Album?{
        return albums.filter { album -> album.nombre.equals(nombre) }.firstOrNull()
    }

}