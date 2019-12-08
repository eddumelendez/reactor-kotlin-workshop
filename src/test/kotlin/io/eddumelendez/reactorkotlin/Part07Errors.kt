package io.eddumelendez.reactorkotlin

import io.eddumelendez.reactorkotlin.domain.User
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.kotlin.test.test

class Part07Errors {

    @Test
    fun monoWithValueInsteadOfError() {
        var mono = betterCallSaulForBogusMono(IllegalStateException().toMono())
        mono.test()
                .expectNext(User.SAUL)
                .verifyComplete()

        mono = betterCallSaulForBogusMono(User.SKYLER.toMono())
        mono.test()
                .expectNext(User.SKYLER)
                .verifyComplete()

    }

    // TODO Return a Mono<User> containing User.SAUL when an error occurs in the input Mono, else do not change the input Mono.
    fun betterCallSaulForBogusMono(mono: Mono<User>): Mono<User> {
        return null!!
    }

    @Test
    fun fluxWithValueInsteadOfError() {
        var flux = betterCallSaulAndJesseForBogusFlux(IllegalStateException().toFlux())
        flux.test()
                .expectNext(User.SAUL, User.JESSE)
                .verifyComplete()

        flux = betterCallSaulAndJesseForBogusFlux(Flux.just(User.SKYLER, User.WALTER))
        flux.test()
                .expectNext(User.SKYLER, User.WALTER)
                .verifyComplete()
    }

    // TODO Return a Flux<User> containing User.SAUL and User.JESSE when an error occurs in the input Flux, else do not change the input Flux.
    fun betterCallSaulAndJesseForBogusFlux(flux: Flux<User>): Flux<User> {
        return null!!
    }

    @Test
    fun handleCheckedExceptions() {
        val flux = capitalizeMany(Flux.just(User.SAUL, User.JESSE))

        flux.test()
                .verifyError(GetOutOfHereException::class.java)
    }

    // TODO Implement a method that capitalize each user of the incoming flux using the capitalizeUser() method and emit an error containing a GetOutOfHereException exception
    fun capitalizeMany(flux: Flux<User>): Flux<User> {
        return null!!
    }

    @Throws(GetOutOfHereException::class)
    fun capitalizeUser(user: User): User {
        if (user == User.SAUL) {
            throw GetOutOfHereException()
        }
        return User(user.username, user.firstname, user.lastname)
    }

    class GetOutOfHereException : Exception()

}
