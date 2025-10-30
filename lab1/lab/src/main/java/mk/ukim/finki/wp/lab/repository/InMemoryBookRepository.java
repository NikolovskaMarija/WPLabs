package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository
public class InMemoryBookRepository implements BookRepository {
    @Override
    public List<Book> findAll() {
        return DataHolder.books;
    }

    @Override
    public List<Book> searchBooks(String text, Double rating) {
        List<Book> filteredBooks= new ArrayList<>();
        for(Book book: DataHolder.books){
            if(book.getTitle().contains(text) && book.getAverageRating()>=rating){
                filteredBooks.add(book);
            }
        }
        return filteredBooks;
    }
}
