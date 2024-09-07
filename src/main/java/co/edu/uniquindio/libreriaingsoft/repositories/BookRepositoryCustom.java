package co.edu.uniquindio.libreriaingsoft.repositories;

import co.edu.uniquindio.libreriaingsoft.model.Book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookRepositoryCustom {

    Page<Book> searchByTitleAndAuthor(String keyword, Pageable pageable);

    List<Book> searchByTitleAuthorOrIsbn(String keyword);
}
