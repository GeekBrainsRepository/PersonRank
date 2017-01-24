package geekbrains.internship.project_full.Crawler.src.main.java.other_classes.parser.model;

/**
 * A link to (usually) alternative language versions of a URL.
 * Class is immutable.
 */
public final class Link {
    private final String rel;
    private final String lang;
    private final String href;

    /**
     * Creates a new Link.
     * @param rel     The rel attribute (relation) of the link. Usually 'alternate' to indicate alternative versions of
     *                a document. Must not be null
     * @param lang    The lang attribute (language or language-locale code) of the link. Must not be null
     * @param href    The href attribute (URL) of the link. Must not be null
     */
    public Link(String rel, String lang, String href) {
        if (rel == null || lang == null || href == null) {
            throw new NullPointerException("Fields rel, lang and href must not be null.");
        }
        this.rel = rel;
        this.lang = lang;
        this.href = href;
    }

    /**
     * Get the rel attribute (relation) of the link.
     * @return the rel attribute (relation) of the link.
     */
    public String getRel() {
        return rel;
    }

    /**
     * Get the lang attribute (language or language-locale code) of the link.
     * @return the lang attribute (language or language-locale code) of the link.
     */
    public String getLang() {
        return lang;
    }

    /**
     * Get the href attribute (URL) of the link.
     * @return the href attribute (URL) of the link.
     */
    public String getHref() {
        return href;
    }

    /**
     * Comparison is based on all fields of the class.
     * @param obj    the reference object with which to compare.
     * @return true if the fields href, lang and rel of the reference object equal those of this object, false otherwise,
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Link link = (Link) obj;
        if (!href.equals(link.href)) {
            return false;
        }
        if (!lang.equals(link.lang)) {
            return false;
        }
        if (!rel.equals(link.rel)) {
            return false;
        }
        return true;
    }

    /**
     * Hash code is calculated from all fields of the class.
     * @return the combined hash code from the fields rel, lang and href.
     */
    @Override
    public int hashCode() {
        int result = rel.hashCode();
        result = 31 * result + lang.hashCode();
        result = 31 * result + href.hashCode();
        return result;
    }

    /**
     * All fields of the class are used as string representation.
     * @return the fields rel, lang and href as String.
     */
    @Override
    public String toString() {
        return rel + ": " + lang + "=" + href;
    }
}
