package dorian.codes

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.*
import io.micronaut.serde.annotation.Serdeable
import io.micronaut.validation.Validated
import jakarta.inject.Singleton

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
    fun getContact(@PathVariable id: Long): HttpResponse<*> {
        val contact = contactRepository.findById(id)
        if (contact.isPresent) return HttpResponse.ok(contact.get().toAPIContact())
        return HttpResponse.notFound(NotFoundResponse("Contact $id not found."))
    }

    @Get()
    fun getAllContacts(): HttpResponse<*> {
        val contacts = contactRepository.findAll().toList()
        if (contacts.isEmpty()) return HttpResponse.ok(NotFoundResponse("No contacts found."))
        return HttpResponse.ok(contacts.map { contact -> contact.toAPIContact() })
    }

    @Post("/add")
    fun addContact(@Body apiContact: APIContact): HttpResponse<APIContact> {
        contactRepository.save(apiContact.toContact())
        return HttpResponse.created(apiContact)
    }

    @Put("/{id}")
    fun updateContact(@PathVariable id: Long, @Body apiContact: APIContact): HttpResponse<APIContact> {
        val contactToUpdate = apiContact.toContact().copy(id = id)
        contactRepository.update(contactToUpdate)
        return HttpResponse.ok(contactToUpdate.toAPIContact())
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