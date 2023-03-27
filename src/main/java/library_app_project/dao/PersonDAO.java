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

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person checkUnique(String name){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select p from Person p where p.name = :name", Person.class).
                setParameter("name", name).stream().findAny().orElse(null);
    }

    @Transactional(readOnly = true)
    public Person show(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class, id);
    }

    @Transactional
    public List<Book> joinPerson(int id){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class, id);
        List<Book> books = person.getBooks();
        System.out.println(books);
        return books;
    }

    @Transactional
    public void save(Person newPerson){
        Session session = sessionFactory.getCurrentSession();
        session.save(newPerson);
    }

    @Transactional
    public void edit(Person editPerson, int id){
        Session session = sessionFactory.getCurrentSession();
        Person personToBeUpdated = session.get(Person.class, id);

        personToBeUpdated.setName(editPerson.getName());
        personToBeUpdated.setBirth_date(editPerson.getBirth_date());
    }

    @Transactional
    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        Person deletePerson = session.get(Person.class, id);
        List<Book> books = deletePerson.getBooks();
        for (Book book: books) {
            book.setOwner(null);
        }
        session.remove(deletePerson);
    }
}
