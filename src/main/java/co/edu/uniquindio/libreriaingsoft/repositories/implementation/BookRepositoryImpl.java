package co.edu.uniquindio.libreriaingsoft.repositories.implementation;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

public class BookRepositoryImpl implements BookRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    /**
     * User search that looks for a single word or a phrase in the autor and title fields.
     * Checked the user input via regex.
     * @param keyword user input.
     * @return list of books.
     */
    @Override
    public List<Book> searchByKeywords(String keyword) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("title").regex(keyword,"i"),
                Criteria.where("author").regex(keyword, "i")
        ));
        return mongoTemplate.find(query, Book.class);
    }

    /**
     * A user can search for books by entering a keyword that contains any combination of author, title and ISBN. Pretty complicated imo.
     * @param keyword user input
     * @return list of books.
     */
    @Override
    public List<Book> searchBooksByKeywords(String keyword) {
        return List.of();
    }
}
