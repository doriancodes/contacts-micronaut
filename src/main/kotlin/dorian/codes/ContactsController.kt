package dorian.codes

import io.micronaut.http.HttpResponse
import io.micronaut.http.MutableHttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.validation.Validated
import jakarta.inject.Singleton
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Singleton
@Controller("/contacts")
@Validated
class ContactsController(val contactRepository: ContactRepository) {

    /**
    @Get("/hello")
    fun greeting(): Mono<HttpResponse<*>> {
        return Mono.just(HttpResponse.ok("Hello"))
    }**/

    @Get("/{id}")
    fun getContact(@PathVariable id: Long): Mono<APIContact> {
        val contact = contactRepository.findById(id)
        return contact.map { it.toAPIContact() }
    }

    @Get()
    fun getAllContacts(): Flux<APIContact> {
        val contacts = contactRepository.findAll()
        return contacts.map { it.toAPIContact() }
    }

    @Post("/add")
    fun addContact(@Body apiContact: APIContact): Mono<APIContact> {
        val newContact = contactRepository.save(apiContact.toContact())
        return newContact.map { it.toAPIContact() }
    }

    @Put("/{id}")
    fun updateContact(@PathVariable id: Long, @Body apiContact: APIContact): Mono<APIContact> {
        val contactToUpdate = apiContact.toContact().copy(id = id)
        val updatedContact = contactRepository.update(contactToUpdate)
        return updatedContact.map { it.toAPIContact() }
    }
}

private fun Contact.toAPIContact(): APIContact =
    APIContact(firstName = firstName, lastName = lastName, email = email, phone = phone)

private fun APIContact.toContact(): Contact =
    Contact(firstName = firstName, lastName = lastName, email = email, phone = phone)

@Serdeable
data class APIContact(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?
)

@Serdeable
data class NotFoundResponse(
    val message: String
)