package de.zappold.bankservice.acceptancetests

import com.fasterxml.jackson.databind.ObjectMapper
import de.zappold.bankservice.controller.CHECKING_ACCOUNT_BASE_PATH
import de.zappold.bankservice.controller.CUSTOMER_BASE_PATH
import de.zappold.bankservice.controller.UpdateCheckingAccount
import de.zappold.bankservice.model.CheckingAccount
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
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.Random::class)
class CheckingAccountAcceptanceTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {
    @Nested
    @DisplayName("GET calls for $CHECKING_ACCOUNT_BASE_PATH")
    inner class GetCalls {
        @Test
        internal fun `is successful on getting all checking accounts with no checking account available`() {
            mockMvc.get(CHECKING_ACCOUNT_BASE_PATH)
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                }
        }

        @Test
        internal fun `fails with NOT_FOUND when specified checking account is not available`() {
            mockMvc.get("$CHECKING_ACCOUNT_BASE_PATH/3")
                .andExpect { status { isNotFound() } }
        }
    }

    @Nested
    @DisplayName("POST calls for $CHECKING_ACCOUNT_BASE_PATH")
    inner class PostCalls {
        @Test
        internal fun `is successful on creating a new checking account`() {
            val createCustomer = Customer(null, "Q4UEAe89", "RU5", LocalDate.parse("2017-03-01"), Gender.DIVERSE)

            val checkingAccount = CheckingAccount(null, "R0hI", 0, 500, "fp9T7", mutableListOf(createCustomer))


            mockMvc.post(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(createCustomer)
            }
                .andExpect { status { isCreated() } }

            mockMvc.post(CHECKING_ACCOUNT_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(checkingAccount)
            }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(checkingAccount.copy(accountNumber = 5)))
                    }
                }
        }
    }

    @Nested
    @DisplayName("PATCH calls for $CHECKING_ACCOUNT_BASE_PATH")
    inner class PatchCalls {
        @Test
        internal fun `is successful on updating an exitsing checking account`() {
            val firstCustomer = Customer(null, "Q4UEAe89", "RU5", LocalDate.parse("2017-03-01"), Gender.DIVERSE)
            val secondCustomer = Customer(null, "1Hd", "Fm0", LocalDate.parse("2014-05-19"), Gender.FEMALE)

            val firstCustomerNumber: Long = 6
            val secondCustomerNumber: Long = 7
            val accountNumber: Long = 8

            val validationPin = "fp9T7"
            val checkingAccount = CheckingAccount(
                null,
                "R0hI",
                0,
                500,
                validationPin,
                mutableListOf(firstCustomer.copy(customerNumber = firstCustomerNumber))
            )


            mockMvc.post(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(firstCustomer)
            }.andExpect { status { isCreated() } }

            mockMvc.post(CUSTOMER_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(secondCustomer)
            }.andExpect { status { isCreated() } }

            mockMvc.post(CHECKING_ACCOUNT_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(checkingAccount)
            }
                .andExpect {
                    status { isCreated() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(checkingAccount.copy(accountNumber = accountNumber)))
                    }
                }

            val expectedUpdatedCheckingAccount = checkingAccount.copy(
                accountNumber = accountNumber,
                customer = mutableListOf(
                    firstCustomer.copy(customerNumber = firstCustomerNumber),
                    secondCustomer.copy(customerNumber = secondCustomerNumber)
                )
            )
            mockMvc.patch(CHECKING_ACCOUNT_BASE_PATH) {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(
                    UpdateCheckingAccount(
                        validationPin,
                        expectedUpdatedCheckingAccount
                    )
                )
            }
                .andExpect {
                    status { isOk() }
                    content {
                        contentType(MediaType.APPLICATION_JSON)
                        json(objectMapper.writeValueAsString(expectedUpdatedCheckingAccount))
                    }
                }
        }
    }

}
