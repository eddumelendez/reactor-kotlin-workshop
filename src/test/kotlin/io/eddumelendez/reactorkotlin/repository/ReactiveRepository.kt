package io.eddumelendez.reactorkotlin.repository

import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface ReactiveRepository<T> {

    fun save(publisher: Publisher<T>): Mono<Void>

    fun findFirst(): Mono<T>

    fun findAll(): Flux<T>

    fun findById(id: String): Mono<T>

}
