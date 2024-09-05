package co.edu.uniquindio.libreriaingsoft.services;


import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    public BookRepository bookRepository;

    public List<Book> searchBooksByTitleAndAuthor(String query) {
        return bookRepository.searchByTitleAndAuthor(query);
    }

    public List<Book> searchBooksByTitleAuthorOrIsbn(String query) {
        return bookRepository.searchByTitleAuthorOrIsbn(query);
    }

    // Método para agregar una reseña a un libro
    public void addReviewToBook(String bookId, Book.Review review) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        book.addReview(review); // Parsear en el back y front
        bookRepository.save(book);
    }


    // Método para obtener reseñas de un libro
    public List<Book.Review> getReviewsForBook(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        return book.getReviews();
    }

}
