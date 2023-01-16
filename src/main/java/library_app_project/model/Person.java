package library_app_project.model;

import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;


public class Person {
    private int id;
    @NotEmpty(message = "Please enter the name")
    private String name;

    @Min(value = 1901, message = "The year of birth person must be more than 1900")
    private int birth_date;

    public Person(){}

    public Person(int id, String name, int birth_date) {
        this.id = id;
        this.name = name;
        this.birth_date = birth_date;
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
}
