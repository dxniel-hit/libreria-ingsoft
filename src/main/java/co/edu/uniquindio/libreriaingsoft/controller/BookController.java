package co.edu.uniquindio.libreriaingsoft.controller;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import co.edu.uniquindio.libreriaingsoft.services.BookService;
import co.edu.uniquindio.libreriaingsoft.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private final BookService bookService;
    @Autowired
    private final RatingService ratingService;
    @Autowired
    private final BookRepository bookRepository;

    public BookController(BookService bookService, RatingService ratingService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.ratingService = ratingService;
        this.bookRepository = bookRepository;
    }

    @GetMapping("/search")
    public List<Book> searchBooksByTitleAndAuthor(@RequestParam String keyword) {
        return bookService.searchBooksByTitleAndAuthor(keyword);
    }

    @PostMapping("/{bookId}/review")
    public ResponseEntity<Void> addReview(@PathVariable String bookId, @RequestBody Book.Review review) {
        bookService.addReviewToBook(bookId, review);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{bookId}/reviews")
    public List<Book.Review> getReviews(@PathVariable String bookId) {
        return bookService.getReviewsForBook(bookId);
    }

    @PostMapping("/{bookId}/rate")
    public ResponseEntity<?> rateBook(@PathVariable String bookId, @RequestParam int rating) {
        try {
            ratingService.rateBook(bookId, rating);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // ¡Qué cobarde soy!
        }
    }

    @GetMapping("/{bookId}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return ResponseEntity.ok(book.getAverageRating());
    }
}
