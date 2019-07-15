package io.eddumelendez.reactorkotlin

import io.eddumelendez.reactorkotlin.domain.User
import io.eddumelendez.reactorkotlin.repository.ReactiveUserRepository
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import reactor.kotlin.core.publisher.toMono

class Part08OtherOperations {

    companion object {

        @JvmStatic
        val MARIE = User("mschrader", "Marie", "Schrader")

        @JvmStatic
        val MIKE = User("mehrmantraut", "Mike", "Ehrmantraut")

    }

    @Test
    fun zipFirstNameAndLastName() {
        val usernameFlux = Flux.just(User.SKYLER.username, User.JESSE.username, User.WALTER.username, User.SAUL.username)
        val firstnameFlux = Flux.just(User.SKYLER.firstname, User.JESSE.firstname, User.WALTER.firstname, User.SAUL.firstname)
        val lastnameFlux = Flux.just(User.SKYLER.lastname, User.JESSE.lastname, User.WALTER.lastname, User.SAUL.lastname)

        val userFlux = userFluxFromStringFlux(usernameFlux, firstnameFlux, lastnameFlux)
        userFlux.test()
                .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
                .verifyComplete()
    }

    // TODO Create a Flux of user from Flux of username, firstname and lastname.
    fun userFluxFromStringFlux(usernameFlux: Flux<String>, firstnameFlux: Flux<String?>, lastnameFlux: Flux<String?>): Flux<User> {
        return null!!
    }

    @Test
    fun fastestMono() {
        val repository1 = ReactiveUserRepository(MARIE)
        val repository2 = ReactiveUserRepository(250, MIKE)

        val mono = useFastestMono(repository1.findFirst(), repository2.findFirst())
        mono.test()
                .expectNext(MARIE)
                .expectComplete()
                .verify()
    }

    // TODO return the mono which returns faster its value
    fun useFastestMono(mono1: Mono<User>, mono2: Mono<User>): Mono<User> {
        return null!!
    }

    @Test
    fun fastestFlux() {
        var repository1 = ReactiveUserRepository(MARIE, MIKE)
        var repository2 = ReactiveUserRepository(250)
        var flux = useFastestFlux(repository1.findAll(), repository2.findAll())
        flux.test()
                .expectNext(MARIE, MIKE)
                .verifyComplete()

        repository1 = ReactiveUserRepository(250, MARIE, MIKE)
        repository2 = ReactiveUserRepository()
        flux = useFastestFlux(repository1.findAll(), repository2.findAll())
        flux.test()
                .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
                .verifyComplete()
    }

    // TODO return the flux which returns faster the first value
    fun useFastestFlux(flux1: Flux<User>, flux2: Flux<User>): Flux<User> {
        return null!!
    }

    @Test
    fun complete() {
        val repository = ReactiveUserRepository()
        val completion = fluxCompletion(repository.findAll())

        completion.test()
                .verifyComplete()
    }

    // TODO Convert the input Flux<User> to a Mono<Void> that represents the complete signal of the flux
    fun fluxCompletion(flux: Flux<User>): Mono<Void> {
        return null!!
    }

    @Test
    fun nullHandling() {
        var mono = nullAwareUserToMono(User.SKYLER)
        mono.test()
                .expectNext(User.SKYLER)
                .verifyComplete()

        mono = nullAwareUserToMono(null)
        mono.test()
                .verifyComplete()
    }

    // TODO Return a valid Mono of user for null input and non null input user (hint: Reactive Streams does not accept null values)
    fun nullAwareUserToMono(user: User?): Mono<User> {
        return null!!
    }

    @Test
    fun emptyHandling() {
        var mono = emptyToSkyler(User.WALTER.toMono())
        mono.test()
                .expectNext(User.WALTER)
                .verifyComplete()

        mono = emptyToSkyler(Mono.empty())
        mono.test()
                .expectNext(User.SKYLER)
                .verifyComplete()
    }

    // TODO Return the same mono passed as input parameter, expect that it will emit User.SKYLER when empty
    fun emptyToSkyler(mono: Mono<User>): Mono<User> {
        return null!!
    }
}
