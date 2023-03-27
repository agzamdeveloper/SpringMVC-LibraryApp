package library_app_project.dao;

import library_app_project.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper <Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setBook_name(rs.getString("book_name"));
        book.setAuthor(rs.getString("author"));
        book.setPublish_date(rs.getInt("publish_date"));

        return book;
    }
}
