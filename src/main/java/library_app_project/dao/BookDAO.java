package library_app_project.dao;

import library_app_project.model.Book;
import library_app_project.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class BookDAO {
    private final SessionFactory sessionFactory;

    @Autowired
    public BookDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book checkUnique(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b where b.book_name = :name", Book.class).
                setParameter("name", name).stream().findAny().orElse(null);
    }

    @Transactional(readOnly = true)
    public Book show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class,id);
    }

    @Transactional
    public Person joinBook(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class, id);
        Person owner = book.getOwner();
        return owner;
    }

    @Transactional
    public void save(Book newBook){
        Session session = sessionFactory.getCurrentSession();
        session.save(newBook);
    }

    @Transactional
    public void assign(int BookId, int PersonId){
        Session session = sessionFactory.getCurrentSession();
        Book editBook = session.get(Book.class, BookId);
        Person addPerson = session.get(Person.class, PersonId);
        System.out.println(addPerson);
        editBook.setOwner(addPerson);
        addPerson.getBooks().add(editBook);
    }

    @Transactional
    public void release(int id){
        Session session = sessionFactory.getCurrentSession();
        Book releaseBook = session.get(Book.class, id);
        List<Book> books = releaseBook.getOwner().getBooks();
        releaseBook.setOwner(null);
        books.remove(releaseBook);
    }

    @Transactional
    public void edit(Book book, int id){
        Session session = sessionFactory.getCurrentSession();
        Book editedBook = session.get(Book.class, id);

        editedBook.setBook_name(book.getBook_name());
        editedBook.setAuthor(book.getAuthor());
        editedBook.setPublish_date(book.getPublish_date());
    }

    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Book deleteBook = session.get(Book.class, id);
        List<Book> books = deleteBook.getOwner().getBooks();
        books.remove(deleteBook);
        session.remove(deleteBook);
    }
}
