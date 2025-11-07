package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DataHolder {
    public static List<Book> books = new ArrayList<>();
    public static List<BookReservation> reservations = new ArrayList<>();
    private final AuthorRepository authorRepository;

    public DataHolder(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @PostConstruct
    public void init (){
        List<Author> authors = authorRepository.findAll();
        if (authors.isEmpty()) return;
        books.add(new Book((long)(Math.random()*100000), "kniga1", "zanr1", 2, authors.get(0)));
        books.add(new Book((long)(Math.random()*100000), "kniga2", "zanr2", 3, authors.get(1)));
        books.add(new Book((long)(Math.random()*100000), "kniga3", "zanr3", 6, authors.get(2)));
        books.add(new Book((long)(Math.random()*100000), "kniga4", "zanr1", 2, authors.get(0)));
        books.add(new Book((long)(Math.random()*100000), "kniga5", "zanr3", 5, authors.get(1)));
        books.add(new Book((long)(Math.random()*100000), "kniga6", "zanr1", 4, authors.get(2)));
        books.add(new Book((long)(Math.random()*100000), "kniga7", "zanr2", 1, authors.get(0)));
        books.add(new Book((long)(Math.random()*100000), "kniga8", "zanr3", 2, authors.get(1)));
        books.add(new Book((long)(Math.random()*100000), "kniga9", "zanr1", 5, authors.get(2)));
        books.add(new Book((long)(Math.random()*100000), "kniga10", "zanr1", 2, authors.get(0)));
    }
}
