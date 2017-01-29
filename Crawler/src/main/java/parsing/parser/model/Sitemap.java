package parsing.parser.model;



import java.util.*;

/**
 * An abstraction of an actual sitemap. This class contains all information that are logically relevant for a sitemap.
 * Class is immutable.

 */
public final class Sitemap {
    private final Set<SitemapIndex> sitemapIndexes;
    private final Set<SitemapEntry> sitemapEntries;
    private final SitemapType sitemapType;

    /**
     * Creates a new Sitemap.
     * @param sitemapIndexes    The sitemap indexes if the sitemap if the sitemap is of type INDEX. An empty Set otherwise.
     * @param sitemapEntries    All sitemap entries of the sitemap or of all referenced sitemaps of a sitemap index
     * @param sitemapType       The type of the sitemap. Must not be null
     * @see Sitemap.SitemapType
     */
    public Sitemap(Set<SitemapIndex> sitemapIndexes, Set<SitemapEntry> sitemapEntries, SitemapType sitemapType) {
        this.sitemapIndexes = Collections.unmodifiableSet(new LinkedHashSet<>(sitemapIndexes));
        this.sitemapEntries = Collections.unmodifiableSet(new LinkedHashSet<>(sitemapEntries));
        if (sitemapType == null) {
            throw new NullPointerException("Field sitemapType must not be null.");
        }
        this.sitemapType = sitemapType;
    }

    /**
     * Enum that contains the different types of sitemaps.
     */
    public enum SitemapType {
        /**
         * Sitemap of type text. A file that only contains URLs (each in a new line).
         */
        TEXT,
        /**
         * Sitemap of type XML. A file that contains an XML structure which holds, aside from the URL, otherclasses information
         * like the last modified date of the URL.
         */
        XML,
        /**
         * A sitemap index. A file that contains an XML structure in which sitemaps of type XML are referenced.
         */
        INDEX;
    }

    /**
     * Get the sitemap indexes for this sitemap.
     * @return a Set which contains a SitemapIndex object for each sitemap index that was referenced in a parsed
     * sitemap. Returns an empty Set for sitemaps of type XML or INDEX.
     */
    public Set<SitemapIndex> getSitemapIndexes() {
        return sitemapIndexes;
    }

    /**
     * Get the sitemap entries for this sitemap.
     * @return a Set which contains a SitemapEntry object for each entry from a parsed sitemap. When parsing a sitemap
     * index, all entries from all referenced sitemaps are returned.
     */
    public Set<SitemapEntry> getSitemapEntries() {
        return sitemapEntries;
    }

    /**
     * Get the type of the sitemap.
     * @return the type of the sitemap
     * @see Sitemap.SitemapType
     */
    public SitemapType getSitemapType() {
        return sitemapType;
    }

    /**
     * Get a sitemap that contains only entries matching the passed URL.
     * @param url    The URL of the sitemap entries that should remain in the returned sitemap
     * @return a copy of this sitemap containing only sitemap entries whose URL starts with the passed URL. The
     * comparison is case insensitive.
     */
    public Sitemap getSitemapMatchingUrl(String url) {
        String urlLowerCase = url.toLowerCase(Locale.ENGLISH);
        Set<SitemapEntry> sitemapEntriesMatchingUrl = new LinkedHashSet<>();
        for (SitemapEntry sitemapEntry : sitemapEntries) {
            if (sitemapEntry.getLoc().toLowerCase(Locale.ENGLISH).startsWith(urlLowerCase)) {
                sitemapEntriesMatchingUrl.add(sitemapEntry);
            }
        }
        return new Sitemap(sitemapIndexes, sitemapEntriesMatchingUrl, sitemapType);
    }

    /**
     * Get a sitemap that contains only entries and indexes that were modified after the passed date.
     * Note: You can also pass a date to the parseSitemap method of the SitemapParser.
     * @param minLastMod    The date after which entries and indexes must be modified to remain in the returned sitemap
     * @return a copy of this sitemap containing only sitemap entries and sitemap indexes whose last modified date is
     * after the passed date.
     */
    public Sitemap getSitemapModifiedAfter(Date minLastMod) {
        Set<SitemapIndex> sitemapIndexesModifiedAfter = new LinkedHashSet<>();
        for (SitemapIndex sitemapIndex : sitemapIndexes) {
            if (sitemapIndex.getLastMod() != null && sitemapIndex.getLastMod().after(minLastMod)) {
                sitemapIndexesModifiedAfter.add(sitemapIndex);
            }
        }
        Set<SitemapEntry> sitemapEntriesModifiedAfter = new LinkedHashSet<>();
        for (SitemapEntry sitemapEntry : sitemapEntries) {
            if (sitemapEntry.getLastMod() != null && sitemapEntry.getLastMod().after(minLastMod)) {
                sitemapEntriesModifiedAfter.add(sitemapEntry);
            }
        }
        return new Sitemap(sitemapIndexesModifiedAfter, sitemapEntriesModifiedAfter, sitemapType);
    }

    /**
     * Get a sitemap that contains only entries with one of the passed change frequencies.
     * @param changeFrequencies    A set containing one or multiple change frequencies of sitemap entries that should
     *                             remain in the returned sitemap
     * @return a copy of this sitemap containing only sitemap entries whose change frequency is one of the passed
     * change frequencies.
     */
    public Sitemap getSitemapWithChangeFreq(EnumSet<SitemapEntry.ChangeFreq> changeFrequencies) {
        Set<SitemapEntry> sitemapEntriesChangeFreq = new LinkedHashSet<>();
        for (SitemapEntry sitemapEntry : sitemapEntries) {
            if (changeFrequencies.contains(sitemapEntry.getChangeFreq())) {
                sitemapEntriesChangeFreq.add(sitemapEntry);
            }
        }
        return new Sitemap(sitemapIndexes, sitemapEntriesChangeFreq, sitemapType);
    }

    /**
     * Get a sitemap that contains only entries with at least the passed priority.
     * @param priority    The priority that a sitemap entry must have to remain in the returned sitemap
     * @return a copy of this sitemap containing only sitemap entries with at least the passed priority.
     */
    public Sitemap getSitemapWithMinPriority(double priority) {
        Set<SitemapEntry> sitemapEntriesMinPriority = new LinkedHashSet<>();
        for (SitemapEntry sitemapEntry : sitemapEntries) {
            if (sitemapEntry.getPriority() != null && sitemapEntry.getPriority() >= priority) {
                sitemapEntriesMinPriority.add(sitemapEntry);
            }
        }
        return new Sitemap(sitemapIndexes, sitemapEntriesMinPriority, sitemapType);
    }

    /**
     * All fields of the class are used as string representation.
     * @return a string representation of the fields sitemapType, sitemapIndexes and sitemapEntries.
     */
    @Override
    public String toString() {
        return "Sitemap{" +
                "sitemapType=" + sitemapType +
                ", sitemapIndexes=" + sitemapIndexes +
                ", sitemapEntries=" + sitemapEntries +
                '}';
    }
}
