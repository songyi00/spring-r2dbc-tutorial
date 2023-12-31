package com.study.library.book.adapter.`in`

import com.study.library.book.port.`in`.DeleteBookUseCase
import com.study.library.book.port.`in`.RegisterBookUseCase
import com.study.library.common.auth.USER_DETAILS
import org.springframework.stereotype.Controller
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.awaitBody
import org.springframework.web.reactive.function.server.bodyValueAndAwait
import org.springframework.web.reactive.function.server.buildAndAwait

import java.net.URI

@Controller
class BookRouter(
    private val registerBookUseCase: RegisterBookUseCase,
    private val deleteBookUseCase: DeleteBookUseCase
) {
    suspend fun registerBook(request: ServerRequest): ServerResponse {
        val bookData = request.awaitBody<RegisterBookUseCase.BookData>()
        val response = registerBookUseCase.registerBook(bookData)

        println("hi: " + request.attribute(USER_DETAILS))

        return ServerResponse.created(
            URI.create("/api/books/${response.id}")
        ).bodyValueAndAwait(response)
    }

    suspend fun deleteBook(request: ServerRequest): ServerResponse {
        val bookId = request.pathVariable("bookId").toLong()
        deleteBookUseCase.deleteBook(bookId)

        return ServerResponse.noContent().buildAndAwait()
    }
}
