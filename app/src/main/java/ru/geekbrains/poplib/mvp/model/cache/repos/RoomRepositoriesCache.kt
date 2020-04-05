package ru.geekbrains.poplib.mvp.model.cache.repos

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import ru.geekbrains.poplib.mvp.model.entity.GithubRepository
import ru.geekbrains.poplib.mvp.model.entity.room.RoomGithubRepository
import ru.geekbrains.poplib.mvp.model.entity.room.db.Database
import java.lang.RuntimeException

///Для базы данных напрашивается тоже интерфейс.. Или Room сам решает данную проблему? (нужно почитать/спросить)
//TODO: Нужно разобраться. Хотя Database это абстрактный класс, по этому он может иметь различные реализации.
class RoomRepositoriesCache(val database: Database) : IRepositoriesCache {
    override fun cacheRepository(userLogin: String, repository: List<GithubRepository>) = Completable.create {
        repository.takeIf {
            it.isNotEmpty()
        }?.let {
            val roomRepos = repository.map {
                RoomGithubRepository(
                    it.id,
                    it.name,
                    it.forksCount,
                    userLogin,
                    it.language
                )
            }
            database.repositoryDao.insert(roomRepos)
        }
    }.subscribeOn(Schedulers.io())
    override fun loadRepository(userLogin: String)= Single.create<List<GithubRepository>> { emitter ->
            database.userDao.findByLogin(userLogin)?.let {
                val roomRepos = database.repositoryDao.findForUser(userLogin)
                val repos = roomRepos.map {
                    GithubRepository(
                        it.id,
                        it.name,
                        it.forksCount,
                        it.language ?: ""
                    )
                }
                emitter.onSuccess(repos)
            } ?: let {
                emitter.onError(RuntimeException("No such user in cache"))
            }
        }
}