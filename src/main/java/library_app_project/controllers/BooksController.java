package library_app_project.controllers;

import library_app_project.dao.BookDAO;
import library_app_project.dao.PersonDAO;
import library_app_project.model.Book;
import library_app_project.model.Person;
import library_app_project.util.BookValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookDAO bookDAO;

    private final PersonDAO personDAO;
    private final BookValidator bookValidator;

    public BooksController(BookDAO bookDAO, PersonDAO personDAO, BookValidator bookValidator) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("books", bookDAO.index());
        return "books/index";
    }

    @GetMapping("/{book_name}")
    public String show(Model model, @PathVariable String book_name){
        model.addAttribute("book", bookDAO.show(book_name));
        model.addAttribute("joinBook", bookDAO.joinBook(book_name));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("person", new Person());
        return "books/show";
    }

    @PatchMapping("/{book_name}")
    public String assign(@PathVariable String book_name,
                         @ModelAttribute("person") Person person){
        //Надо придумать другое решение для возврата страницы /books/{book_name}
        bookDAO.assign(book_name, person.getId());
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("book", new Book());
        return "books/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult){
        bookValidator.validate(book, bindingResult);
        if (bindingResult.hasErrors()){
            return "books/new";
        }

        bookDAO.save(book);
        return "redirect:/books";
    }

    @GetMapping("{book_name}/edit")
    public String edit(@PathVariable String book_name, Model model){
        model.addAttribute("book", bookDAO.show(book_name));
        return "books/edit";
    }

    @PatchMapping("/{book_name}/edit")
    public String save(@PathVariable String book_name, @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult){

        // Сделал так чтобы можно было чтобы изменить только дату рождения а имя таким оставить
        if(!book_name.equals(book.getBook_name())){
            bookValidator.validate(book, bindingResult);
        }

        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        bookDAO.edit(book, book_name);
        return "redirect:/books";
    }

    @DeleteMapping("/{book_name}")
    public String delete(@PathVariable("book_name") String book_name){
        bookDAO.delete(book_name);
        return "redirect:/books";
    }
}
