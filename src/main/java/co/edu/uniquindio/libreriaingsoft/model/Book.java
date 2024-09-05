package co.edu.uniquindio.libreriaingsoft.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    @Field(name = "ISBN")
    private String isbn;

    @Field(name = "Book-Title")
    private String bookTitle;

    @Field(name = "Book-Author")
    private String bookAuthor;

    @Field(name = "Year-Of-Publication")
    private String yearOfPublication;

    @Field(name = "Image-URL-L")
    private String imageUrl;

    @Field(name = "Reviews")
    private List<Review> reviews = new ArrayList<>();

    private List<Integer> ratings = new ArrayList<>();

    @Field(name = "Average-Rating")
    private double averageRating = 0.0;  // Average rating


    // Clase interna para las reseñas
    @Data
    public static class Review {
        private String reviewer;
        private String comment;
    }

    // Se usa en BookService.
    public void addReview(Review review) {
        this.reviews.add(review);
    }

    /**
     * Añade una calificación a la lista de calificaciones.
     * Actualiza el promedio de calificación del libro.
     * Si se añaden 100.000 calificaciones no será muy costoso computacionalmente, ¡qué peligro!
     * @param rating calificación del usuario
     */
    public void addRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.ratings.add(rating);
        updateAverageRating();
    }

    private void updateAverageRating() {
        if (ratings.isEmpty()) {
            this.averageRating = 0.0;
        } else {
            this.averageRating = ratings.stream().mapToInt(Integer::intValue).average().orElse(0.0);
        }
    }
}

