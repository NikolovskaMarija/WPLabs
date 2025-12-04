package mk.ukim.finki.wp.lab.service.impl;

import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.model.Author;
import mk.ukim.finki.wp.lab.repository.BookRepository;
import mk.ukim.finki.wp.lab.repository.BookReservationRepository;
import mk.ukim.finki.wp.lab.repository.AuthorRepository;
import mk.ukim.finki.wp.lab.service.BookService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookReservationRepository bookReservationRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, BookReservationRepository bookReservationRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.bookReservationRepository = bookReservationRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> listAll() {
        return bookRepository.findAll().stream()
                .filter(book -> book.getAuthor() != null)
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> searchBooks(String text, Double rating) {
        return bookRepository.findAllByTitleContainingOrAverageRatingGreaterThan(text, rating);
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

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public Book create(String title, String genre, Double averageRating, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Book book = new Book(title, genre, averageRating, author);
        return bookRepository.save(book);
    }

    @Override
    public Book update(Long id, String title, String genre, Double averageRating, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new IllegalArgumentException("Author not found"));
        Book updated = findById(id).get();
        updated.setTitle(title);
        updated.setGenre(genre);
        updated.setAverageRating(averageRating);
        updated.setAuthor(author);

        return bookRepository.save(updated);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
