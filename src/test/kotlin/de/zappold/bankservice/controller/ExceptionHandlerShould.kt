package de.zappold.bankservice.controller

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.http.HttpStatus

@TestMethodOrder(MethodOrderer.Random::class)
class ExceptionHandlerShould {
    private val exceptionHandler = ExceptionHandler()

    @Test
    internal fun `response with NOT FOUND on NoSuchElementException`() {
        val handleNotFound = exceptionHandler.handleNotFound(NoSuchElementException("This element wasn't found"))
        assertThat(handleNotFound.statusCode).isEqualTo(HttpStatus.NOT_FOUND)
    }

    @Test
    internal fun `response with BAD REQUEST on IllegalArgumentException`() {
        val handleNotFound = exceptionHandler.handleBadRequest(IllegalArgumentException("Something went wrong"))
        assertThat(handleNotFound.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
    }
}
