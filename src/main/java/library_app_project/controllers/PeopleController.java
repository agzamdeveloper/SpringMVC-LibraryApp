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

    @GetMapping("/{name}")
    public String show(Model model, @PathVariable String name){
        model.addAttribute("person", personDAO.show(name));
        model.addAttribute("books",personDAO.joinPerson(name));
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
        if (bindingResult.hasErrors()){
            return "people/new";
        }

        personDAO.save(person);
        return "redirect:/people";
    }

    @GetMapping("{name}/edit")
    public String edit(@PathVariable String name, Model model){
        model.addAttribute("person", personDAO.show(name));
        return "people/edit";
    }


    @PatchMapping("/{name}")
    public String save(@PathVariable String name, @ModelAttribute("person") @Valid Person person,
                       BindingResult bindingResult){

        // Сделал так чтобы можно было чтобы изменить только дату рождения а имя таким оставить
        if(!name.equals(person.getName())){
            personValidator.validate(person, bindingResult);
        }

        if(bindingResult.hasErrors()){
            return "people/edit";
        }

        personDAO.edit(person, name);
        return "redirect:/people";
    }

    @DeleteMapping("/{name}")
    public String delete(@PathVariable("name") String name){
        personDAO.delete(name);
        return "redirect:/people";
    }
}
