package de.zappold.bankservice.controller

import de.zappold.bankservice.model.Customer
import de.zappold.bankservice.model.Gender
import de.zappold.bankservice.services.CustomerService
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.Random::class)
class CustomerControllerShould {
    private val miriamMueller =
        Customer(
            id = null,
            customerNumber = "kdnr4711",
            lastName = "Müller",
            firstName = "Miriam",
            birthday = LocalDate.parse("2000-03-17"),
            gender = Gender.FEMALE
        )

    private val customerService: CustomerService = mockk(relaxed = true)

    private val customerController: CustomerController by lazy { CustomerController(customerService) }

    @Test
    internal fun `interact with customer service for retrieving all customers`() {
        customerController.retrieveAllCustomers()

        verify(exactly = 1) { customerService.retrieveAllCustomers() }
    }

    @Test
    internal fun `interact with customer service for retrieving specific customer by customer id`() {
        val id = "RYk"
        customerController.retrieveCustomer(id)

        verify(exactly = 1) { customerService.retrieveCustomer(id) }
    }

    @Test
    internal fun `interact with customer service for creating new customer`() {
        customerController.createCustomer(miriamMueller)

        verify(exactly = 1) { customerService.createCustomer(miriamMueller) }
    }

    @Test
    internal fun `interact with customer service for updating a customer`() {
        customerController.updateCustomer(miriamMueller)

        verify(exactly = 1) { customerService.updateCustomer(miriamMueller) }
    }

    @Test
    internal fun `interact with customer service for deleting a customer`() {
        val id = "26"
        customerController.deleteCustomer(id)

        verify(exactly = 1) { customerService.deleteCustomer(id) }
    }
}
