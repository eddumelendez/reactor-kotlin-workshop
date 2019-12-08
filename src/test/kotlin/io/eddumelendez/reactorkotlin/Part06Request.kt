package io.eddumelendez.reactorkotlin

import io.eddumelendez.reactorkotlin.domain.User
import io.eddumelendez.reactorkotlin.repository.ReactiveUserRepository
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier
import reactor.kotlin.test.test

class Part06Request {

    val repository = ReactiveUserRepository()

    @Test
    fun requestAll() {
        val flux = repository.findAll()

        val verifier = requestAllExpectFour(flux)
        verifier.verify()
    }

    // TODO Create a StepVerifier that requests initially all values and expect a 4 values to be received
    fun requestAllExpectFour(flux: Flux<User>): StepVerifier {
        return null!!
    }

    @Test
    fun requestOneByOne() {
        val flux = repository.findAll()

        val verifier = requestOneExpectSkylerThenRequestOneExpectJesse(flux)
        verifier.verify()
    }

    // TODO Create a StepVerifier that requests initially 1 value and expects {@link User.SKYLER} then requests another value and expects {@link User.JESSE}.
    fun requestOneExpectSkylerThenRequestOneExpectJesse(flux: Flux<User>): StepVerifier {
        return null!!
    }

    @Test
    fun experimentWithLog() {
        val flux = fluxWithLog()

        flux.test(0)
                .thenRequest(1)
                .expectNextMatches({ true })
                .thenRequest(1)
                .expectNextMatches({ true })
                .thenRequest(2)
                .expectNextMatches({ true })
                .expectNextMatches({ true })
                .verifyComplete()
    }

    // TODO Return a Flux with all users stored in the repository that prints automatically logs for all Reactive Streams signals
    fun fluxWithLog(): Flux<User> {
        return null!!
    }

    @Test
    fun experimentWithDoOn() {
        val flux = fluxWithDoOnPrintln()

        flux.test()
                .expectNextCount(4)
                .verifyComplete()
    }

    // TODO Return a Flux with all users stored in the repository that prints "Starring:" on subscribe, "firstname lastname" for all values and "The end!" on complete
    fun fluxWithDoOnPrintln(): Flux<User> {
        return null!!
    }

}
