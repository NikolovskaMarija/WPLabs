package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DataHolder {
    public static List<Book> books = new ArrayList<>();
    public static List<BookReservation> reservations = new ArrayList<>();
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    public DataHolder(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @PostConstruct
    public void init (){
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) {
            authors.add(new Author("Ivo", "Andric", "Serbia", "Nobel laureate novelist.", new ArrayList<>()));
            authors.add(new Author("Ismail", "Kadare", "Albania", "Renowned Albanian novelist.", new ArrayList<>()));
            authors.add(new Author("Petre", "M. Andreevski", "North Macedonia", "Prominent Macedonian writer.", new ArrayList<>()));
            authorRepository.saveAllAndFlush(authors);
        }
        books.add(new Book("kniga1", "zanr1", 2, authors.get(0)));
        books.add(new Book("kniga2", "zanr2", 3, authors.get(1)));
        books.add(new Book("kniga3", "zanr3", 6, authors.get(2)));
        books.add(new Book("kniga4", "zanr1", 2, authors.get(0)));
        books.add(new Book("kniga5", "zanr3", 5, authors.get(1)));
        books.add(new Book("kniga6", "zanr1", 4, authors.get(2)));
        books.add(new Book("kniga7", "zanr2", 1, authors.get(0)));
        books.add(new Book("kniga8", "zanr3", 2, authors.get(1)));
        books.add(new Book("kniga9", "zanr1", 5, authors.get(2)));
        books.add(new Book("kniga10", "zanr1", 2, authors.get(0)));
        bookRepository.saveAllAndFlush(books);
    }
}
