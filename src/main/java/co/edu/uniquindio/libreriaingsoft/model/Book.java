package co.edu.uniquindio.libreriaingsoft.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private String description;
    private double price;
    private int stock;
    private List<Integer> ratings;
    private double averageRating;
    private List<String> userReviews;

    public Book() {
    }

    public Book(String id, String title, String author, String isbn, String category, String description, double price, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public void addRating(int rating) {
        this.ratings.add(rating);
        updateAverageRating();
    }

    private void updateAverageRating() {
        if (ratings.isEmpty()) {
            this.averageRating = 0;
        } else {
            this.averageRating = ratings.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0);
        }
    }

    public void addReview(String review) {
        this.userReviews.add(review);
    }
}

