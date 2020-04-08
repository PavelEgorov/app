package ru.geekbrains.poplib.ui.image

import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import ru.geekbrains.poplib.mvp.model.cache.image.IImageCache
import ru.geekbrains.poplib.mvp.model.image.IImageLoader
import timber.log.Timber
import javax.inject.Inject

class GlideImageLoader : IImageLoader<ImageView> {
    @Inject lateinit var cacheImage: IImageCache

    override fun loadInto(url: String, container: ImageView) {
        //Проверять наличие сети

        Glide.with(container.context)
            .asBitmap()
            .load(url) //При отсутствии сети грузить с диска через ByteArray
            .listener(object : RequestListener<Bitmap> {

                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Bitmap>,
                    isFirstResource: Boolean
                ): Boolean {
                   cacheImage.LoadImage(url).subscribe({
                        Glide.with(container.context)
                            .asBitmap()
                            .load(it)
                            .into(target);
                    }, {
                        Timber.e(it)
                    })
                    return true
                }

                override fun onResourceReady(
                    resource: Bitmap?,
                    model: Any,
                    target: Target<Bitmap>,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    resource?.let {
                        cacheImage.cacheImage(url, it).subscribe()
                    }

                    return false
                }
            })
            .into(container)
    }
}