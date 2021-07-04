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
        checkingAccountRepository.save(checkingAccount.copy(accountNumber = null))

    fun updateCheckingAccount(checkingAccount: CheckingAccount, verificationPin: String): CheckingAccount =
        checkingAccountRepository
            .findByIdOrNull(checkingAccount.accountNumber)
            ?.let { existingAccount ->
                if (existingAccount.pin == verificationPin)
                    checkingAccountRepository.save(checkingAccount)
                else
                    throw AuthenticationException("Authentication failed.")
            }
            ?: accountNumberNotFound(checkingAccount.accountNumber)

}

private fun accountNumberNotFound(accountNumber: Long?): Nothing =
    throw NoSuchElementException("Checking account with account number '$accountNumber' not found.")

class AuthenticationException(override val message: String) : RuntimeException(message)
