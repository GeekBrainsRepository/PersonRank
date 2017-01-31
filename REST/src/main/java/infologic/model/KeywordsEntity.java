package infologic.model;

import javax.persistence.*;

@Entity
@Table(name = "Keywords", schema = "personrank", catalog = "")
public class KeywordsEntity implements Book{
    private int id;
    private String name;
    private int personId;
    private PersonsEntity personsByPersonId;

    @Id
    @Column(name = "ID")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "Name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "PersonID", insertable = false, updatable = false)
    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        KeywordsEntity that = (KeywordsEntity) o;

        if (id != that.id) return false;
        if (personId != that.personId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + personId;
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "PersonID", referencedColumnName = "ID", nullable = false)
    public PersonsEntity getPersonsByPersonId() {
        return personsByPersonId;
    }

    public void setPersonsByPersonId(PersonsEntity personsByPersonId) {
        this.personsByPersonId = personsByPersonId;
    }
}
