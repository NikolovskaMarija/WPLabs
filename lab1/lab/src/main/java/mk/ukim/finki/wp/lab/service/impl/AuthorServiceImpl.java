package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.service.AuthorService;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;
    private final BookService bookService;

    public AuthorServiceImpl(AuthorRepository authorRepository, BookService bookService) {
        this.authorRepository = authorRepository;
        this.bookService = bookService;
    }

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Author create(String name, String surname, String country, String biography) {
        Author author = new Author(null, name, surname, country, biography, new ArrayList<>());
        return authorRepository.save(author);
    }

    @Override
    public Author update(Long id, String name, String surname, String country, String biography) {
        Author updated = findById(id).get();

        updated.setName(name);
        updated.setSurname(surname);
        updated.setCountry(country);
        updated.setBiography(biography);
        return authorRepository.save(updated);
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
