package ru.geekbrains.poplib.mvp.model.cache.user

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubUser

interface IUserCache {
    fun cacheUser(user: GithubUser) : Completable
    fun loadUser(userlogin: String) : Single<GithubUser>
}