package de.zappold.bankservice.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "customer", indexes = [Index(name = "idx_customer_customernumber", columnList = "customerNumber")])
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var customerNumber: Long? = null,
    var lastName: String,
    var firstName: String,
    var birthday: LocalDate,
    @Enumerated(EnumType.STRING)
    var gender: Gender,
    @ManyToMany
    var checkingAccount: MutableList<CheckingAccount> = mutableListOf()
)
