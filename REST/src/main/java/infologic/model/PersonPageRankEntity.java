package infologic.model;

import javax.persistence.*;

@Entity
@Table(name = "person_page_rank", schema = "personrank", catalog = "")
public class PersonPageRankEntity {
    private Integer personId;
    private Integer pageId;
    private Integer rankId;
    private PersonsEntity personsByPersonId;
    private PagesEntity pagesByPageId;

    @Id
    @Column(name = "rank_id")
    public Integer getRankId() {
        return rankId;
    }

    @Basic
    @Column(name = "person_id", insertable = false, updatable = false)
    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    @Basic
    @Column(name = "page_id", insertable = false, updatable = false)
    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public void setRankId(Integer rank) {
        this.rankId = rankId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonPageRankEntity that = (PersonPageRankEntity) o;

        if (personId != null ? !personId.equals(that.personId) : that.personId != null) return false;
        if (pageId != null ? !pageId.equals(that.pageId) : that.pageId != null) return false;
        if (rankId != null ? !rankId.equals(that.rankId) : that.rankId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = personId != null ? personId.hashCode() : 0;
        result = 31 * result + (pageId != null ? pageId.hashCode() : 0);
        result = 31 * result + (rankId != null ? rankId.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    public PersonsEntity getPersonsByPersonId() {
        return personsByPersonId;
    }

    public void setPersonsByPersonId(PersonsEntity personsByPersonId) {
        this.personsByPersonId = personsByPersonId;
    }

    @ManyToOne
    @JoinColumn(name = "page_id", referencedColumnName = "id")
    public PagesEntity getPagesByPageId() {
        return pagesByPageId;
    }

    public void setPagesByPageId(PagesEntity pagesByPageId) {
        this.pagesByPageId = pagesByPageId;
    }
}
