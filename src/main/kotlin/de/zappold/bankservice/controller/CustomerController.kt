package de.zappold.bankservice.controller

import de.zappold.bankservice.model.Customer
import de.zappold.bankservice.services.CustomerService
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.HttpStatus.NO_CONTENT
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

const val CUSTOMER_BASE_PATH = "/api/v0/customers"

@RestController
@RequestMapping(CUSTOMER_BASE_PATH, produces = [APPLICATION_JSON_VALUE])
class CustomerController(private val customerService: CustomerService) {
    @GetMapping
    fun retrieveAllCustomers(): Collection<Customer> =
        customerService.retrieveAllCustomers()

    @GetMapping("/{customerNumber}")
    fun retrieveCustomer(@PathVariable customerNumber: String): Customer =
        customerService.retrieveCustomer(customerNumber)

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    @ResponseStatus(CREATED)
    fun createCustomer(@RequestBody customer: Customer): Customer =
        customerService.createCustomer(customer)

    @PatchMapping(consumes = [APPLICATION_JSON_VALUE])
    fun updateCustomer(@RequestBody customer: Customer): Customer =
        customerService.updateCustomer(customer)

    @DeleteMapping("/{customerNumber}")
    @ResponseStatus(NO_CONTENT)
    fun deleteCustomer(@PathVariable customerNumber: String): Unit =
        customerService.deleteCustomer(customerNumber)
}
