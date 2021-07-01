package de.zappold.bankservice.services

import de.zappold.bankservice.model.Customer
import de.zappold.bankservice.repository.CustomerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun retrieveAllCustomers(): Collection<Customer> =
        customerRepository.findAll()

    fun retrieveCustomer(id: String): Customer =
        customerRepository
            .findByIdOrNull(id.toLong())
            .orThrow { customerNotFound(id) }

    fun createCustomer(customer: Customer): Customer =
        customerRepository.save(
            Customer(
                null,
                customer.customerNumber,
                customer.lastName,
                customer.firstName,
                customer.birthday,
                customer.gender
            )
        )

    fun updateCustomer(customer: Customer): Customer =
        customer
            .takeIfCustomerNumberStaysSame()
            .saveCustomer(customer)

    fun deleteCustomer(id: String): Unit =
        customerRepository.deleteById(id.toLong())

    private fun Customer.saveCustomer(customer: Customer): Customer =
        run { customerRepository.save(customer) }

    private fun Customer.takeIfCustomerNumberStaysSame() =
        takeIf { retrieveExistingCustomerNumberFor(id) == customerNumber }
            ?: throw changingCustomerNumberNotAllowed()

    private fun retrieveExistingCustomerNumberFor(id: Long?) =
        retrieveExistingCustomer(id).customerNumber

    private fun retrieveExistingCustomer(id: Long?) =
        customerRepository
            .findByIdOrNull(id)
            .orThrow { customerNotFound(id.toString()) }

    private fun customerNotFound(id: String) =
        NoSuchElementException("No customer with customer number '$id' found.")

    private fun changingCustomerNumberNotAllowed() =
        UnsupportedOperationException("Changing the customer number is not allowed.")
}

private fun Customer?.orThrow(failure: () -> Exception): Customer =
    let { if (it == null) throw failure() else return this!! }
