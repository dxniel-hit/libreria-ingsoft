package co.edu.uniquindio.libreriaingsoft.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

//    private List<Integer> ratings;
//    private double averageRating;
//    private List<String> userReviews;

//    public void addRating(int rating) {
//        this.ratings.add(rating);
//        updateAverageRating();
//    }
//
//    private void updateAverageRating() {
//        if (ratings.isEmpty()) {
//            this.averageRating = 0;
//        } else {
//            this.averageRating = ratings.stream()
//                    .mapToInt(Integer::intValue)
//                    .average()
//                    .orElse(0);
//        }
//    }
//
//    public void addReview(String review) {
//        this.userReviews.add(review);
//    }
}

