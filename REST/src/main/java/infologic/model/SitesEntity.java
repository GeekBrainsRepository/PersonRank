package infologic.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "Sites", schema = "personrank", catalog = "")
public class SitesEntity {
    private int id;
    private String name;
    private Collection<PagesEntity> pagesById;

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

        SitesEntity that = (SitesEntity) o;

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

    @OneToMany(mappedBy = "sitesBySiteId")
    public Collection<PagesEntity> getPagesById() {
        return pagesById;
    }

    public void setPagesById(Collection<PagesEntity> pagesById) {
        this.pagesById = pagesById;
    }
}
