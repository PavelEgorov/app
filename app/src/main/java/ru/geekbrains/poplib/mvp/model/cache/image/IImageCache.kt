package ru.geekbrains.poplib.mvp.model.cache.image

import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.net.URL

interface IImageCache {
    fun cacheImage(url: String, bitmap: Bitmap) : Completable
    fun LoadImage(url: String): Single<ByteArray>
}