package ru.geekbrains.poplib.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.cache.user.IUserCache
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.network.NetworkStatus
import java.lang.RuntimeException

//TODO: Практическое задание 1 - вытащить кэширование в отдельный класс RoomUserCache и внедрить его сюда через интфейс IUserCache
class GithubUsersRepo(
    val api: IDataSource,
    val networkStatus: NetworkStatus,
    val roomUser: IUserCache
) {
    fun getUser(username: String) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUser(username)
                .map { user ->
                    roomUser.cacheUser(user).subscribe()
                    user
                }
        } else {
            roomUser.loadUser(username)
        }
    }.subscribeOn(Schedulers.io())
}
