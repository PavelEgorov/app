package ru.geekbrains.poplib.mvp.model.cache.user

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.entity.GithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubUser
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import java.lang.RuntimeException

class RoomUserCache(val database: Database) : IUserCache {
    override fun cacheUser(user: GithubUser) = Completable.create {emmiter ->
        val roomUser = database.userDao.findByLogin(user.login)?.apply {
            avatarUrl = user.avatarUrl
            reposUrl = user.reposUrl
        } ?: RoomGithubUser(user.login, user.avatarUrl, user.reposUrl)
        database.userDao.insert(roomUser)

        emmiter.onComplete()
    }.subscribeOn(Schedulers.io())


    override fun loadUser(userlogin: String)= Single.create<GithubUser> {emitter ->
        database.userDao.findByLogin(userlogin)?.let { roomUser ->
            emitter.onSuccess(GithubUser(roomUser.login, roomUser.avatarUrl, roomUser.reposUrl))
        } ?: let {
            emitter.onError(RuntimeException("No such user in cache"))
        }
    }.subscribeOn(Schedulers.io())
}