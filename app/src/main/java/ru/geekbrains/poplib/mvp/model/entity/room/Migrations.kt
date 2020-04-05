package ru.geekbrains.poplib.mvp.model.entity.room

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE RoomGithubRepository ADD COLUMN language TEXT")
    }
}

/// На данный момент нет точного понимания в необходимости данного действия..
/// Возвможно рум сам создаст новую таблицу, а данным обновлением я вызову ошибку.
/// TODO: Нужно разобраться с этим моментом
val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE RoomGithubImage (url TEXT NOT NULL, localPath TEXT NOT NULL, PRIMARY KEY(url))")
    }
}