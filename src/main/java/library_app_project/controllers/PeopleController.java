package library_app_project.controllers;

import library_app_project.dao.PersonDAO;
import library_app_project.model.Person;
import library_app_project.util.PersonValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class PeopleController {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    public PeopleController(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping()
    public String index(Model model){
        model.addAttribute("people", personDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(Model model, @PathVariable("id") int id){
        model.addAttribute("person", personDAO.show(id));
        model.addAttribute("books",personDAO.joinPerson(id));
        return "people/show";
    }

    @GetMapping("/new")
    public String add(Model model){
        model.addAttribute("person", new Person());
        return "people/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult){
        personValidator.validate(person, bindingResult);
        if(bindingResult.hasErrors()){
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", personDAO.show(id));
        return "people/edit";
    }


    @PatchMapping("/{id}/edit")
    public String save(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        personDAO.edit(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id){
        personDAO.delete(id);
        return "redirect:/people";
    }
}
