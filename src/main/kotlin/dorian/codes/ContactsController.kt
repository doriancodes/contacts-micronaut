package dorian.codes

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
        return contactRepository.findById(id).flatMap {
            val contactToUpdate = apiContact.toContact().copy(id = id)
            contactRepository.update(contactToUpdate).map { it.toAPIContact() }
        }
    }

    @Delete("/{id}")
    fun deleteContact(@PathVariable id: Long): Mono<Long> {
        return contactRepository.findById(id).flatMap {
            contactRepository.delete(it)
        }
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
