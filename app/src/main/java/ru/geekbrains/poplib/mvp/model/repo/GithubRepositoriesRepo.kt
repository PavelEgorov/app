package ru.geekbrains.poplib.mvp.model.repo

import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.api.IDataSource
import ru.geekbrains.poplib.mvp.model.cache.repos.IRepositoriesCache
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import ru.geekbrains.poplib.mvp.model.network.NetworkStatus
import java.lang.RuntimeException

//TODO: Практическое задание 1 - вытащить кэширование в отдельный класс RoomRepositoriesCache и внедрить его сюда через интфейс IRepositoriesCache
class GithubRepositoriesRepo(
    val api: IDataSource,
    val networkStatus: NetworkStatus,
    val roomRepos: IRepositoriesCache
) {
    fun getUserRepositories(user: GithubUser) = networkStatus.isOnlineSingle().flatMap { isOnline ->
        if (isOnline) {
            api.getUserRepos(user.reposUrl).map { repos ->
                roomRepos.cacheRepository(user.login, repos).subscribe()
                repos
            }
        } else {
            roomRepos.loadRepository(user.login)
        }
    }.subscribeOn(Schedulers.io())
}