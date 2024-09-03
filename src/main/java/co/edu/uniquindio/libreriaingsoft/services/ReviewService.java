package co.edu.uniquindio.libreriaingsoft.services;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final BookRepository bookRepository;

    public ReviewService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void reviewBook(String bookId, String review) {
        if (review.isEmpty()) {
            throw new IllegalArgumentException("Review cannot be empty");
        }
        if (review.length() > 5000) {
            throw new IllegalArgumentException("Review cannot be longer than 5000 characters");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.addReview(review);
        bookRepository.save(book);
    }
}
