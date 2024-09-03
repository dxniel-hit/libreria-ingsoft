package co.edu.uniquindio.libreriaingsoft.controller;

import co.edu.uniquindio.libreriaingsoft.services.BookService;
import co.edu.uniquindio.libreriaingsoft.services.RatingService;
import co.edu.uniquindio.libreriaingsoft.services.ReviewService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final RatingService ratingService;
    private final ReviewService reviewService;

    public BookController(BookService bookService, RatingService ratingService, ReviewService reviewService) {
        this.bookService = bookService;
        this.ratingService = ratingService;
        this.reviewService = reviewService;
    }

    @GetMapping("/searchBooks")
    public String searchBooks(@RequestParam String query) {
        return bookService.searchBooks(query);
    }

    @PostMapping("/{bookId}/rate")
    public void rateBook(@PathVariable String bookId, @RequestParam int rating) {
        ratingService.rateBook(bookId, rating);
    }

    @PostMapping("/{bookId}/review")
    public void reviewBook (@PathVariable String bookId, @RequestParam String review) {
        reviewService.reviewBook(bookId, review);
    }


}
