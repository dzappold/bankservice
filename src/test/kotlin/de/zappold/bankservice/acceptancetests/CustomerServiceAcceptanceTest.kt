package de.zappold.bankservice.acceptancetests

import de.zappold.bankservice.controller.CUSTOMER_BASE_PATH
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.Random::class)
class CustomerServiceAcceptanceTest(@Autowired private val mockMvc: MockMvc) {

    @Nested
    @DisplayName("GET calls for $CUSTOMER_BASE_PATH")
    inner class GetCalls {
        @Test
        internal fun `is successful on getting all users with no users available`() {
            mockMvc.get(CUSTOMER_BASE_PATH)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        internal fun `fails with NOT_FOUND when specified user is not available`() {
            mockMvc.get("$CUSTOMER_BASE_PATH/3")
                .andExpect { status { isNotFound() } }
        }
    }
}

