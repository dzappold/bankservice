package de.zappold.bankservice.repository

import de.zappold.bankservice.model.CheckingAccount
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CheckingAccountRepository : JpaRepository<CheckingAccount, Long>
