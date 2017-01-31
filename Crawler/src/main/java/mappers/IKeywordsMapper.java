package mappers;

import beans.Keywords;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for keywords table
 */
public interface IKeywordsMapper {

    String SELECT_KEYWORDS = "SELECT id, name, person_id as personId FROM keywords";
    String SELECT_KEYWORDS_BY_PERSON_ID = SELECT_KEYWORDS + "WHERE person_id = #{personId}";

    @Select(SELECT_KEYWORDS)
    List<Keywords> getKeywords();

    @Select(SELECT_KEYWORDS_BY_PERSON_ID)
    List<String> getKeywordsByPersonId(final int personId);
}
