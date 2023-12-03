package com.songyi.r2dbctutorial.book.application

import com.songyi.r2dbctutorial.book.domain.Book
import com.songyi.r2dbctutorial.book.domain.vo.ISBN
import com.songyi.r2dbctutorial.book.port.out.BookCommandPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookCommandPort: BookCommandPort
) {
    @Transactional
    suspend fun register(request: BookData): BookResponse {
        val book = bookCommandPort.save(
            Book(
                name = request.name,
                author = request.author,
                isbn = ISBN(request.isbn),
                publishedTime = request.publishedTime
            )
        )
        return BookResponse.from(book)
    }
}