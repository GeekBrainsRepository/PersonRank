package otherclasses.parser.model;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * An entry of a sitemap (either of type XML or TEXT) that contains all relevant information of the entry. This can be
 * only the URL (loc), in case of a TEXT sitemap or further information in case of an XML sitemap.
 * Class is immutable.
 */
public final class SitemapEntry {
    private final String loc;
    private final Set<Link> links;
    private final Date lastMod;
    private final ChangeFreq changeFreq;
    private final Double priority;

    /**
     * Creates a new SitemapEntry.
     * @param loc    The loc (URL) of the sitemap entry. Must not be null
     */
    public SitemapEntry(String loc) {
        this(loc, null, null, null, null);
    }

    /**
     * Creates a new SitemapEntry.
     * @param loc           The loc property (URL) of the sitemap entry. Must not be null
     * @param lastMod       The lastmod property (last modified date) of the sitemap entry
     * @param changeFreq    The changefreq property (change frequency) of the sitemap entry
     * @param priority      The priority property of the sitemap entry
     */
    public SitemapEntry(String loc, Date lastMod, ChangeFreq changeFreq, Double priority) {
        this(loc, null, lastMod, changeFreq, priority);
    }

    /**
     * Creates a new SitemapEntry.
     * @param loc           The loc property (URL) of the sitemap entry. Must not be null
     * @param lastMod       The lastmod property (last modified date) of the sitemap entry
     * @param links         The link properties of the sitemap entry. This is an extension by Google to indicate alternate
     *                      languages versions of an entry in a sitemap
     * @param changeFreq    The changefreq property (change frequency) of the sitemap entry
     * @param priority      The priority property of the sitemap entry
     * @see <a href="https://support.google.com/webmasters/answer/2620865">Use a sitemap to indicate alternate language pages</a>
     */
    public SitemapEntry(String loc, Set<Link> links, Date lastMod, ChangeFreq changeFreq, Double priority) {
        if (loc == null) {
            throw new NullPointerException("Field loc must not be null.");
        }
        this.loc = loc;
        if (links == null) {
            this.links = Collections.unmodifiableSet(new LinkedHashSet<Link>());
        } else {
            this.links = Collections.unmodifiableSet(new LinkedHashSet<>(links));
        }
        if (lastMod == null) {
            this.lastMod = null;
        } else {
            this.lastMod = new Date(lastMod.getTime());
        }
        this.changeFreq = changeFreq;
        this.priority = priority;
    }

    /**
     * Enum containing all possible values for the change frequency of a sitemap entry.
     */
    public enum ChangeFreq {
        /**
         * The always changefreq property.
         */
        always,
        /**
         * The hourly changefreq property.
         */
        hourly,
        /**
         * The daily changefreq property.
         */
        daily,
        /**
         * The weekly changefreq property.
         */
        weekly,
        /**
         * The monthly changefreq property.
         */
        monthly,
        /**
         * The yearly changefreq property.
         */
        yearly,
        /**
         * The never changefreq property.
         */
        never,
    }

    /**
     * Get the loc property (URL) of the sitemap entry.
     * @return the loc property (URL) of the sitemap entry.
     */
    public String getLoc() {
        return loc;
    }

    /**
     * Get the link properties of the sitemap entry. Can be null when the sitemap entry has no links.
     * @return the link properties of the sitemap entry.
     * @see <a href="https://support.google.com/webmasters/answer/2620865">Use a sitemap to indicate alternate language pages</a>
     */
    public Set<Link> getLinks() {
        return links;
    }

    /**
     * Get the lastmod property (last modified date) of the sitemap entry. Can be null when the sitemap entry has no
     * lastmod property.
     * @return the lastmod property (last modified date) of the sitemap entry.
     */
    public Date getLastMod() {
        if (lastMod == null) {
            return null;
        }
        return new Date(lastMod.getTime());
    }

    /**
     * Get the changefreq property (change frequency) of the sitemap entry. Can be null when the sitemap entry has no
     * changefreq property.
     * @return the changefreq property (change frequency) of the sitemap entry.
     */
    public ChangeFreq getChangeFreq() {
        return changeFreq;
    }

    /**
     * Get the priority property of the sitemap entry. Can be null when the sitemap entry has no priority property.
     * @return the priority property of the sitemap entry.
     */
    public Double getPriority() {
        return priority;
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

        otherclasses.parser.model.SitemapEntry that = (otherclasses.parser.model.SitemapEntry) obj;
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
     * @return the loc property (URL) of the sitemap entry.
     */
    @Override
    public String toString() {
        return loc;
    }
}
