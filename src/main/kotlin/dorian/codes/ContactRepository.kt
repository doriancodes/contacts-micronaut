package dorian.codes

import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.r2dbc.annotation.R2dbcRepository
import io.micronaut.data.repository.reactive.ReactiveStreamsCrudRepository
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import javax.validation.constraints.NotNull

@R2dbcRepository(dialect = Dialect.POSTGRES)
interface ContactRepository: ReactiveStreamsCrudRepository<Contact, Long> {
    override fun findById(id: @NotNull Long): Mono<Contact>
    override fun findAll(): Flux<Contact>
    override fun <S : Contact?> save(entity: S): Mono<S>
    override fun <S : Contact?> update(entity: S): Mono<S>
    override fun delete(entity: Contact): Mono<Long>
}