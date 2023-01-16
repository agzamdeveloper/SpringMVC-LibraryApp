package library_app_project.dao;

import library_app_project.model.Book;
import library_app_project.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index(){
        return jdbcTemplate.query("SELECT * FROM Book", new BookMapper());
    }

    public Optional<Book> checkUnique(String name){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_name=?",new Object[]{name},
                new BookMapper()).stream().findAny();
    }

    public Book show(String name){
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_name=?", new Object[]{name},
                new BookMapper()).stream().findAny().orElse(null);
    }

    public Person joinBook(String book_name){
        return jdbcTemplate.query("SELECT * FROM People LEFT JOIN book b on people.person_id = b.person_id WHERE book_name=?",
                new Object[]{book_name}, new PersonMapper()).stream().findAny().orElse(null);
    }

    public void save(Book newBook){
        jdbcTemplate.update("INSERT INTO Book(book_name, author, publish_date) VALUES (?,?,?)",
                newBook.getBook_name(), newBook.getAuthor(), newBook.getPublish_date());
    }

    public void assign(String book_name, int id){
        jdbcTemplate.update("UPDATE Book SET person_id=? WHERE book_name=?", id, book_name);
    }

    public void edit(Book editBook, String book_name){
        jdbcTemplate.update("UPDATE Book SET book_name=?, author=?, publish_date=? WHERE book_name=?",
                editBook.getBook_name(), editBook.getAuthor(), editBook.getPublish_date(), book_name);
    }

    public void delete(String book_name){
        jdbcTemplate.update("DELETE FROM Book WHERE book_name=?", book_name);
    }
}
