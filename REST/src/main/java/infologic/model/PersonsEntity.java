package infologic.model;

import javax.persistence.*;
import java.util.Collection;

@NamedQueries(
        @NamedQuery(
                name = "getPersonsByName",
                query = "from PersonsEntity pe where pe.name = :name"
        )
)

@Entity
@Table(name = "Persons", schema = "personrank", catalog = "")
public class PersonsEntity implements Dictionary {
    private int id;
    private String name;
    private Collection<KeywordsEntity> keywordsesById;
    private Collection<PersonPageRankEntity> personPageRanksById;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PersonsEntity that = (PersonsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "personsByPersonId")
    public Collection<KeywordsEntity> getKeywordsesById() {
        return keywordsesById;
    }

    public void setKeywordsesById(Collection<KeywordsEntity> keywordsesById) {
        this.keywordsesById = keywordsesById;
    }

    @OneToMany(mappedBy = "personsByPersonId")
    public Collection<PersonPageRankEntity> getPersonPageRanksById() {
        return personPageRanksById;
    }

    public void setPersonPageRanksById(Collection<PersonPageRankEntity> personPageRanksById) {
        this.personPageRanksById = personPageRanksById;
    }
}
