package infologic.controller;

import infologic.model.PersonsEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {


    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/person/{id}")
    public @ResponseBody PersonsEntity deletePerson(@PathVariable Integer id){
        //deleting user
        PersonsEntity ent = new PersonsEntity();
        ent.setName("testname");
        ent.setId(666);
        return ent;

    }

    /*
    @RequestMapping(method = RequestMethod.PUT, path = "/admin/person/{id}")
    public @ResponseBody  PersonsEntity updatePerson(@PathVariable Integer id, @RequestBody PersonsEntity name) {
        //Update person
        return name;
    }
    */

}
