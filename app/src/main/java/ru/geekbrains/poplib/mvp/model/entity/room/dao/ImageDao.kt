package ru.geekbrains.poplib.mvp.model.entity.room.dao

import androidx.room.*
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubImage

@Dao
interface ImageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: RoomGithubImage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg image: RoomGithubImage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(images: List<RoomGithubImage>)

    @Update
    fun update(image: RoomGithubImage)

    @Update
    fun update(vararg image: RoomGithubImage)

    @Update
    fun update(images: List<RoomGithubImage>)

    @Delete
    fun delete(image: RoomGithubImage)

    @Delete
    fun delete(vararg image: RoomGithubImage)

    @Delete
    fun delete(images: List<RoomGithubImage>)

    @Query("SELECT * FROM RoomGithubImage")
    fun getAll(): List<RoomGithubImage>

    @Query("SELECT * FROM RoomGithubImage WHERE url = :url LIMIT 1")
    fun findByUrl(url: String): RoomGithubImage?
}