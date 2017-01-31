package infologic.controller;

import infologic.model.Dictionary;
import infologic.model.KeywordsEntity;
import infologic.model.PersonsEntity;
import infologic.model.SitesEntity;
import infologic.repository.Repository;
import infologic.repository.RepositoryInterface;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    private static RepositoryInterface<Dictionary> repository = new Repository();

    ////////////////////////////////DELETE
    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/person/{id}")
    public @ResponseBody void deletePerson(@PathVariable Integer id){
        PersonsEntity pattern = new PersonsEntity();
        pattern.setId(id);
        repository.remove(pattern);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/site/{id}")
    public @ResponseBody void deleteSite(@PathVariable Integer id){
        SitesEntity pattern = new SitesEntity();
        pattern.setId(id);
        repository.remove(pattern);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/admin/keyword/{id}")
    public @ResponseBody void deleteKeyWord(@PathVariable Integer id){
        KeywordsEntity pattern = new KeywordsEntity();
        pattern.setId(id);
        repository.remove(pattern);
    }
    //////////////////////////////////PUT
    @RequestMapping(method = RequestMethod.PUT, path = "/admin/person/{name}")
    public @ResponseBody void putPerson(@PathVariable String name){
        PersonsEntity pattern = new PersonsEntity();
        pattern.setName(name);
        repository.add(pattern);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/admin/site/{name}")
    public @ResponseBody void putSite(@PathVariable String name){
        SitesEntity pattern = new SitesEntity();
        pattern.setName(name);
        repository.add(pattern);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/admin/keyword/{name}/{personId}")
    public @ResponseBody void putKeyWord(@PathVariable String name, @PathVariable Integer personId){
        KeywordsEntity pattern = new KeywordsEntity();
        pattern.setName(name);
        pattern.setPersonId(personId);
        repository.add(pattern);
    }
    //////////////////////////////////////POST
    @RequestMapping(method = RequestMethod.POST, path = "/admin/person/{id}/{name}")
    public @ResponseBody void postPerson(@PathVariable Integer id, @PathVariable String name){
        PersonsEntity pattern = new PersonsEntity();
        pattern.setId(id);
        pattern.setName(name);
        repository.update(pattern);
    }
    @RequestMapping(method = RequestMethod.POST, path = "/admin/site/{id}/{name}")
    public @ResponseBody void postSite(@PathVariable Integer id, @PathVariable String name){
        SitesEntity pattern = new SitesEntity();
        pattern.setId(id);
        pattern.setName(name);
        repository.update(pattern);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/admin/keyword/{id}/{name}")
    public @ResponseBody void postKeyWords(@PathVariable Integer id, @PathVariable String name){
        KeywordsEntity pattern = new KeywordsEntity();
        pattern.setId(id);
        pattern.setName(name);
        repository.update(pattern);
    }


    /*
    @RequestMapping(method = RequestMethod.PUT, path = "/admin/person/{id}")
    public @ResponseBody  PersonsEntity updatePerson(@PathVariable Integer id, @RequestBody PersonsEntity name) {
        //Update person
        return name;
    }
    */

}
