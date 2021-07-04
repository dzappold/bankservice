package de.zappold.bankservice.acceptancetests

import com.fasterxml.jackson.databind.ObjectMapper
import de.zappold.bankservice.controller.CUSTOMER_BASE_PATH
import de.zappold.bankservice.model.Customer
import de.zappold.bankservice.model.Gender
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
import org.springframework.test.web.servlet.delete
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.Random::class)
class CustomerServiceAcceptanceTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {

    @Nested
    @DisplayName("GET calls for $CUSTOMER_BASE_PATH")
    inner class GetCalls {
        @Test
        internal fun `is successful on getting all customers with no customers available`() {
            mockMvc.get(CUSTOMER_BASE_PATH)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        internal fun `fails with NOT_FOUND when specified customer is not available`() {
            mockMvc.get("$CUSTOMER_BASE_PATH/3")
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST calls for $CUSTOMER_BASE_PATH")
    inner class PostCalls {
        @Test
        internal fun `is successful on creating a new customer`() {
            val createCustomer = Customer(null, "Q4UEAe89", "RU5", LocalDate.parse("2017-03-01"), Gender.DIVERSE)
            val expectedCustomer = Customer(1, "Q4UEAe89", "RU5", LocalDate.parse("2017-03-01"), Gender.DIVERSE)

            mockMvc.post(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(createCustomer)
            }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(expectedCustomer))
                    }
                }

            mockMvc.get("$CUSTOMER_BASE_PATH/${expectedCustomer.customerNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(expectedCustomer))
                    }
                }
        }
    }

    @Nested
    @DisplayName("PATCH calls for $CUSTOMER_BASE_PATH")
    inner class PatchCalls {
        @Test
        internal fun `is successful on updating existing customer`() {
            val createCustomer = Customer(null, "Q4UEAe89", "RU5", LocalDate.parse("2017-03-01"), Gender.DIVERSE)
            val expectedCustomer = createCustomer.copy(customerNumber = 2)

            mockMvc.post(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(createCustomer)
            }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(expectedCustomer))
                    }
                }

            val update = expectedCustomer.copy(lastName = "N4B4V6KH", firstName = "4Jc4P")
            mockMvc.patch(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(update)
            }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(update))
                    }
                }

            mockMvc.get("$CUSTOMER_BASE_PATH/${update.customerNumber}")
                .andExpect {
                    content {
                        json(objectMapper.writeValueAsString(update))
                    }
                }
        }

        @Test
        fun `fails with NOT FOUND if no customer with given customer number exists`() {
            val nonExistingCustomer = Customer(268, "Q4UEAe89", "RU5", LocalDate.parse("2017-03-01"), Gender.DIVERSE)

            mockMvc.patch(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(nonExistingCustomer)
            }
                .andExpect {
                    status {
                        isNotFound()
                    }
                }
        }

    }

    @Nested
    @DisplayName("DELETE calls for $CUSTOMER_BASE_PATH")
    inner class DeleteCalls {
        @Test
        internal fun `is successful on deleting existing customer`() {
            val createCustomer = Customer(null, "Q4UEAe89", "RU5", LocalDate.parse("2017-03-01"), Gender.DIVERSE)
            val expectedCustomer = createCustomer.copy(customerNumber = 3)

            mockMvc.post(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(createCustomer)
            }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(expectedCustomer))
                    }
                }

            mockMvc.delete("$CUSTOMER_BASE_PATH/${expectedCustomer.customerNumber}")
                .andExpect { status { isNoContent() } }

            mockMvc.get("$CUSTOMER_BASE_PATH/${expectedCustomer.customerNumber}")
                .andExpect { status { isNotFound() } }
        }
    }
}

