package com.mwema.afyapoa.utils

import android.content.Context
import android.net.Uri
import java.io.File

fun Uri.toFile(context: Context): File {

    // 1️⃣ Ask Android for the image data
    val inputStream =
        context.contentResolver.openInputStream(this)
            ?: throw Exception("Cannot open input stream")

    // 2️⃣ Create temporary file inside app cache
    val file = File.createTempFile(
        "profile_image",
        ".jpg",
        context.cacheDir
    )

    // 3️⃣ Copy image bytes into our file
    file.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }

    // 4️⃣ Return usable file
    return file
}