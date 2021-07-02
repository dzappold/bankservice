package de.zappold.bankservice.controller

import de.zappold.bankservice.model.CheckingAccount
import de.zappold.bankservice.services.CheckingAccountService
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.Random::class)
class CheckingAccountControllerShould {
    private val checkingAccountService: CheckingAccountService = mockk(relaxed = true)

    private val checkingAccountController: CheckingAccountController by lazy {
        CheckingAccountController(checkingAccountService)
    }

    private val checkingAccount: CheckingAccount = CheckingAccount(
        accountNumber = null,
        name = "xyz",
        balance = 0L,
        dispolimit = 500,
        pin = "O1MpkrP"
    )

    @Test
    internal fun `retrieve all checking accounts from service`() {
        checkingAccountController.retrieveAllCheckingAccounts()

        verify(exactly = 1) { checkingAccountService.retrieveAllCheckingAccounts() }
    }

    @Test
    internal fun `retrieve a single checking accounts from service`() {
        val accountNumber = 12345L
        checkingAccountController.retrieveCheckingAccount(accountNumber)

        verify(exactly = 1) { checkingAccountService.retrieveCheckingAccount(accountNumber) }
    }

    @Test
    internal fun `create a checking account from service`() {
        checkingAccountController.createCheckingAccount(checkingAccount)

        verify(exactly = 1) { checkingAccountService.createCheckingAccount(checkingAccount) }
    }

    @Test
    internal fun `update a checking account from service`() {
        checkingAccountController.updateCheckingAccount(checkingAccount)

        verify(exactly = 1) { checkingAccountService.updateCheckingAccount(checkingAccount) }
    }

    // TODO: /api/v0/checkingAccount/accountNumber/deposit/700
    // TODO: /api/v0/checkingAccount/accountNumber/withdrawal/700
}
