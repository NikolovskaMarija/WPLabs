package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
@Component
public class DataHolder {
    public static List<Book> books = new ArrayList<>();
    public static List<BookReservation> reservations = new ArrayList<>();

    @PostConstruct
    public void init (){
        books.add(new Book("kniga1", "zanr1", 2));
        books.add(new Book("kniga2", "zanr2", 3));
        books.add(new Book("kniga3", "zanr3", 5));
        books.add(new Book("kniga4", "zanr1", 2));
        books.add(new Book("kniga5", "zanr3", 5));
        books.add(new Book("kniga6", "zanr1", 4));
        books.add(new Book("kniga7", "zanr2", 1));
        books.add(new Book("kniga8", "zanr3", 2));
        books.add(new Book("kniga9", "zanr1", 5));
        books.add(new Book("kniga10", "zanr1", 2));
    }
}
