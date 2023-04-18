package com.submission.pokemonapp.core.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Toast
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

fun View.setVisible(visible: Boolean){
    visibility = if (visible){
        View.VISIBLE
    } else {
        View.GONE
    }
}


fun Activity.toasShort(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun extractColorFromUrl(context: Context, imageUrl: String, callback: (Int?) -> Unit) {
    Glide.with(context)
        .asBitmap()
        .load(imageUrl)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                // Create a new bitmap that's 1x1 pixel
                try {
                    Palette.from(resource).generate {pallete ->
                        val dominantColor = pallete?.dominantSwatch?.rgb ?: Color.TRANSPARENT
                        callback(dominantColor)
                    }
                } catch (e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                // Do nothing
            }
        })
}


fun capitalizeFirstLetter(word: String): String = word.substring(0, 1).uppercase() + word.substring(1)