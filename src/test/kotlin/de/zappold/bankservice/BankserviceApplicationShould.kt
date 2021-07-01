package de.zappold.bankservice

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT

@SpringBootTest(webEnvironment = RANDOM_PORT)
class BankserviceApplicationShould {

    @Test
    fun `load context successfully`() {
    }

}
