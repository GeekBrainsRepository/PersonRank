package infologic.repository;

import infologic.dao.PagesDAOInterface;
import infologic.dao.PersonsDAOInterface;
import infologic.dao.PersonsPageRankDAOInterface;
import infologic.model.PersonPageRankEntity;
import infologic.model.PersonsEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RealUserRepository implements UserRepository {

	PersonsDAOInterface persons;
	PersonsPageRankDAOInterface personsPageRank;
	PagesDAOInterface pages;

	@Override
	public Common get(int siteId) {
		Map<String, Integer> result = new HashMap();
		Date date = new Date(0);
		ArrayList<PersonsEntity> personList = new ArrayList<>(persons.getAllPerson());
		for (PersonsEntity o : personList) {
			String name = o.getName();
			ArrayList<PersonPageRankEntity> rankList = new ArrayList<>(personsPageRank.getPersonPageRankByPersonSite(o.getId(), siteId));
			int sum = 0;
			for (PersonPageRankEntity ppr : rankList) {
				sum += ppr.getRankId();
				Date temp = pages.getById(ppr.getPageId()).getLastScanDate();
				if (temp.after(date))
					date = temp;
			}
			result.put(name, sum);
		}
		return new Common(date, result);
	}

	@Override
	public Daily get(int siteId, int personId, long dateStart, long dateEnd) {
		// TODO Auto-generated method stub
		return null;
	}

}