package de.zappold.bankservice.services

import de.zappold.bankservice.model.Customer
import de.zappold.bankservice.repository.CustomerRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CustomerService(private val customerRepository: CustomerRepository) {

    fun retrieveAllCustomers(): Collection<Customer> =
        customerRepository.findAll()

    fun retrieveCustomer(customerNumber: String): Customer =
        customerRepository
            .findByIdOrNull(customerNumber.toLong())
            .orThrow { customerNotFound(customerNumber) }

    fun createCustomer(customer: Customer): Customer =
        customerRepository.save(
            Customer(
                null,
                customer.lastName,
                customer.firstName,
                customer.birthday,
                customer.gender
            )
        )

    fun updateCustomer(customer: Customer): Customer =
        (customerRepository.findByIdOrNull(customer.customerNumber)?.let {

            customerRepository.save(customer)
        } ?: throw customerNotFound(customer.customerNumber.toString()))

    fun deleteCustomer(customerNumber: String): Unit =
        customerRepository.deleteById(customerNumber.toLong())

    private fun customerNotFound(customerNumber: String) =
         NoSuchElementException("No customer with customer number '$customerNumber' found.")

}

private fun Customer?.orThrow(failure: () -> Exception): Customer =
    let { if (it == null) throw failure() else return this!! }
