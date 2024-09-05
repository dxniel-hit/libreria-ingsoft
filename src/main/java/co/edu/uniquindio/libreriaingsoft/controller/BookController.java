package co.edu.uniquindio.libreriaingsoft.controller;

import co.edu.uniquindio.libreriaingsoft.model.Book;
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
    private final RatingService ratingService;

    public BookController(BookService bookService, RatingService ratingService) {
        this.bookService = bookService;
        this.ratingService = ratingService;
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
}
