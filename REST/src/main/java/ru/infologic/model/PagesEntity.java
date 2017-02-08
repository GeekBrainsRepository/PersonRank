package ru.infologic.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Entity
@Table(name = "Pages", schema = "personrank", catalog = "")
public class PagesEntity {
    private int id;
    private String url;
    private int siteId;
    private Timestamp foundDataTime;
    private Timestamp lastScanDate;
    private SitesEntity sitesBySiteId;
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
    @Column(name = "Url")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "SiteID", insertable = false, updatable = false)
    public int getSiteId() {
        return siteId;
    }

    public void setSiteId(int siteId) {
        this.siteId = siteId;
    }

    @Basic
    @Column(name = "FoundDateTime")
    public Timestamp getFoundDataTime() {
        return foundDataTime;
    }

    public void setFoundDataTime(Timestamp foundDataTime) {
        this.foundDataTime = foundDataTime;
    }

    @Basic
    @Column(name = "LastScanDate")
    public Timestamp getLastScanDate() {
        return lastScanDate;
    }

    public void setLastScanDate(Timestamp lastScanDate) {
        this.lastScanDate = lastScanDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PagesEntity that = (PagesEntity) o;

        if (id != that.id) return false;
        if (siteId != that.siteId) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (foundDataTime != null ? !foundDataTime.equals(that.foundDataTime) : that.foundDataTime != null)
            return false;
        if (lastScanDate != null ? !lastScanDate.equals(that.lastScanDate) : that.lastScanDate != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + siteId;
        result = 31 * result + (foundDataTime != null ? foundDataTime.hashCode() : 0);
        result = 31 * result + (lastScanDate != null ? lastScanDate.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "SiteID", referencedColumnName = "ID", nullable = false)
    public SitesEntity getSitesBySiteId() {
        return sitesBySiteId;
    }

    public void setSitesBySiteId(SitesEntity sitesBySiteId) {
        this.sitesBySiteId = sitesBySiteId;
    }

    @OneToMany(mappedBy = "pagesByPageId")
    public Collection<PersonPageRankEntity> getPersonPageRanksById() {
        return personPageRanksById;
    }

    public void setPersonPageRanksById(Collection<PersonPageRankEntity> personPageRanksById) {
        this.personPageRanksById = personPageRanksById;
    }
}
