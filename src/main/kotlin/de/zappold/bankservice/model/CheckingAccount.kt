package de.zappold.bankservice.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class CheckingAccount(
    accountNumber: Long?,
    var name: String,
    var balance: Long,
    var dispolimit: Int,
    var pin: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var accountNumber = accountNumber
        protected set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CheckingAccount

        if (name != other.name) return false
        if (balance != other.balance) return false
        if (dispolimit != other.dispolimit) return false
        if (pin != other.pin) return false
        if (accountNumber != other.accountNumber) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + balance.hashCode()
        result = 31 * result + dispolimit
        result = 31 * result + pin.hashCode()
        result = 31 * result + (accountNumber?.hashCode() ?: 0)
        return result
    }

}
