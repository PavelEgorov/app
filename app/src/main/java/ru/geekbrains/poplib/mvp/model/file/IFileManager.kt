package ru.geekbrains.poplib.mvp.model.file

import android.graphics.Bitmap

interface IFileManager {
    fun getFileName(url: String): String
    fun saveBitmapIntoFile(bitmap: Bitmap, fileName: String): String
    fun loadBitmapIntoByteArray(localPath: String): ByteArray
}