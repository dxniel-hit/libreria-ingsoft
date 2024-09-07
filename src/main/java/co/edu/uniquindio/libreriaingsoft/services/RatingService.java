package co.edu.uniquindio.libreriaingsoft.services;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    @Autowired
    private BookRepository bookRepository;

    public void rateBook(String bookId, int rating) {
        Book book = bookRepository.findById(bookId).orElse(null);
        assert book != null;
        book.addRating(rating);
        bookRepository.save(book);
    }
}
