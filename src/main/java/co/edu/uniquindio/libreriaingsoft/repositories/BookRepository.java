package co.edu.uniquindio.libreriaingsoft.repositories;

import co.edu.uniquindio.libreriaingsoft.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

}
