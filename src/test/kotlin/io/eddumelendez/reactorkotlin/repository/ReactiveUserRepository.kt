package io.eddumelendez.reactorkotlin.repository

import io.eddumelendez.reactorkotlin.domain.User
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import java.time.Duration
import java.util.function.BiFunction

class ReactiveUserRepository : ReactiveRepository<User> {

    companion object {

        @JvmStatic
        private val DEFAULT_DELAY_IN_MS = 100L

    }

    var delayInMs : Long = 0

    var users: MutableList<User> = arrayListOf()

    constructor(): this(DEFAULT_DELAY_IN_MS)

    constructor(delayInMs: Long) {
        this.delayInMs = delayInMs
        this.users = mutableListOf(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
    }

    constructor(vararg users: User): this(DEFAULT_DELAY_IN_MS, *users)

    constructor(delayInMs: Long, vararg users: User) {
        this.delayInMs = delayInMs
        this.users = users.toMutableList()
    }

    override fun save(publisher: Publisher<User>): Mono<Void> {
        return withDelay(Flux.from(publisher)).doOnNext({ u -> this.users.add(u) }).then()
    }

    override fun findFirst(): Mono<User> {
        return withDelay(users.get(0).toMono())
    }

    override fun findAll(): Flux<User> {
        return withDelay(users.toFlux())
    }

    override fun findById(id: String): Mono<User> {
        val user = users.stream()
                .filter({ p -> p.username == id})
                .findFirst()
                .orElseThrow({ IllegalArgumentException("No user with username $id found!")})
        return withDelay(user.toMono())
    }

    fun withDelay(userMono: Mono<User>) : Mono<User> {
        return Mono.delay(Duration.ofMillis(delayInMs)).flatMap({ userMono })
    }

    fun withDelay(userFlux: Flux<User>): Flux<User> {
        return Flux.interval(Duration.ofMillis(delayInMs)).zipWith(userFlux, BiFunction { _, user -> user })
    }

}
