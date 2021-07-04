package de.zappold.bankservice.controller

import de.zappold.bankservice.model.CheckingAccount
import de.zappold.bankservice.services.CheckingAccountService
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

const val CHECKING_ACCOUNT_BASE_PATH = "/api/v0/checkingaccounts"

@RestController
@RequestMapping(CHECKING_ACCOUNT_BASE_PATH, produces = [MediaType.APPLICATION_JSON_VALUE])
class CheckingAccountController(private val checkingAccountService: CheckingAccountService) {
    @GetMapping
    fun retrieveAllCheckingAccounts(): Collection<CheckingAccount> =
        checkingAccountService.retrieveAllCheckingAccounts()

    @GetMapping("/{accountNumber}")
    fun retrieveCheckingAccount(@PathVariable accountNumber: Long): CheckingAccount =
        checkingAccountService.retrieveCheckingAccount(accountNumber)

    @PostMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    @ResponseStatus(HttpStatus.CREATED)
    fun createCheckingAccount(@RequestBody checkingAccount: CheckingAccount): CheckingAccount =
        checkingAccountService.createCheckingAccount(checkingAccount)

    @PatchMapping(consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun updateCheckingAccount(
        @RequestBody checkingAccount: CheckingAccount,
        @RequestBody verificationPin: String
    ): CheckingAccount =
        checkingAccountService.updateCheckingAccount(checkingAccount, verificationPin)
}
