package ir.keshavarzreza.simpleshopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Book;
import java.util.Collection;

@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
//    private BookRepository repository;

    @GetMapping("/{id}")
    public Book findById() {
        return null;
    }

    @GetMapping("/")
    public Collection<Book> findBooks() {
        return null;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Book updateBook(
      @PathVariable("id") final String id, @RequestBody final Book book) {
        return book;
    }
}