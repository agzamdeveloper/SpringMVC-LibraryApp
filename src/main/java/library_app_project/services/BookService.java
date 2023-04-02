package library_app_project.services;

import library_app_project.model.Book;
import library_app_project.model.Person;
import library_app_project.repositories.BooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {
    private final BooksRepository booksRepository;

    @Autowired
    public BookService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public List<Book> findAllWithPage(int page, int booksPerPage){
        return booksRepository.findAll(PageRequest.of(page, booksPerPage, Sort.by("publishDate"))).
                getContent();
    }

    public List<Book> findBookStartingWith(String startingWith){
        List<Book> books = booksRepository.findByNameStartingWith(startingWith);
        System.out.println(books);
        return books;
    }
    public Book findOne(int id){
        Optional<Book> findBook = booksRepository.findById(id);
        return findBook.orElse(null);
    }

    public Book checkUnique(String bookName){
        Book findBook = booksRepository.findByName(bookName);
        return findBook;
    }

    public Person joinPerson(int id){
        Book book = booksRepository.findById(id).orElse(null);
        return book.getOwner();
    }

    @Transactional
    public void assign(int bookId, Person person){
        Book book = booksRepository.findById(bookId).orElse(null);
        book.setOwner(person);
        book.setAssignAt(new Date());
    }

    @Transactional
    public void release(int id){
        Book book = booksRepository.findById(id).orElse(null);
        book.setOwner(null);
    }

    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }

    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }

    @Transactional
    public void delete(int id){
        booksRepository.deleteById(id);
    }

}
