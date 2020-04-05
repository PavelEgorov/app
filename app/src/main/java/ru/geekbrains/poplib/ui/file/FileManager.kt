package ru.geekbrains.poplib.ui.file

import android.R.attr.bitmap
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import ru.geekbrains.poplib.mvp.model.file.IFileManager
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class FileManager(val context: Context?): IFileManager {
    override fun getFileName(url: String) :String{
        return url.substringAfterLast("/").substringBeforeLast(".")
    }

    override fun saveBitmapIntoFile(bitmap: Bitmap, fileName: String): String {
        context?.let {
            val dst = File(context.getExternalFilesDir(null), "${fileName}.jpeg")
            val stream = FileOutputStream(dst)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.close()

            return dst.absolutePath
        } ?: return ""
    }

    override fun loadBitmapIntoByteArray(localPath: String): ByteArray {
        return File(localPath).readBytes()
    }
}