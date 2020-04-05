package ru.geekbrains.poplib.mvp.model.cache.image

import android.R.attr
import android.graphics.Bitmap
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubImage
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.file.IFileManager
import timber.log.Timber

class RoomImageCache(
    val database: Database,
    val fileManager: IFileManager
) : IImageCache {
    override fun cacheImage(url: String, bitmap: Bitmap) = Completable.create {
        val localPath = fileManager.saveBitmapIntoFile(
            bitmap,
            fileManager.getFileName(url)
        )

        localPath?.let {
            database.imageDao.insert(
                RoomGithubImage(
                    url,
                    it
                )
            )
        }
    }.subscribeOn(Schedulers.io())

    override fun LoadImage(url: String) = Single.create<ByteArray> { emiter ->
        Database.getInstance().imageDao.findByUrl(url)?.let {
            emiter.onSuccess(
                fileManager.loadBitmapIntoByteArray(it.localPath)
            )
        } ?: Timber.e("Can't find file")
    }.subscribeOn(Schedulers.io())
}