package library_app_project.services;

import library_app_project.model.Book;
import library_app_project.model.Person;
import library_app_project.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        Optional<Person> findPerson = peopleRepository.findById(id);
        return findPerson.orElse(null);
    }

    public Person checkUnique(String name){
        Optional<Person> findPerson= Optional.ofNullable(peopleRepository.findByName(name));
        return findPerson.orElse(null);
    }

    public List<Book> joinBooks(int id){
        Person person = peopleRepository.findById(id).orElse(null);
        List<Book> books = person.getBooks();
        System.out.println(books);
        return books;
    }

    @Transactional
    public void save(Person person){
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson){
        updatedPerson.setId(id);
        peopleRepository.save(updatedPerson);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }
}
