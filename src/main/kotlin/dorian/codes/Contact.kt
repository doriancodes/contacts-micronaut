package dorian.codes

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "contacts")
data class Contact(
    @Id
    @GeneratedValue
    val id: Long? = null,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?
)