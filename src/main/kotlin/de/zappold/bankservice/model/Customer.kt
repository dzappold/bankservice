package de.zappold.bankservice.model

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    var customerNumber: String,
    var lastName: String,
    var firstName: String,
    var birthday: LocalDate,
    @Enumerated(EnumType.STRING)
    var gender: Gender
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Customer

        if (id != other.id) return false
        if (customerNumber != other.customerNumber) return false
        if (lastName != other.lastName) return false
        if (firstName != other.firstName) return false
        if (birthday != other.birthday) return false
        if (gender != other.gender) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + customerNumber.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + firstName.hashCode()
        result = 31 * result + birthday.hashCode()
        result = 31 * result + gender.hashCode()
        return result
    }
}
