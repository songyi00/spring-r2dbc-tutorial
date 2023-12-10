package com.songyi.r2dbctutorial.book.application

import com.songyi.r2dbctutorial.book.domain.Book
import com.songyi.r2dbctutorial.book.domain.BookStatus
import com.songyi.r2dbctutorial.book.domain.vo.ISBN
import com.songyi.r2dbctutorial.book.port.`in`.DeleteBookUseCase
import com.songyi.r2dbctutorial.book.port.`in`.RegisterBookUseCase
import com.songyi.r2dbctutorial.book.port.`in`.RegisterBookUseCase.BookData
import com.songyi.r2dbctutorial.book.port.`in`.UpdateStockUseCase
import com.songyi.r2dbctutorial.book.port.out.BookCommandPort
import com.songyi.r2dbctutorial.book.port.out.BookQueryPort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BookService(
    private val bookCommandPort: BookCommandPort,
    private val bookQueryPort: BookQueryPort
) : RegisterBookUseCase, UpdateStockUseCase, DeleteBookUseCase {
    @Transactional
    override suspend fun registerBook(request: BookData): Book {
        return bookCommandPort.save(
            Book(
                name = request.name,
                author = request.author,
                isbn = ISBN(request.isbn),
                source = request.source,
                publishedDate = request.publishedDate,
                totalStock = request.totalStock
            )
        )
    }

    @Transactional
    override suspend fun updateStock(stockData: UpdateStockUseCase.StockData): Book {
        val book = bookQueryPort.loadBook(stockData.bookId)
        book.updateStock(stockData.rentedCount)
        return bookCommandPort.update(book)
    }

    @Transactional
    override suspend fun deleteBook(bookId: Long) {
        bookCommandPort.delete(bookId)
    }
}