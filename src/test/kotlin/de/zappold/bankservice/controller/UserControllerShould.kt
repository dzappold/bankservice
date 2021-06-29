package de.zappold.bankservice.controller

import de.zappold.bankservice.services.UserService
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder

@TestMethodOrder(MethodOrderer.Random::class)
class UserControllerShould {
    @Test
    internal fun `interact with user service for retrieving all users`() {
        val userService = mockk<UserService>()
        every { userService.retrieveAllUsers() } returns emptyList()

        val userController = UserController(userService)
        userController.retrieveAllUsers()

        verify(exactly = 1) { userService.retrieveAllUsers() }
    }
}
