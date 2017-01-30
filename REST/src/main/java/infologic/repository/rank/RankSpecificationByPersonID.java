package infologic.repository.rank;

import infologic.model.PersonPageRankEntity;
import infologic.repository.Specification;


public class RankSpecificationByPersonID implements Specification<PersonPageRankEntity>{
    int personId;

    public RankSpecificationByPersonID(int personId) {
        this.personId = personId;
    }

    @Override
    public boolean specified(PersonPageRankEntity pattern) {
        return pattern.getPersonId()==personId;
    }
}
