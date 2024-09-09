package co.edu.uniquindio.libreriaingsoft.repositories.implementation;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
    public Page<Book> searchByTitleAndAuthor(String keyword, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("Book-Title").regex(keyword, "i"),
                Criteria.where("Book-Author").regex(keyword, "i")
        ));
        long count = mongoTemplate.count(query, Book.class);
        List<Book> books = mongoTemplate.find(query.with(pageable), Book.class);
        return new PageImpl<>(books, pageable, count);
    }



    @Override
    public Page<Book> searchByTitleAuthorOrIsbn(String keyword, Pageable pageable) {
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("Book-Title").regex(keyword, "i"),
                Criteria.where("Book-Author").regex(keyword, "i"),
                Criteria.where("ISBN").regex(keyword, "i")
        ));
        long count = mongoTemplate.count(query, Book.class);
        List<Book> books = mongoTemplate.find(query.with(pageable), Book.class);
        return new PageImpl<>(books, pageable, count);
    }

}
