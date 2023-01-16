package library_app_project.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Book {
    private int person_id;
    @NotEmpty(message = "Please enter name of the book")
    private String book_name;
    @NotEmpty(message = "Please enter name of author")
    private String author;
    @Min(value = 1, message = "The publish year of book must be more than zero")
    private int publish_date;

    public Book() {}

    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
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
}
