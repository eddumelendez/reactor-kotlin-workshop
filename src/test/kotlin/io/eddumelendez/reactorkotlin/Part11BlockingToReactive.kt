package io.eddumelendez.reactorkotlin

import io.eddumelendez.reactorkotlin.domain.User
import io.eddumelendez.reactorkotlin.repository.BlockingRepository
import io.eddumelendez.reactorkotlin.repository.BlockingUserRepository
import io.eddumelendez.reactorkotlin.repository.ReactiveUserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.test.test

class Part11BlockingToReactive {

    @Test
    fun slowPublisherFastSubscriber() {
        val repository = BlockingUserRepository()
        val flux = blockingRepositoryToFlux(repository)
        assertEquals("The call to findAll must be deferred until the flux is subscribed", 0, repository.callCount)
        flux.test()
                .expectNext(User.SKYLER, User.JESSE, User.WALTER, User.SAUL)
                .verifyComplete()
    }

    // TODO Create a Flux for reading all users from the blocking repository deferred until the flux is subscribed, and run it with an elastic scheduler
    fun blockingRepositoryToFlux(repository: BlockingRepository<User>): Flux<User> {
        return null!!
    }

    @Test
    fun fastPublisherSlowSubscriber() {
        val reactiveRepository = ReactiveUserRepository()
        val blockingRepository = BlockingUserRepository(*arrayOf<User>())
        val complete = fluxToBlockingRepository(reactiveRepository.findAll(), blockingRepository)
        assertEquals(0, blockingRepository.callCount)
        complete.test()
                .verifyComplete()
        val it = blockingRepository.findAll().iterator()
        assertEquals(User.SKYLER, it.next())
        assertEquals(User.JESSE, it.next())
        assertEquals(User.WALTER, it.next())
        assertEquals(User.SAUL, it.next())
        assertFalse(it.hasNext())
    }

    // TODO Insert users contained in the Flux parameter in the blocking repository using an elastic scheduler and return a Mono<Void> that signal the end of the operation
    fun fluxToBlockingRepository(flux: Flux<User>, repository: BlockingRepository<User>): Mono<Void> {
        return null!!
    }

}
