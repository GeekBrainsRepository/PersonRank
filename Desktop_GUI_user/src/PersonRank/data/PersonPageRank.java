/*
 *  
 */
package PersonRank.data;

/**
 * 
 */
class PersonPageRank {
    
    private Person person;
    private Page page;
    private int rank;

    public PersonPageRank(Person person, Page page, int rank) {
        this.person = person;
        this.page = page;
        this.rank = rank;
    }

    public Person getPerson() {
        return person;
    }

    public Page getPage() {
        return page;
    }

    public int getRank() {
        return rank;
    }
    
    
}
