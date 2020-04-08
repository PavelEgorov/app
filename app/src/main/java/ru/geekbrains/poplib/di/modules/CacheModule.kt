package ru.geekbrains.poplib.di.modules

import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.geekbrains.poplib.mvp.model.cache.image.IImageCache
import ru.geekbrains.poplib.mvp.model.cache.image.RoomImageCache
import ru.geekbrains.poplib.mvp.model.cache.repos.IRepositoriesCache
import ru.geekbrains.poplib.mvp.model.cache.user.IUserCache
import ru.geekbrains.poplib.mvp.model.cache.repos.RoomRepositoriesCache
import ru.geekbrains.poplib.mvp.model.cache.user.RoomUserCache
import ru.geekbrains.poplib.mvp.model.entity.room.MIGRATION_1_2
import ru.geekbrains.poplib.mvp.model.entity.room.MIGRATION_2_3
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.file.IFileManager
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.file.FileManager
import javax.inject.Singleton

@Module
class CacheModule {

    @Singleton
    @Provides
    fun database(app: App): Database {
        return Room.databaseBuilder(app, Database::class.java, Database.DB_NAME)
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .build()
    }

    @Provides
    fun usersCache(database: Database): IUserCache {
        return RoomUserCache(database)
    }

    @Provides
    fun repositoriesCache(database: Database): IRepositoriesCache {
        return RoomRepositoriesCache(database)
    }
}