package de.zappold.bankservice.controller

import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.Random::class)
class UserServiceAcceptanceTest(@Autowired private val mockMvc: MockMvc) {
    @Test
    internal fun `return NOT_FOUND when no users available`() {
        mockMvc.get("/users")
            .andExpect { status { isNotFound() } }
    }
}

