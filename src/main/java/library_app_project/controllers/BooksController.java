package library_app_project.controllers;

import library_app_project.model.Book;
import library_app_project.model.Person;
import library_app_project.services.BookService;
import library_app_project.services.PeopleService;
import library_app_project.util.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/books")
public class BooksController {
    private final BookService bookService;
    private final PeopleService peopleService;
    private final BookValidator bookValidator;

    @Autowired
    public BooksController(BookService bookService, PeopleService peopleService, BookValidator bookValidator) {
        this.bookService = bookService;
        this.peopleService = peopleService;
        this.bookValidator = bookValidator;
    }

    @GetMapping()
    public String index(HttpServletRequest request, Model model){
        if(!request.getParameterMap().isEmpty()){
            String page = request.getParameter("page");
            String booksPerPage = request.getParameter("booksPerPage");

            model.addAttribute("books", bookService.findAllWithPage(Integer.parseInt(page),
                    Integer.parseInt(booksPerPage)));
        }else {
            model.addAttribute("books", bookService.findAll());
        }
        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("book", bookService.findOne(id));
        model.addAttribute("joinBook", bookService.joinPerson(id));
        model.addAttribute("people", peopleService.findAll());
        model.addAttribute("person", new Person());
        return "books/show";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id,
                         @ModelAttribute("person") Person person){
        bookService.assign(id, person);
        return "redirect:/books";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id){
        bookService.release(id);
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

        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("book", bookService.findOne(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/edit")
    public String save(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                       BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "books/edit";
        }

        bookService.update(id, book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id){
        bookService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String search(Model model){
        model.addAttribute("book", new Book());

        return "books/search";
    }

    @PostMapping("/search")
    public String result(@RequestParam("letters") String letters, Model model){
        model.addAttribute("books", bookService.findBookStartingWith(letters));

        return "books/search";
    }

}
