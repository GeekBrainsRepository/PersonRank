package parsing.parser.model;

import java.util.Date;

/**
 * A sitemap index that contains links to actual sitemaps.
 * Class is immutable.
 */
public final class SitemapIndex {
    private final String loc;
    private final Date lastMod;

    /**
     * Creates a new SitemapIndex.
     * @param loc    The loc (URL) of the sitemap index. Must not be null
     */
    public SitemapIndex(String loc) {
        this(loc, null);
    }

    /**
     * Creates a new SitemapIndex.
     * @param loc        The loc (URL) of the sitemap index. Must not be null
     * @param lastMod    The lastmod property (last modified date) of the sitemap index
     */
    public SitemapIndex(String loc, Date lastMod) {
        if (loc == null) {
            throw new NullPointerException("Field loc must not be null.");
        }
        this.loc = loc;
        if (lastMod == null) {
            this.lastMod = null;
        } else {
            this.lastMod = new Date(lastMod.getTime());
        }
    }

    /**
     * Get the loc (URL) of the sitemap index.
     * @return the loc (URL) of the sitemap index.
     */
    public String getLoc() {
        return loc;
    }

    /**
     * Get the lastmod property (last modified date) of the sitemap index.
     * @return the lastmod property (last modified date) of the sitemap index.
     */
    public Date getLastMod() {
        if (lastMod == null) {
            return null;
        }
        return new Date(lastMod.getTime());
    }

    /**
     * Comparison is based on the loc property.
     * @param obj    the reference object with which to compare.
     * @return true if the loc property of the reference object equals the loc property of this object, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        SitemapIndex that = (SitemapIndex) obj;
        return loc.equals(that.loc);
    }

    /**
     * Hash code is calculated from the loc property.
     * @return the hash code from the loc property.
     */
    @Override
    public int hashCode() {
        return loc.hashCode();
    }

    /**
     * The loc property is used as string representation.
     * @return the loc property (URL) of the sitemap index.
     */
    @Override
    public String toString() {
        return loc;
    }
}
