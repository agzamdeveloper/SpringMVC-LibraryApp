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

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookDAO.show(id));
        model.addAttribute("joinBook", bookDAO.joinBook(id));
        model.addAttribute("people", personDAO.index());
        model.addAttribute("person", new Person());
        return "books/show";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person") Person person){
        bookDAO.assign(id, person.getId());
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookDAO.release(id);
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

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookDAO.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/edit")
    public String save(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        bookDAO.edit(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookDAO.delete(id);
        return "redirect:/books";
    }
}
