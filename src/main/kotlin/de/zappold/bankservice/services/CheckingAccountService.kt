package de.zappold.bankservice.services

import de.zappold.bankservice.model.CheckingAccount
import org.springframework.stereotype.Service

@Service
class CheckingAccountService {
    fun retrieveAllCheckingAccounts(): Collection<CheckingAccount> {
        TODO("Not yet implemented")
    }

    fun retrieveCheckingAccount(accountNumber: Long): CheckingAccount {
        TODO("Not yet implemented")
    }

    fun createCheckingAccount(checkingAccount: CheckingAccount): CheckingAccount {
        TODO("Not yet implemented")
    }

    fun updateCheckingAccount(checkingAccount: CheckingAccount): CheckingAccount {
        TODO("Not yet implemented")
    }

}
