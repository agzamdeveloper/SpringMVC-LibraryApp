package library_app_project.dao;

import library_app_project.model.Book;
import library_app_project.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index(){
        return jdbcTemplate.query("SELECT * FROM People", new PersonMapper());
    }

    public Optional<Person> checkUnique(String name){
        return jdbcTemplate.query("SELECT * FROM People WHERE person_name=?",new Object[]{name},
                new PersonMapper()).stream().findAny();
    }
    public Person show(String name){
        return jdbcTemplate.query("SELECT * FROM People WHERE person_name=?", new Object[]{name},
                new PersonMapper()).stream().findAny().orElse(null);
    }

    public List<Book> joinPerson(String person_name){
        return jdbcTemplate.query("SELECT * FROM People LEFT JOIN book b on people.person_id = b.person_id WHERE person_name=?",
                new Object[]{person_name}, new BookMapper());
    }

    public void save(Person newPerson){
        jdbcTemplate.update("INSERT INTO People(person_name, birth_date) VALUES (?,?)",
                newPerson.getName(), newPerson.getBirth_date());
    }

    public void edit(Person editPerson, String name){
        jdbcTemplate.update("UPDATE People SET person_name=?, birth_date=? WHERE person_name=?",
                editPerson.getName(), editPerson.getBirth_date(), name);
    }

    public void delete(String name){
        jdbcTemplate.update("DELETE FROM People WHERE person_name=?", name);
    }
}
