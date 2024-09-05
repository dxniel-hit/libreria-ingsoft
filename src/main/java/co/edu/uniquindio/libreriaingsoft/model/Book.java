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

    // Clase interna para las reseñas
    @Data
    public static class Review {
        private String reviewer;
        private String comment;
    }

    public void addReview(Review review) {
        this.reviews.add(review);
    }
}

