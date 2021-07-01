package de.zappold.bankservice.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

@Table(name = "Customer", indexes = [
    Index(name = "idx_customer_customernumber", columnList = "customerNumber")
])
@Entity
class Customer(
    customerNumber: Long? = null,
    var lastName: String,
    var firstName: String,
    var birthday: LocalDate,
    @Enumerated(EnumType.STRING)
    var gender: Gender
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var customerNumber = customerNumber
        protected set

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (customerNumber != other.customerNumber) return false
        if (lastName != other.lastName) return false
        if (firstName != other.firstName) return false
        if (birthday != other.birthday) return false
        if (gender != other.gender) return false

        return true
    }

    override fun hashCode(): Int {
        var result = customerNumber?.hashCode() ?: 0
        result = 31 * result + lastName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + birthday.hashCode()
        result = 31 * result + gender.hashCode()
        return result
    }
}
