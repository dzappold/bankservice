package de.zappold.bankservice.services

import assertk.assertAll
import assertk.assertThat
import assertk.assertions.messageContains
import de.zappold.bankservice.model.Customer
import de.zappold.bankservice.model.Gender.FEMALE
import de.zappold.bankservice.repository.CustomerRepository
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDate

@TestMethodOrder(MethodOrderer.Random::class)
class CustomerServiceShould {

    private val customerRepository: CustomerRepository = mockk()
    private val customerService = CustomerService(customerRepository)

    @Test
    internal fun `interact with customer repository for finding all customers`() {
        every { customerRepository.findAll() } returns emptyList()

        customerService.retrieveAllCustomers()

        verify(exactly = 1) { customerRepository.findAll() }
    }

    @Test
    internal fun `interact with customer repository for find specific customers`() {
        val unknownId = 70L
        every { customerRepository.findByIdOrNull(unknownId) } returns null

        val thrownException = assertThrows<NoSuchElementException> {
            customerService.retrieveCustomer(unknownId.toString())
        }
        assertThat(thrownException).messageContains("No customer with customer number '$unknownId' found.")
        verify(exactly = 1) { customerRepository.findByIdOrNull(unknownId) }
    }

    @Test
    internal fun `interact with customer repository for find specific customers EXISTING`() {
        val knownId = 23L
        every { customerRepository.findByIdOrNull(knownId) } returns mariaSchmidt(knownId)

        customerService.retrieveCustomer(knownId.toString())
        verify(exactly = 1) { customerRepository.findByIdOrNull(knownId) }
    }

    @Test
    internal fun `interact with customer repository for save new customer`() {
        val mariaSchmidt = mariaSchmidt(null)
        val createdMariaSchmidt = mariaSchmidt(17L)

        every { customerRepository.save(mariaSchmidt) } returns createdMariaSchmidt

        customerService.createCustomer(mariaSchmidt)
        verify(exactly = 1) { customerRepository.save(mariaSchmidt) }
    }

    @Test
    internal fun `interact with customer repository for save new customer with defined id will override id`() {
        every { customerRepository.save(any()) } returns mariaSchmidt(17L)

        customerService.createCustomer(mariaSchmidt(26L))
        verify(exactly = 1) { customerRepository.save(mariaSchmidt(null)) }
    }

    @Test
    internal fun `interact with customer repository for update customer`() {
        // CustomerRepository::findByIdOrNull is an extension function therefore we need mockkStatic
        mockkStatic(CustomerRepository::findByIdOrNull)

        val customerNumber = 17L
        every { customerRepository.findByIdOrNull(any()) } returns susanneMaier(customerNumber)
        every { customerRepository.save(any()) } returns mariaSchmidt(customerNumber)

        customerService.updateCustomer(mariaSchmidt(customerNumber))
        verify(exactly = 1) { customerRepository.findByIdOrNull(customerNumber) }
    }

    @Test
    internal fun `interact with customer repository for update non existing customer`() {
        // CustomerRepository::findByIdOrNull is an extension function therefore we need mockkStatic
        mockkStatic(CustomerRepository::findByIdOrNull)

        every { customerRepository.findByIdOrNull(any()) } returns null

        assertAll {
            assertThrows<NoSuchElementException> {
                customerService.updateCustomer(mariaSchmidt(17L))
            }

            verify(exactly = 1) { customerRepository.findByIdOrNull(17) }
        }
    }

    @Test
    internal fun `interact with customer repository for delete customer`() {
        val idToDelete = 17L
        every { customerRepository.deleteById(idToDelete) } just Runs

        customerService.deleteCustomer(idToDelete.toString())

        verify(exactly = 1) { customerRepository.deleteById(idToDelete) }
    }

    private fun mariaSchmidt(customerNumber: Long?) =
        Customer(
            customerNumber = customerNumber,
            lastName = "Schmidt",
            firstName = "Maria",
            birthday = LocalDate.parse("1997-02-28"),
            gender = FEMALE
        )

    private fun susanneMaier(customerNumber: Long?) =
        Customer(
            customerNumber = customerNumber,
            lastName = "Maier",
            firstName = "Susanne",
            birthday = LocalDate.parse("1997-02-28"),
            gender = FEMALE
        )
}
