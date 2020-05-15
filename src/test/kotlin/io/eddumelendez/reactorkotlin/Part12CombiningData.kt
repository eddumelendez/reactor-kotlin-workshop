package io.eddumelendez.reactorkotlin

import io.eddumelendez.reactorkotlin.domain.User
import io.eddumelendez.reactorkotlin.repository.ReactiveUserRepository
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import reactor.core.publisher.Mono
import reactor.kotlin.test.test
import java.time.Duration

class Part12CombiningData {

    private lateinit var user1: Mono<User>
    private lateinit var user2: Mono<User>
    private lateinit var empty: Mono<User>

    companion object {
        @JvmStatic
        val SINGLE_DELAY = 500L
    }

    @Before
    fun prepareRepositories() {
        user1 = ReactiveUserRepository(SINGLE_DELAY).findById(User.SKYLER.username)
        user2 = ReactiveUserRepository(SINGLE_DELAY).findById(User.JESSE.username)
        empty = Mono.empty<User>().delayElement(Duration.ofMillis(SINGLE_DELAY))
    }

    @Test
    fun shouldCombineMonoData() {
        val mono = combineMonoData(user1, user2)
        mono.test()
                .expectNext(User(
                        User.SKYLER.username + User.JESSE.username,
                        User.SKYLER.firstname,
                        User.JESSE.lastname
                ))
                .expectComplete()
                .verify()
    }

    // TODO return combined user: new username should be a concatenation of two usernames,
    // firstname should be taken from first user, and lastname from second user
    fun combineMonoData(mono1: Mono<User>, mono2: Mono<User>): Mono<User> {
        return null!!
    }

    @Test
    fun shouldCombineMonoDataWithSecondEmpty() {

        val mono = combineMonoDataWithSecondEmpty(user1, user2)
        val duration = mono.test()
                .expectNext(User(
                        User.SKYLER.username + User.JESSE.username,
                        User.SKYLER.firstname,
                        User.JESSE.lastname
                ))
                .expectComplete()
                .verify()
                .toMillis()

        val mono1 = combineMonoDataWithSecondEmpty(user1, empty)
        val duration1 = mono1.test()
                .expectNext(User(
                        User.SKYLER.username,
                        User.SKYLER.firstname,
                        null
                ))
                .expectComplete()
                .verify()
                .toMillis()
        Assert.assertTrue("First case should be fast", duration < SINGLE_DELAY * 2)
        Assert.assertTrue("Second case should be fast", duration1 < SINGLE_DELAY * 2)
    }

    // TODO same as above, but second provider could be empty
    fun combineMonoDataWithSecondEmpty(mono1: Mono<User>, mono2: Mono<User>): Mono<User> {
        return null!!
    }


    @Test
    fun shouldCombineMonoDataWithAnyEmpty() {

        val mono = combineMonoDataWithAnyEmpty(user1, user2)
        val duration = mono.test()
                .expectNext(User(
                        User.SKYLER.username + User.JESSE.username,
                        User.SKYLER.firstname,
                        User.JESSE.lastname
                ))
                .expectComplete()
                .verify()
                .toMillis()

        val mono1 = combineMonoDataWithAnyEmpty(user1, empty)
        val duration1 = mono1.test()
                .expectNext(User(
                        User.SKYLER.username,
                        User.SKYLER.firstname,
                        null
                ))
                .expectComplete()
                .verify()
                .toMillis()

        val mono2 = combineMonoDataWithAnyEmpty(empty, user2)
        val duration2 = mono2.test()
                .expectNext(User(
                        User.JESSE.username,
                        null,
                        User.JESSE.lastname
                ))
                .expectComplete()
                .verify()
                .toMillis()

        val mono3 = combineMonoDataWithAnyEmpty(empty, empty)
        val duration3 = mono3.test()
                .expectComplete()
                .verify()
                .toMillis()

        Assert.assertTrue("First case should be fast", duration < SINGLE_DELAY * 2)
        Assert.assertTrue("Second case should be fast", duration1 < SINGLE_DELAY * 2)
        Assert.assertTrue("Third case should be fast", duration2 < SINGLE_DELAY * 2)
        Assert.assertTrue("Fourth case should be fast", duration3 < SINGLE_DELAY * 2)
    }

    // TODO same as above, but either of the providers could be empty
    fun combineMonoDataWithAnyEmpty(mono1: Mono<User>, mono2: Mono<User>): Mono<User> {
        return null!!
    }
}
