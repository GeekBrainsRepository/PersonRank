package mappers;

import beans.Keywords;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * mapper for keywords table
 */
public interface IKeywordsMapper {

    String SELECT_KEYWORDS = "SELECT ID as id, Name as name, PersonID as personId FROM Keywords";
    String SELECT_KEYWORDS_BY_PERSON_ID = SELECT_KEYWORDS + "WHERE PersonID = #{personId}";

    @Select(SELECT_KEYWORDS)
    List<Keywords> getKeywords();

    @Select(SELECT_KEYWORDS_BY_PERSON_ID)
    List<String> getKeywordsByPersonId(final int personId);
}
