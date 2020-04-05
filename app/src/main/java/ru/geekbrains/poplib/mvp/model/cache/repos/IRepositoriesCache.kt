package ru.geekbrains.poplib.mvp.model.cache.repos

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository

interface IRepositoriesCache {
    fun cacheRepository(userLogin: String, repository: List<GithubRepository>): Completable
    fun loadRepository(userLogin: String) : Single<List<GithubRepository>>
}