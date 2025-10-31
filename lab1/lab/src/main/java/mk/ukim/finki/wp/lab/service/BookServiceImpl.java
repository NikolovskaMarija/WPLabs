package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import mk.ukim.finki.wp.lab.repository.BookReservationRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookReservationRepository bookReservationRepository;

    public BookServiceImpl(BookRepository bookRepository, BookReservationRepository bookReservationRepository) {
        this.bookRepository = bookRepository;
        this.bookReservationRepository = bookReservationRepository;
    }

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> searchBooks(String text, Double rating) {
        return bookRepository.searchBooks(text, rating);
    }

    @Override
    public Book findMostPopularBook() {
        List<Book> books = bookRepository.findAll();
        List<BookReservation> reservations = bookReservationRepository.findAll();
        Map<String, Long> bookReservationCounts = new HashMap<>();
        
        for (BookReservation reservation : reservations) {
            String bookTitle = reservation.getBookTitle();
            bookReservationCounts.put(bookTitle, 
                bookReservationCounts.getOrDefault(bookTitle, 0L) + reservation.getNumberOfCopies());
        }
        
        Book mostPopular = null;
        long maxReservations = 0;
        
        for (Book book : books) {
            long count = bookReservationCounts.getOrDefault(book.getTitle(), 0L);
            if (count > maxReservations) {
                maxReservations = count;
                mostPopular = book;
            }
        }
        
        return mostPopular;
    }

    @Override
    public long getReservationCountForBook(String bookTitle) {
        List<BookReservation> reservations = bookReservationRepository.findAll();
        long totalCount = 0;
        
        for (BookReservation reservation : reservations) {
            if (reservation.getBookTitle().equals(bookTitle)) {
                totalCount += reservation.getNumberOfCopies();
            }
        }
        
        return totalCount;
    }
}
