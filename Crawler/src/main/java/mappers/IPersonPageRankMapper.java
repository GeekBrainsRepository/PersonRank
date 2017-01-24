package mappers;

import beans.PersonPageRank;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for table PersonPageRank
 */
public interface IPersonPageRankMapper {

    String SELECT_PERSON_PAGE_RANK = "SELECT person_id as personId, page_id as pageId," +
            "rank FROM person_page_rank";

    @Select(SELECT_PERSON_PAGE_RANK)
    List<PersonPageRank> getPersonPageRank();
}
