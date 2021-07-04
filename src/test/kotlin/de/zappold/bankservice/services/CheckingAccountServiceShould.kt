package de.zappold.bankservice.services

import de.zappold.bankservice.model.CheckingAccount
import de.zappold.bankservice.repository.CheckingAccountRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.junit.jupiter.api.assertThrows
import org.springframework.data.repository.findByIdOrNull

@TestMethodOrder(MethodOrderer.Random::class)
class CheckingAccountServiceShould {
    private val checkingAccountRepository: CheckingAccountRepository = mockk()
    private val checkingAccountService = CheckingAccountService(checkingAccountRepository)

    @Test
    internal fun `retrieve all checking accounts from repository`() {
        every { checkingAccountRepository.findAll() } returns emptyList()

        checkingAccountService.retrieveAllCheckingAccounts()

        verify(exactly = 1) { checkingAccountRepository.findAll() }
    }

    @Test
    internal fun `retrieve specific existing checking account from repository`() {
        mockkStatic(CheckingAccountRepository::findByIdOrNull)
        val accountNumber: Long = 93
        every { checkingAccountRepository.findByIdOrNull(accountNumber) } returns checkingAccount(
            accountNumber,
            "n3334Rw"
        )

        checkingAccountService.retrieveCheckingAccount(accountNumber)

        verify(exactly = 1) { checkingAccountRepository.findByIdOrNull(accountNumber) }
    }

    @Test
    internal fun `retrieve specific non-existing checking account from repository`() {
        mockkStatic(CheckingAccountRepository::findByIdOrNull)
        val accountNumber: Long = 146
        every { checkingAccountRepository.findByIdOrNull(accountNumber) } returns null

        assertThrows<NoSuchElementException> {
            checkingAccountService.retrieveCheckingAccount(accountNumber)
        }
    }

    @Test
    internal fun `create a new checking account from repository`() {
        val checkingAccount = checkingAccount(null, "n3334Rw")
        every { checkingAccountRepository.save(checkingAccount) } returns checkingAccount(456, "n3334Rw")

        checkingAccountService.createCheckingAccount(checkingAccount)

        verify(exactly = 1) { checkingAccountRepository.save(checkingAccount) }
    }

    @Test
    internal fun `create a new checking account with account number will override account number from repository`() {
        val checkingAccount = checkingAccount(null, "n3334Rw")
        every { checkingAccountRepository.save(checkingAccount) } returns checkingAccount(456, "n3334Rw")

        checkingAccountService.createCheckingAccount(checkingAccount(339, "n3334Rw"))

        verify(exactly = 1) { checkingAccountRepository.save(checkingAccount) }
    }

    @Test
    internal fun `update checking account with same values from repository`() {
        mockkStatic(CheckingAccountRepository::findByIdOrNull)

        val verificationPin = "n3334Rw"
        val checkingAccount = checkingAccount(467, verificationPin)
        every { checkingAccountRepository.findByIdOrNull(any()) } returns checkingAccount
        every { checkingAccountRepository.save(checkingAccount) } returns checkingAccount

        checkingAccountService.updateCheckingAccount(checkingAccount, verificationPin)

        verify(exactly = 1) { checkingAccountRepository.save(checkingAccount) }
    }

    // TODO: update checking account needs pin for validation
    // TODO: update checking account non existing
    // TODO: update checking account - modify balance not allowed

    private fun checkingAccount(accountNumber: Long?, verificationPin: String) =
        CheckingAccount(
            accountNumber = accountNumber,
            name = "ih1",
            balance = 0,
            dispolimit = 500,
            pin = verificationPin,
            customer = mutableListOf()
        )
}
