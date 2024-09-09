package co.edu.uniquindio.libreriaingsoft.controller;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import co.edu.uniquindio.libreriaingsoft.services.BookService;
import co.edu.uniquindio.libreriaingsoft.services.RatingService;
import co.edu.uniquindio.libreriaingsoft.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController // Guarantees that a JSON will be return every query.
@RequestMapping("/books")
public class BookController {

    // Attributes and a constructor.

    @Autowired
    private final BookService bookService;
    @Autowired
    private final RatingService ratingService;
    @Autowired
    private final BookRepository bookRepository;
    @Autowired
    private UserService userService;

    public BookController(BookService bookService, RatingService ratingService, BookRepository bookRepository) {
        this.bookService = bookService;
        this.ratingService = ratingService;
        this.bookRepository = bookRepository;
    }

    /**
     * Given a keyword, the keyword will look in the author and title attributes of the Book clas.
     * One of the two options the user has to search a book.
     * @param keyword String
     * @param page int
     * @param size int
     * @return content and total pages of books.
     */
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchBooksByTitleAndAuthor(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Book> bookPage = bookService.searchBooksByTitleAndAuthor(keyword, page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("content", bookPage.getContent());
        response.put("totalPages", bookPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    /**
     * Given a keyword, the keyword will look in the author, title or isbn attributes of the Book class.
     * Second option an usar has to search a book.
     * @param keyword String
     * @return content and total pages of books.
     */
    @GetMapping("/searchAv")
    public ResponseEntity<Map<String, Object>> searchBooksByTitleAuthorOrIsbn(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {

        Page<Book> bookPage = bookService.searchBooksByTitleAuthorOrIsbn(keyword, page, size);
        Map<String, Object> response = new HashMap<>();
        response.put("content", bookPage.getContent());
        response.put("totalPages", bookPage.getTotalPages());
        return ResponseEntity.ok(response);
    }

    /**
     * Given a book id and a review (username and text), the review will be held on the collection of books.
     * Example JSON:
     * {
     *   "reviewer": "user123",
     *   "comment": "Why the fuck this one works but rate does not"
     * }
     * @param bookId String
     * @param review Review
     * @return HTTPStatus.OK
     */
    @PostMapping("/{bookId}/review")
    public ResponseEntity<Void> addReview(@PathVariable String bookId, @RequestBody Book.Review review) {
        bookService.addReviewToBook(bookId, review);
        return ResponseEntity.ok().build();
    }

    /**
     * Gets the reviews a book has by id.
     * @param bookId id of the book.
     * @return a list of reviews the book has.
     */
    @GetMapping("/{bookId}/reviews")
    public List<Book.Review> getReviews(@PathVariable String bookId) {
        return bookService.getReviewsForBook(bookId);
    }

    /**
     * Given a book id and a Rating that contains an int from 1 to 5, the rating will be added to the books' collection.
     * Example JSON:
     * {
     *   "userId": "66dc9a95308c0f61cc9fdeff",
     *   "rating": 5
     * }
     * @param bookId String
     * @param rating Rating
     * @return HTTPStatus OK or IllegalArgumentException
     */
    @PostMapping("/{bookId}/rate")
    public ResponseEntity<?> rateBook(@PathVariable String bookId, @RequestBody Book.Rating rating) {
        try {
            String userId = rating.getUserId();
            int ratingValue = rating.getRating();

            // Validate if user exists
            if (!userService.isUserRegistered(userId)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not registered.");
            }

            // Validate rating
            if (ratingValue < 1 || ratingValue > 5) {
                return ResponseEntity.badRequest().body("Rating must be between 1 and 5.");
            }

            // Proceed with adding the rating
            ratingService.rateBook(bookId, ratingValue);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    /**
     * Given a book id, the front will need to update the book average rating everytime a rating is added to the book.
     * This may collapse the database, very insecure right now!
     * @param bookId String
     * @return IllegalArgumentException or HTTPStatus.OK
     */
    @GetMapping("/{bookId}/rating")
    public ResponseEntity<Double> getAverageRating(@PathVariable String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book not found"));
        return ResponseEntity.ok(book.getAverageRating());
    }

    /**
     * Gets all books.
     * @return a ResponseEntity with all the contained books
     */
    @GetMapping
    public ResponseEntity<?> getAllBooksPaginated(@RequestParam(defaultValue = "0") int page,
                                                  @RequestParam(defaultValue = "20") int size) {
        return bookService.findAllBooksPaginated(page, size);
    }
}
