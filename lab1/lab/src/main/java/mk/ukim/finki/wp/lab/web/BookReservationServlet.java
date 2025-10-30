package mk.ukim.finki.wp.lab.web;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;

public class BookReservationServlet extends HttpServlet {
    private final BookReservationService bookService;
    private final SpringTemplateEngine springTemplateEngine;

    public BookReservationServlet(BookReservationService bookService, SpringTemplateEngine springTemplateEngine) {
        this.bookService = bookService;
        this.springTemplateEngine = springTemplateEngine;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("bookTitle");
        String readerName = req.getParameter("readerName");
        String readerAddress = req.getParameter("readerAddress");
        Integer numCopies = Integer.valueOf(req.getParameter("numCopies"));

        BookReservation reservation = bookService.placeReservation(title, readerName, readerAddress, numCopies);

        IWebExchange exchange = JakartaServletWebApplication
                .buildApplication(getServletContext())
                .buildExchange(req, resp);

        WebContext context = new WebContext(exchange);
        context.setVariable("reservation", reservation);
        context.setVariable("ipAddress", req.getRemoteAddr());

        springTemplateEngine.process("reservationConfirmation.html", context, resp.getWriter());
    }
}