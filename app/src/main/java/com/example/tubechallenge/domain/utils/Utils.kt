package com.example.tubechallenge.domain.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.tubechallenge.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Utils {

    fun formatElapsedTime(elapsedMillis: Long, withMillis: Boolean): String {
        val milliSeconds = (elapsedMillis % 1000) / 10  // Ensures two-digit millis
        val seconds = (elapsedMillis / 1000) % 60
        val minutes = (elapsedMillis / (1000 * 60)) % 60
        val hours = (elapsedMillis / (1000 * 60 * 60)) % 24
        return when (withMillis) {
            true -> String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliSeconds)
            false -> String.format("%02d:%02d:%02d", hours, minutes, seconds)

        }
    }

    fun createImageFile(context: Context): Uri? {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "JPEG_${timeStamp}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera") // Saves to DCIM/Camera
        }

        val resolver = context.contentResolver
        return resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
    }

    @Composable
    fun TrailingIcon(icon: Int, onClick: () -> Unit) {
        Icon(
            painterResource(icon),
            "Enter time now",
            modifier = Modifier.clickable { onClick() }
        )
    }
}