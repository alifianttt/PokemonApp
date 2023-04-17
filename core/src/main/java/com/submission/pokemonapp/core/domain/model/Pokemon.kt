package com.submission.pokemonapp.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pokemon(
    var id: Int,
    var name: String,
    var url: String,
    var isSave: Boolean
): Parcelable
