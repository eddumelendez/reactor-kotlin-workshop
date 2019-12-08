package io.eddumelendez.reactorkotlin.repository

import io.eddumelendez.reactorkotlin.domain.User
import reactor.kotlin.core.publisher.toMono

class BlockingUserRepository : BlockingRepository<User> {

    val reactiveRepository : ReactiveRepository<User>

    constructor() {
        reactiveRepository = ReactiveUserRepository()
    }

    constructor(delayInMs: Long) {
        reactiveRepository = ReactiveUserRepository(delayInMs)
    }

    constructor(vararg users: User) {
        reactiveRepository = ReactiveUserRepository(*users)
    }

    constructor(delayInMs: Long, vararg users: User) {
        reactiveRepository = ReactiveUserRepository(delayInMs, *users)
    }

    var callCount : Int = 0

    override fun save(value: User) {
        callCount++
        reactiveRepository.save(value.toMono()).block()
    }

    override fun findFirst(): User? {
        callCount++
        return reactiveRepository.findFirst().block()
    }

    override fun findAll(): Iterable<User> {
        callCount++
        return reactiveRepository.findAll().toIterable()
    }

    override fun findById(id: String): User? {
        callCount++
        return reactiveRepository.findById(id).block()
    }

}
