package de.zappold.bankservice.services

import de.zappold.bankservice.model.CheckingAccount
import de.zappold.bankservice.repository.CheckingAccountRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CheckingAccountService(private val checkingAccountRepository: CheckingAccountRepository) {
    fun retrieveAllCheckingAccounts(): Collection<CheckingAccount> =
        checkingAccountRepository.findAll()

    fun retrieveCheckingAccount(accountNumber: Long): CheckingAccount =
        checkingAccountRepository
            .findByIdOrNull(accountNumber)
            ?: accountNumberNotFound(accountNumber)

    fun createCheckingAccount(checkingAccount: CheckingAccount): CheckingAccount =
        checkingAccountRepository.save(
            CheckingAccount(
                null,
                checkingAccount.name,
                checkingAccount.balance,
                checkingAccount.dispolimit,
                checkingAccount.pin
            )
        )

    fun updateCheckingAccount(checkingAccount: CheckingAccount, verificationPin: String): CheckingAccount =
        checkingAccountRepository
            .findByIdOrNull(checkingAccount.accountNumber)
            ?.let {
                checkingAccountRepository.save(checkingAccount)
            }
            ?: accountNumberNotFound(checkingAccount.accountNumber)

}

private fun accountNumberNotFound(accountNumber: Long?): Nothing =
    throw NoSuchElementException("Checking account with account number '$accountNumber' not found.")
