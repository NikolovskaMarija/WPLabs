package mk.ukim.finki.wp.lab.repository;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public Optional<Book> findById(Long id) {
        return DataHolder.books.stream().filter(b -> b.getId().equals(id)).findFirst();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId((long)(Math.random() * 100000));
        }
        DataHolder.books.add(book);
        return book;
    }

    @Override
    public Book update(Long id, Book updated) {
        this.deleteById(id);
        updated.setId(id);
        DataHolder.books.add(updated);
        return updated;
    }

    @Override
    public void deleteById(Long id) {
        DataHolder.books.removeIf(b -> b.getId().equals(id));
    }
}
