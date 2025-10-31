package mk.ukim.finki.wp.lab.bootstrap;

import mk.ukim.finki.wp.lab.model.BookReservation;
import mk.ukim.finki.wp.lab.service.BookReservationService;
import mk.ukim.finki.wp.lab.service.BookService;
import mk.ukim.finki.wp.lab.web.BookListServlet;
import mk.ukim.finki.wp.lab.web.BookReservationServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Configuration
public class WebConfig {

    @Bean
    public ServletRegistrationBean<BookListServlet> bookListServlet(
            BookService bookService,
            SpringTemplateEngine springTemplateEngine) {

        BookListServlet servlet = new BookListServlet(bookService, springTemplateEngine);
        ServletRegistrationBean<BookListServlet> registrationBean =
                new ServletRegistrationBean<>(servlet);
        registrationBean.addUrlMappings("/", "", "/searchBooks");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }
    @Bean
    public ServletRegistrationBean<BookReservationServlet> bookReservationServlet(
            BookReservationService bookService,
            SpringTemplateEngine springTemplateEngine) {

        BookReservationServlet servlet = new BookReservationServlet(bookService, springTemplateEngine);
        ServletRegistrationBean<BookReservationServlet> registrationBean =
                new ServletRegistrationBean<>(servlet);
        registrationBean.addUrlMappings("/bookReservation");
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }
}