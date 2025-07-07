package edu.vt.mobiledev.pharmascan.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

object ImageUtils {

    fun generateHash(bitmap: Bitmap): String {
        return bitmap.byteCount.toString() // simple hash for demo
    }

    fun getBitmapFromFile(file: File): Bitmap? {
        return BitmapFactory.decodeFile(file.absolutePath)
    }
}
