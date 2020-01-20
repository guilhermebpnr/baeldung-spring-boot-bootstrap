package dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp.repository;

import dev.guilhermebpnr.baeldung.tutorials.springbootbootstratp.model.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<Book> findByTitle(String title);
}
