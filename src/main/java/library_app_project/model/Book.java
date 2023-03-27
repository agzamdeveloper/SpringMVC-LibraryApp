package library_app_project.model;

import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please enter name of the book")
    @Column(name = "book_name")
    private String book_name;
    @NotEmpty(message = "Please enter name of author")
    @Column(name = "author")
    private String author;
    @Min(value = 1, message = "The publish year of book must be more than zero")
    @Column(name = "publish_date")
    private int publish_date;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {}

    public Book(String book_name, String author, int publish_date, Person owner) {
        this.book_name = book_name;
        this.author = author;
        this.publish_date = publish_date;
        this.owner = owner;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublish_date() {
        return publish_date;
    }

    public void setPublish_date(int publish_date) {
        this.publish_date = publish_date;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", book_name='" + book_name + '\'' +
                ", author='" + author + '\'' +
                ", publish_date=" + publish_date +
                '}';
    }
}
