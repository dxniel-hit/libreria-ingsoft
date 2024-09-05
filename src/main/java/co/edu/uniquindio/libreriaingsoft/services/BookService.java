package co.edu.uniquindio.libreriaingsoft.services;


import co.edu.uniquindio.libreriaingsoft.model.Book;
import co.edu.uniquindio.libreriaingsoft.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {

    @Autowired
    public BookRepository bookRepository;

    public List<Book> searchBooksByTitleAndAuthor(String query) {
        return bookRepository.searchByTitleAndAuthor(query);
    }

}
