/*
 * 
 */
package PersonRank.data;

/**
 *
 */
class Keyword {

    private String name;
    private Person person;

    public Keyword(String name, Person person) {
        this.name = name;
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public Person getPerson() {
        return person;
    }

}
