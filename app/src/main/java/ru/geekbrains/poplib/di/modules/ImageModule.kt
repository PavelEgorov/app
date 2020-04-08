package ru.geekbrains.poplib.di.modules

import dagger.Module
import dagger.Provides
import ru.geekbrains.poplib.mvp.model.cache.image.IImageCache
import ru.geekbrains.poplib.mvp.model.cache.image.RoomImageCache
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.file.IFileManager
import ru.geekbrains.poplib.ui.image.GlideImageLoader

//Реализовать модуль и внедрение всего, что касается картинок. Кэш тоже сюда.
@Module(
    includes = [
        CacheModule::class,
        FileModule::class
    ]
)
class ImageModule {

    @Provides
    fun imageCache(database: Database, fileManager: IFileManager): IImageCache{
        return RoomImageCache(database, fileManager)
    }

    /// TODO: почему-то данный вариант не работает.. Предполагаю, что проблема в дженерике. Нужно разобраться
    /// точнее работает, но не так как хотелось бы.. Ошибки нет. Но картинку не подтягивает
    /// а ещё нужно его в отдельный модуль вытащить
//    @Provides
//    fun imageLoader(imageCache: IImageCache):GlideImageLoader{
//        return GlideImageLoader(imageCache)
//    }
}