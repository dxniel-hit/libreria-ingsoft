package co.edu.uniquindio.libreriaingsoft.repositories;

import co.edu.uniquindio.libreriaingsoft.model.Book;

import java.util.List;


public interface BookRepositoryCustom {

    List<Book> searchByKeywords(String keyword);

    List<Book> searchBooksByKeywords(String keyword);
}
