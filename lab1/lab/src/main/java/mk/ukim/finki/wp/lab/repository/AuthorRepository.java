package mk.ukim.finki.wp.lab.repository;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Author;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository {
    private final List<Author> authors = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (authors.isEmpty()) {
            authors.add(new Author((long) (Math.random() * 1000), "Ivo", "Andric", "Serbia", "Nobel laureate novelist."));
            authors.add(new Author((long) (Math.random() * 1000), "Ismail", "Kadare", "Albania", "Renowned Albanian novelist."));
            authors.add(new Author((long) (Math.random() * 1000), "Petre", "M. Andreevski", "North Macedonia", "Prominent Macedonian writer."));
        }
    }

    public List<Author> findAll() {
        return authors;
    }

    public Optional<Author> findById(Long id) {
        return authors.stream().filter(a -> a.getId().equals(id)).findFirst();
    }
}