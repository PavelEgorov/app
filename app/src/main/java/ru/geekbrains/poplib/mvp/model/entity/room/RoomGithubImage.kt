package ru.geekbrains.poplib.mvp.model.entity.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/// Я Предполагаю, что кеш картинок будет общим для всех картинок, по этому не привязываюсь к avatarUrl у RoomGithubUser
@Entity
data class RoomGithubImage (
    @PrimaryKey val url: String,
    val localPath: String
)