package com.example.bankapplication.controller;

import com.example.bankapplication.dto.Book;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books Finder", description = "The APIs to search for all or specific books")
public class BookController {

    List<Book> books = new ArrayList<>();

    public BookController() {
        initializeBooks();
    }

    private void initializeBooks() {
        books.addAll(List.of(
                new Book(1,"Maths Pro","Author 1","Maths",5),
                new Book(2,"Science Pro","Author 2","Science",5),
                new Book(3,"English Pro","Author 3","English",4),
                new Book(4,"Computer Pro","Author 4","Computer",1),
                new Book(5,"Geo Pro","Author 5","Geo",3),
                new Book(6,"Eco Pro","Author 6","Eco",2)
        ));
    }

    @GetMapping("/all-books")
    public List<Book> getAllBooks() {
        return books;
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable @Min(value =1)long id) {
        return books.stream()
                .filter(b -> b.getId() == id)
                .findFirst()
                .orElse(null);
    }

    @GetMapping
    public List<Book> getBooksByCategory(@RequestParam(required = false) String category) {

        if (category == null) {
            return books;
        }
        return books.stream()
                .filter(b -> b.getCategory().equalsIgnoreCase(category))
                .toList();
    }

    @PostMapping
    public void createBook(@RequestBody Book book) {

        boolean isNewBook = books.stream()
                .noneMatch(b -> b.getTitle().equalsIgnoreCase(book.getTitle()));

        if (isNewBook) {
            books.add(book);
        }
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable @Min(value =1) long id, @RequestBody Book book) {
        for(int i  = 0; i < books.size(); i++) {
            if(books.get(i).getId() == id) {
                books.set(i, book);
                return;
            }
        }
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable @Min(value =1) long id) {
        books.removeIf(b -> b.getId() == id);
    }
}
