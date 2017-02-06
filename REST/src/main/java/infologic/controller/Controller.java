package infologic.controller;

import infologic.StatisticUtilities;
import infologic.model.*;
import infologic.repository.Repository;
import infologic.repository.RepositoryInterface;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class Controller {

    private static RepositoryInterface<Dictionary> repository = new Repository();

    ////////////////////////////////DELETE
    @RequestMapping(method = RequestMethod.DELETE, path = "/person/{id}")
    public
    @ResponseBody
    void deletePerson(@PathVariable Integer id) {
        PersonsEntity pattern = new PersonsEntity();
        pattern.setId(id);
        repository.remove(pattern);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/site/{id}")
    public
    @ResponseBody
    void deleteSite(@PathVariable Integer id) {
        SitesEntity pattern = new SitesEntity();
        pattern.setId(id);
        repository.remove(pattern);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/keyword/{id}")
    public
    @ResponseBody
    void deleteKeyword(@PathVariable Integer id) {
        KeywordsEntity pattern = new KeywordsEntity();
        pattern.setId(id);
        repository.remove(pattern);
    }

    //////////////////////////////////PUT
    @RequestMapping(method = RequestMethod.PUT, path = "/person/{name}")
    public
    @ResponseBody
    void putPerson(@PathVariable String name) {
        PersonsEntity pattern = new PersonsEntity();
        pattern.setName(name);
        repository.add(pattern);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/site/{name}")
    public
    @ResponseBody
    void putSite(@PathVariable String name) {
        SitesEntity pattern = new SitesEntity();
        pattern.setName(name);
        repository.add(pattern);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/keyword/{name}/{personId}")
    public
    @ResponseBody
    void putKeyword(@PathVariable String name, @PathVariable Integer personId) {
        KeywordsEntity pattern = new KeywordsEntity();
        pattern.setName(name);
        pattern.setPersonId(personId);
        repository.add(pattern);
    }

    //////////////////////////////////////POST
    @RequestMapping(method = RequestMethod.POST, path = "/person/{id}/{name}")
    public
    @ResponseBody
    void postPerson(@PathVariable Integer id, @PathVariable String name) {
        PersonsEntity pattern = new PersonsEntity();
        pattern.setId(id);
        pattern.setName(name);
        repository.update(pattern);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/site/{id}/{name}")
    public
    @ResponseBody
    void postSite(@PathVariable Integer id, @PathVariable String name) {
        SitesEntity pattern = new SitesEntity();
        pattern.setId(id);
        pattern.setName(name);
        repository.update(pattern);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/keyword/{id}/{name}/{personId}")
    public
    @ResponseBody
    void postKeyword(@PathVariable Integer id, @PathVariable String name, @PathVariable Integer personId) {
        KeywordsEntity pattern = new KeywordsEntity();
        pattern.setId(id);
        pattern.setName(name);
        pattern.setPersonId(personId);
        repository.update(pattern);
    }

    ////////////////////////////////////////GET
    @RequestMapping(method = RequestMethod.GET, path = "/person")
    public Map<Integer, String> getPersons() {
        return StatisticUtilities.createPersons(repository);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/site")
    public Map<Integer, String> getSites() {
        return StatisticUtilities.createSites(repository);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/keyword/{personId}")
    public Map<Integer, String> getKeywords(@PathVariable Integer personId) {
        return StatisticUtilities.createKeyword(repository, personId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/common/{siteId}")
    public Common getCommon(@PathVariable("siteId") Integer siteId) {
        return StatisticUtilities.createCommon(siteId);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/daily/{siteId}/{personId}/{dateStart}/{dateEnd}")
    public Daily getDaily(@PathVariable Integer siteId, @PathVariable Integer personId, @PathVariable Long dateStart, @PathVariable Long dateEnd) {
        return StatisticUtilities.createDaily(siteId, personId, dateStart, dateEnd);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/authentication/{login}/{password}")
    public String  authentication(@PathVariable("login") String login, @PathVariable("password") String password){
        return StatisticUtilities.authenticationUsers(login,password);
    }
}