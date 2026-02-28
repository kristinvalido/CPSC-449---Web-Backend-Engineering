package com.example.webbackend.controller;

import com.example.webbackend.entity.Book;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api")
public class BookController {

    private List<Book> books = new ArrayList<>();

    private Long id = 1L;

    public BookController() {
        // add some books
        books.add(new Book(id++, "jAVA", "Kennedy", 20.0));
        books.add(new Book(id++, "Spring", "Bryan", 25.0));
        books.add(new Book(id++, "Spring Book", "Bryan", 22.0));
        books.add(new Book(id++, "Effective Java", "Seth", 21.0));
        books.add(new Book(id++, "Java Concurrency", "Kennedy", 23.0));
        books.add(new Book(id++, "Design Patterns", "Kennedy",24.0));
        books.add(new Book(id++, "Head First Java", "Seth", 25.0));
        books.add(new Book(id++, "Spring in Action", "Seth", 26.0));
        books.add(new Book(id++, "Architecture", "Kennedy", 27.0));
        books.add(new Book(id++, "Refactoring", "Jones", 28.0));
        books.add(new Book(id++, "The Programmer", "Jones", 29.0));
        books.add(new Book(id++, "JavaScript for Dummies", "Jones", 30.0));
        books.add(new Book(id++, "JavaScript for Dummies", "Seth", 31.0));
        books.add(new Book(id++, "Python for Dummies", "Kennedy",32.0));
        books.add(new Book(id++, "C++ for Dummies", "Bryan", 33.0));
    }

    //get all books -/ api / books
    @GetMapping ("/books")
    public List<Book> getBooks() {
        return books;
    }

    //get book id
    @GetMapping("/books/{id}")
    public Book getBook(@PathVariable Long id){
        return books.stream().filter(book -> book.getId().equals(id))
                .findFirst().orElse(null);
    }

    // create a new book
    @PostMapping("/books")
    public List<Book> createBook(@RequestBody Book book) {
        books.add(book);
        return books;
    }

    //search by title
    @GetMapping ("/books/search")
    public List<Book> searchByTitle(
            @RequestParam(required = false, defaultValue = "") String title
    ){
        if(title.isEmpty()) {
            return books;
        }
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    //price range
    @GetMapping("/books/price-range")
    public List<Book> getBooksByPrice(
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice
    ){
        return books.stream()
                .filter(book -> {
                    boolean min = minPrice == null || book.getPrice() >= minPrice;
                    boolean max = maxPrice == null || book.getPrice() <= maxPrice;
                    return min && max;
                }).collect(Collectors.toList());
    }

    //sort
    @GetMapping("/books/sorted")
    public List<Book> getSortedBooks(
            @RequestParam(required = false, defaultValue = "title") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order
    ){
        Comparator<Book> comparator;
        switch(sortBy.toLowerCase()) {
            case "author":
                comparator = Comparator.comparing(Book::getAuthor);
                break;
            case "title":
                comparator = Comparator.comparing(Book::getTitle);
            default:
                comparator = Comparator.comparing(Book::getTitle);
                break;
        }
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return books.stream().sorted(comparator)
                .collect(Collectors.toList());
    }

    //replace existing book
    @PutMapping ("/books/{id}")
    public List<Book> newBook(@PathVariable Long id,
                              @RequestBody Book replaceBook) {
        books.stream().filter(book -> book.getId().equals(id))
                .findFirst()
                .ifPresent(book -> {
                    book.setTitle(replaceBook.getTitle());
                    book.setAuthor(replaceBook.getAuthor());
                    book.setPrice(replaceBook.getPrice());
                    return;
                });
        return books;
    }

    //partially updating book
    @PatchMapping ("/books/{id}")
    public List<Book> partialUpdate(
            @PathVariable Long id,
            @RequestBody Book partialChange) {
        for (Book book : books) {
            if (book.getId().equals(id)) {

                if (partialChange.getTitle() != null) {
                    book.setTitle(partialChange.getTitle());
                }
                if (partialChange.getAuthor() != null) {
                    book.setAuthor(partialChange.getAuthor());
                }
                if (partialChange.getId() != null) {
                    book.setId(partialChange.getId());
                }
                if (partialChange.getPrice() != null) {
                    book.setPrice(partialChange.getPrice());
                }
                break;
            }
        }
        return books;
    }

    //delete booking
    @DeleteMapping ("/books/{id}")
    public List<Book> deleteBook(
            @PathVariable Long id,
            @RequestBody Book deleteBook
    ){
        books.removeIf( book -> book.getId().equals(id));
        return books.stream().toList();

    }

    //GET endpoint with pagination
    // this means it shows the books in pages, so page 0, books 3, would be the first page with the first 3 books
    @GetMapping("/books/page")
    public List<Book> pageSize(
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int numBooks
    ){
        int start = page * numBooks; // so if it's page 0 and numBooks is 5, then start is 0
        int end = start + numBooks; // end is 0 + 5 = 5
        // book.get(0) is first book
        return books.subList(start,end);
    }

    // ADVANCED get with pagination and filtering and sorting
    // filter first, then sort, then pagination
    // just filter by author and price, then sort by  title, not gonna ask order
    @GetMapping ("/books/advanced")
    public List<Book> advancedSearch(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) int page,
            @RequestParam(required = false) int numBooks
            ){
        Stream<Book> filter = books.stream();
        int start = page * numBooks;
        int end = start + numBooks;
        if (author != null){
            filter = books.stream().filter(book-> book.getAuthor().equals(author));
            filter = filter.sorted(Comparator.comparing(Book::getTitle));

        }
        if (maxPrice != null){
            filter = books.stream().filter(book -> book.getPrice() <= maxPrice );
            filter = filter.sorted(Comparator.comparing(Book::getTitle));
        }

        return filter.toList().subList(start,end);
    }
}

