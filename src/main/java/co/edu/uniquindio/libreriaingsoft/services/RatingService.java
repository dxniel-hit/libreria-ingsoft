package co.edu.uniquindio.libreriaingsoft.services;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final BookRepository bookRepository;

    @Autowired
    public RatingService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void rateBook(String bookId, int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating should be between 1 and 5");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        book.addRating(rating);
        bookRepository.save(book);
    }
}
