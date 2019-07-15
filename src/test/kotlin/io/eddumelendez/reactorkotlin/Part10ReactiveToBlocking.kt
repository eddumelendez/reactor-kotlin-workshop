package io.eddumelendez.reactorkotlin

import io.eddumelendez.reactorkotlin.domain.User
import io.eddumelendez.reactorkotlin.repository.ReactiveUserRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class Part10ReactiveToBlocking {

    val repository = ReactiveUserRepository()

    @Test
    fun mono() {
        val mono = repository.findFirst()
        val user = monoToValue(mono)

        assertEquals(User.SKYLER, user)
    }

    // TODO Return the user contained in that Mono
    fun monoToValue(mono: Mono<User>): User? {
        return null!!
    }

    @Test
    fun flux() {
        val flux = repository.findAll()
        val users = fluxToValues(flux)
        val it = users.iterator()
        assertEquals(User.SKYLER, it.next())
        assertEquals(User.JESSE, it.next())
        assertEquals(User.WALTER, it.next())
        assertEquals(User.SAUL, it.next())
        assertFalse(it.hasNext())
    }

    // TODO Return the users contained in that Flux
    fun fluxToValues(flux: Flux<User>): Iterable<User> {
        return null!!
    }

}
