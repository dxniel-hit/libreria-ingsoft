package co.edu.uniquindio.libreriaingsoft.repositories.implementation;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
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
    public List<Book> searchByTitleAndAuthor(String keyword) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("Book-Title").regex(keyword, "i"), // Match MongoDB field name
                Criteria.where("Book-Author").regex(keyword, "i") // Match MongoDB field name
        ));
        return mongoTemplate.find(query, Book.class);
    }

    @Override
    public List<Book> searchByTitleAuthorOrIsbn(String keyword) {
        // Tú puedes Javi!
        return null;
    }

}
