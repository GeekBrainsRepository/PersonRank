package infologic.dao;

import java.util.Collection;

import infologic.model.PersonPageRankEntity;

public interface PersonsPageRankDAOInterface {
	Collection<PersonPageRankEntity> getPersonPageRankByPersonSite(int personId, int siteId);
}