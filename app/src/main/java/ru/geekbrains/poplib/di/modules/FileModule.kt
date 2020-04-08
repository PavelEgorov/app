package ru.geekbrains.poplib.di.modules

import dagger.Module
import dagger.Provides
import ru.geekbrains.poplib.mvp.model.file.IFileManager
import ru.geekbrains.poplib.ui.App
import ru.geekbrains.poplib.ui.file.FileManager
import javax.inject.Singleton

@Module
class FileModule {
    @Singleton
    @Provides
    fun fileManager(app: App): IFileManager {
        return FileManager(app.baseContext)
    }
}