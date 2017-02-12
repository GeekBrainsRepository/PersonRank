package beans;

/**
 * бин таблицы PersonPageRank
 */
public class PersonPageRank {

    private int id;
    private int personId;
    private int pageId;
    private int rank;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "personId- " + personId + " pageID- " + pageId + " rank- " + rank;
    }
}
