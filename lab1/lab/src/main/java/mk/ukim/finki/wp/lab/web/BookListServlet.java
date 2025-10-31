package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.Book;
import mk.ukim.finki.wp.lab.service.BookService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.List;

public class BookListServlet extends HttpServlet {
    private final BookService bookService;
    private final SpringTemplateEngine springTemplateEngine;

    public BookListServlet(BookService bookService, SpringTemplateEngine springTemplateEngine) {
        this.bookService = bookService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Book> books = bookService.listAll();
        Book mostPopularBook = bookService.findMostPopularBook();
        long mostPopularBookCopies = mostPopularBook != null ? bookService.getReservationCountForBook(mostPopularBook.getTitle()) : 0;

        IWebExchange exchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(exchange);
        context.setVariable("books", books);
        context.setVariable("mostPopularBook", mostPopularBook);
        context.setVariable("mostPopularBookCopies", mostPopularBookCopies);

        springTemplateEngine.process("listBooks.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("bookTitle");
        Double rating = Double.valueOf(req.getParameter("rating"));

        List<Book> books = bookService.searchBooks(title, rating);
        Book mostPopularBook = bookService.findMostPopularBook();
        long mostPopularBookCopies = mostPopularBook != null ? bookService.getReservationCountForBook(mostPopularBook.getTitle()) : 0;
        
        IWebExchange exchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(exchange);
        context.setVariable("books", books);
        context.setVariable("mostPopularBook", mostPopularBook);
        context.setVariable("mostPopularBookCopies", mostPopularBookCopies);

        springTemplateEngine.process("listBooks.html", context, resp.getWriter());
    }
}