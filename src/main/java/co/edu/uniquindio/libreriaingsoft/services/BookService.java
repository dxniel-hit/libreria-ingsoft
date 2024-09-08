package co.edu.uniquindio.libreriaingsoft.services;


import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    private UserService userService;

    public Page<Book> searchBooksByTitleAndAuthor(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return bookRepository.searchByTitleAndAuthor(query, pageable);
    }

    public List<Book> searchBooksByTitleAuthorOrIsbn(String query) {
        return bookRepository.searchByTitleAuthorOrIsbn(query);
    }

    // Método para agregar una reseña a un libro
    public void addReviewToBook(String bookId, Book.Review review) {
        // Buscar el libro por su ID
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Obtener el username a partir del userId del review
        String username = userService.findUsernameById(review.getReviewer());

        // Asignar el username a la reseña
        review.setReviewer(username);

        // Agregar la reseña al libro y guardarlo
        book.addReview(review);
        bookRepository.save(book);
    }


    // Método para obtener reseñas de un libro
    public List<Book.Review> getReviewsForBook(String bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
        return book.getReviews();
    }

    /**
     * Gets all books from the db
     * @return
     */
    public ResponseEntity<?> findAllBooksPaginated(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            Page<Book> books = bookRepository.findAll(pageable);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed books request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
