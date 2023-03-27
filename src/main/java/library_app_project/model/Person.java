package library_app_project.model;


import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "People")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Please enter the name")
    @Column(name = "person_name")
    private String name;

    @Min(value = 1901, message = "The year of birth person must be more than 1900")
    @Column(name = "birth_date")
    private int birth_date;

    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person(){}

    public Person(String name, int birth_date, List<Book> books) {
        this.name = name;
        this.birth_date = birth_date;
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() { return name;}

    public void setName(String name) {
        this.name = name;
    }

    public int getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(int birth_date) {
        this.birth_date = birth_date;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth_date=" + birth_date +
                '}';
    }
}
