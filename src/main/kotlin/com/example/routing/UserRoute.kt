package com.example.routing

import com.example.model.User
import com.example.routing.request.UserRequest
import com.example.routing.response.UserResponse
import com.example.service.UserService
import com.example.util.autherized
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.userRoute(
    userService: UserService
) {
    post {
        val userRequest = call.receive<UserRequest>()
        val createdUser = userService.save(
            user = userRequest.toModel()
        ) ?: return@post call.response.status(HttpStatusCode.BadRequest)

        call.response.header(
            name = "id",
            value = createdUser.id.toString()
        )
        call.respond(message = HttpStatusCode.Created)

        authenticate {
            autherized("ADMIN") {
                get {
                    val users = userService.findAll()
                    call.respond(
                        message = users.map(User::toResponse)
                    )
                }
            }
        }

        authenticate("another-auth") {
            autherized("ADMIN", "USER") {
                get("/{id}") {
                    val id = call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)
                    val foundUser = userService.findById(id = id) ?: return@get call.respond(HttpStatusCode.NotFound)
                    if (foundUser.username != extractPrincipleUser(call)) {
                        return@get call.respond(HttpStatusCode.NotFound)
                    }
                    call.respond(
                        message = foundUser.toResponse()
                    )

                }
            }
        }

    }
}

fun extractPrincipleUser(call: ApplicationCall): String? =
    call.principal<JWTPrincipal>()?.payload?.getClaim("username")?.asString()

private fun UserRequest.toModel(): User = User(
    id = UUID.randomUUID(),
    username = this.username,
    password = this.password,
    role = "User"
)

private fun User.toResponse(): UserResponse = UserResponse(
    id = this.id,
    username = this.username
)
