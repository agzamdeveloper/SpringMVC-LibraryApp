package library_app_project.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.Period;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Entity
@Table(name = "Book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Please enter name of the book")
    @Column(name = "book_name")
    private String name;
    @NotEmpty(message = "Please enter name of author")
    @Column(name = "author")
    private String author;
    @Min(value = 1, message = "The publish year of book must be more than zero")
    @Column(name = "publish_date")
    private int publishDate;

    @Column(name = "assign_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date assignAt;

    @Transient
    private Boolean overdue;

    @Transient
    private long timed;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;

    public Book() {}

    public Book(String book_name, String author, int publish_date, Person owner) {
        this.name = book_name;
        this.author = author;
        this.publishDate = publish_date;
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBook_name() {
        return name;
    }

    public void setBook_name(String book_name) {
        this.name = book_name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublish_date() {
        return publishDate;
    }

    public void setPublish_date(int publish_date) {
        this.publishDate = publish_date;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Date getAssignAt() {
        return assignAt;
    }

    public void setAssignAt(Date assignAt) {
        this.assignAt = assignAt;
    }

    public Boolean getOverdue(){
        if(assignAt != null){
            long diffMs = new Date().getTime() - assignAt.getTime();
            long diff = TimeUnit.DAYS.convert(diffMs, TimeUnit.MILLISECONDS);
            if(diff > 10){
                return true;
            }else{
                return false;
            }
        }else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", book_name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", publish_date=" + publishDate +
                '}';
    }
}
