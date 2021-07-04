package de.zappold.bankservice.model

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(
    name = "checkingAccount",
    indexes = [Index(name = "idx_checkingaccount_accountnumber", columnList = "accountNumber")]
)
data class CheckingAccount(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var accountNumber: Long?,
    var name: String,
    var balance: Long,
    var dispolimit: Int,
    var pin: String,
    @ManyToMany(mappedBy = "checkingAccount", fetch = FetchType.EAGER)
    var customer: MutableCollection<Customer>
)
