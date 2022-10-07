package dorian.codes

import io.micronaut.runtime.Micronaut.*
import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "Contacts API",
        version = "0.1",
        description = "Find, save, update contacts."
    )
)
class Application

fun main(args: Array<String>) {
    run(*args)
}

